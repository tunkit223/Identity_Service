package com.TaKiLearnSpringBoot.identity_service.repository;

import com.TaKiLearnSpringBoot.identity_service.entity.InvalidatedToken;
import com.TaKiLearnSpringBoot.identity_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
