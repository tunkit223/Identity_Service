package com.TaKiLearnSpringBoot.identity_service.repository;

import com.TaKiLearnSpringBoot.identity_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
