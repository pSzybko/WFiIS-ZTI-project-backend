package zti.projekt.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;
import java.util.List;
import zti.projekt.model.AnnouncementModel;
import zti.projekt.model.LocationEnum;
import zti.projekt.model.UserModel;

@Repository
public interface AnnouncementRepository extends JpaRepository<AnnouncementModel, Integer> {
    List<AnnouncementModel> findByLocation(LocationEnum location);
    List<AnnouncementModel> findByLocation(LocationEnum location, Sort sort);
    List<AnnouncementModel> findByUser(UserModel user);
    List<AnnouncementModel> findByUserId(Integer userId);
    List<AnnouncementModel> findAll(Sort sort);
}