package com.infinotive.facility.domain.repository;

import com.infinotive.facility.domain.entity.User;
import com.infinotive.facility.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByTenant_IdAndEmailAndActiveTrue(String tenantId, String email);

    List<User> findAllByTenant_IdAndRoleAndActiveTrue(String tenantId, UserRole role);
}
