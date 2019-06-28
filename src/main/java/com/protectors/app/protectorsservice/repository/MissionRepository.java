package com.protectors.app.protectorsservice.repository;

import com.protectors.app.protectorsservice.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository  extends JpaRepository<Mission, Long> {
}
