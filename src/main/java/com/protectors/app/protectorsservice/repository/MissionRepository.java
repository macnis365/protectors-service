package com.protectors.app.protectorsservice.repository;

import com.protectors.app.protectorsservice.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.id = ?1 and e.isDeleted = false")
    Optional<Mission> findById(Long id);

}
