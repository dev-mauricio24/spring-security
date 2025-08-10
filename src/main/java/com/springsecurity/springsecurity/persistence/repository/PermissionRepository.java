package com.springsecurity.springsecurity.persistence.repository;

import com.springsecurity.springsecurity.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
}
