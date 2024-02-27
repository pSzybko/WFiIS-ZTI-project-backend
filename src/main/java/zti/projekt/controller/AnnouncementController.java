package zti.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import zti.projekt.model.AnnouncementModel;
import zti.projekt.model.LocationEnum;
import zti.projekt.model.UserModel;
import zti.projekt.repository.AnnouncementRepository;
import zti.projekt.service.AnnouncementService;
import zti.projekt.service.UserService;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final UserService userService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService, UserService userService) {
        this.announcementService = announcementService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementModel> getAnnouncementById(@PathVariable("id") Integer id) {
        AnnouncementModel announcement = announcementService.getAnnouncementById(id);
        return ResponseEntity.ok(announcement);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AnnouncementModel>> getAnnouncementsByUser(@PathVariable("userId") Integer userId) {
        List<AnnouncementModel> announcements = announcementService.getAnnouncementsByUser(userId);
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<AnnouncementModel>> getAnnouncementsByLocation(@PathVariable("location") LocationEnum location) {
        List<AnnouncementModel> announcements = announcementService.getAnnouncementsByLocation(location);
        return ResponseEntity.ok(announcements);
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementModel>> getAllAnnouncements() {
        List<AnnouncementModel> announcements = announcementService.getAllAnnouncements();
        return ResponseEntity.ok(announcements);
    }
    @GetMapping("/sorted/{sortDirection}")
    public ResponseEntity<List<AnnouncementModel>> getAllAnnouncementsSortedByDate(@PathVariable String sortDirection) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection.equalsIgnoreCase("DESC")) {
            direction = Sort.Direction.DESC;
        }
        List<AnnouncementModel> announcements = announcementService.getAllAnnouncementsSortedByDate(direction);
        return ResponseEntity.ok(announcements);
    }
    @GetMapping("/sorted/{location}/{sortDirection}")
    public ResponseEntity<List<AnnouncementModel>> getAnnouncementsByLocationSortedByDate(
            @PathVariable LocationEnum location,
            @PathVariable String sortDirection
    ) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection.equalsIgnoreCase("DESC")) {
            direction = Sort.Direction.DESC;
        }
        List<AnnouncementModel> announcements = announcementService.getAnnouncementsByLocationSortedByDate(location, direction);
        return ResponseEntity.ok(announcements);
    }
    @PostMapping
    public ResponseEntity<AnnouncementModel> createAnnouncement(@RequestBody AnnouncementModel announcement) {
        AnnouncementModel createdAnnouncement = announcementService.createAnnouncement(announcement);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnouncement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementModel> updateAnnouncement(
            @PathVariable("id") Integer id, @RequestBody AnnouncementModel announcement) {
        AnnouncementModel updatedAnnouncement = announcementService.updateAnnouncement(id, announcement);
        return ResponseEntity.ok(updatedAnnouncement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable("id") Integer id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/following/{id}")
    public ResponseEntity<List<AnnouncementModel>> getAnnouncementsFromFollowingUsers(@PathVariable("id") Integer id) {
        UserModel currentUser = userService.getUserById(id);
        Set<UserModel> followingUsers = currentUser.getFollowing();
        List<AnnouncementModel> announcements = new ArrayList<>();

        for (UserModel user : followingUsers) {
            announcements.addAll(announcementService.getAnnouncementsByUser(user.getId()));
        }

        return ResponseEntity.ok(announcements);
    }
}
