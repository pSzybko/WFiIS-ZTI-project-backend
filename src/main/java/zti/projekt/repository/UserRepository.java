package zti.projekt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zti.projekt.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer>{
    Optional<UserModel> findByUsernameAndPassword(String username, String password);
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findById(Integer id);
    Boolean existsByUsername(String username);
}
