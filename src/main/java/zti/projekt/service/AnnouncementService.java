package zti.projekt.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zti.projekt.model.AnnouncementModel;
import zti.projekt.model.LocationEnum;
import zti.projekt.repository.AnnouncementRepository;
import java.util.List;
import zti.projekt.exception.AnnouncementNotFoundException;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public AnnouncementModel createAnnouncement(AnnouncementModel announcement) {
        return announcementRepository.save(announcement);
    }

    public AnnouncementModel getAnnouncementById(Integer id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new AnnouncementNotFoundException("Announcement not found with id: " + id));
    }

    public List<AnnouncementModel> getAnnouncementsByUser(Integer userId) {
        return announcementRepository.findByUserId(userId);
    }

    public List<AnnouncementModel> getAnnouncementsByLocation(LocationEnum location) {
        return announcementRepository.findByLocation(location);
    }

    public List<AnnouncementModel> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public AnnouncementModel updateAnnouncement(Integer id, AnnouncementModel announcement) {
        AnnouncementModel existingAnnouncement = getAnnouncementById(id);
        existingAnnouncement.setTitle(announcement.getTitle());
        existingAnnouncement.setDescription(announcement.getDescription());
        existingAnnouncement.setType(announcement.getType());
        existingAnnouncement.setLocation(announcement.getLocation());
        return announcementRepository.save(existingAnnouncement);
    }

    public void deleteAnnouncement(Integer id) {
        AnnouncementModel announcement = getAnnouncementById(id);
        announcementRepository.delete(announcement);
    }
    public List<AnnouncementModel> getAllAnnouncementsSortedByDate(Sort.Direction sortDirection) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        if (sortDirection == Sort.Direction.ASC) {
            sort = Sort.by(Sort.Direction.ASC, "createdAt");
        }
        return announcementRepository.findAll(sort);
    }
    public List<AnnouncementModel> getAnnouncementsByLocationSortedByDate(LocationEnum location, Sort.Direction sortDirection) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        if (sortDirection == Sort.Direction.ASC) {
            sort = Sort.by(Sort.Direction.ASC, "createdAt");
        }
        return announcementRepository.findByLocation(location, sort);
    }
}