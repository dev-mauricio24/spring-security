package com.springsecurity.springsecurity.persistence.repository;

import com.springsecurity.springsecurity.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
