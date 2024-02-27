package zti.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import zti.projekt.exception.UserRegistrationException;
import zti.projekt.model.AnnouncementModel;
import zti.projekt.model.UserModel;
import zti.projekt.repository.UserRepository;
import zti.projekt.service.AnnouncementService;
import zti.projekt.service.UserService;
import zti.projekt.payload.request.FollowRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable("id") Integer id) {
        UserModel user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestBody FollowRequest followRequest) {
        userService.followUser(followRequest.getFollowerId(), followRequest.getFollowedId());
        return ResponseEntity.ok("User followed successfully");
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestBody FollowRequest followRequest) {
        userService.unfollowUser(followRequest.getFollowerId(), followRequest.getFollowedId());
        return ResponseEntity.ok("User unfollowed successfully");
    }
    @GetMapping("/{followerId}/following/{followedId}")
    public ResponseEntity<Boolean> isFollowing(@PathVariable Integer followerId, @PathVariable Integer followedId) {
        boolean isFollowing = userService.isFollowing(followerId, followedId);
        return ResponseEntity.ok(isFollowing);
    }

    @GetMapping("/{followerId}/followers/{followedId}")
    public ResponseEntity<Boolean> isFollower(@PathVariable Integer followerId, @PathVariable Integer followedId) {
        boolean isFollower = userService.isFollower(followerId, followedId);
        return ResponseEntity.ok(isFollower);
    }
}
