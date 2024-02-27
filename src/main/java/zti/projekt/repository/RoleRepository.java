package zti.projekt.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zti.projekt.model.RoleModel;
import zti.projekt.model.RoleEnum;


@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Integer>{
    Optional<RoleModel> findByName(RoleEnum role);
}