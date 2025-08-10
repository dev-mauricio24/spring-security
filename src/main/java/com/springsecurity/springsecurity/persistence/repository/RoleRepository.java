package com.springsecurity.springsecurity.persistence.repository;

import com.springsecurity.springsecurity.persistence.entity.RoleEntity;
import com.springsecurity.springsecurity.utils.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Set<RoleEntity> findByRoleIn(Set<RoleEnum> roles);
}
