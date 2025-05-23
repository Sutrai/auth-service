package com.ceawse.authservice.repository;

import com.ceawse.authservice.domain.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {

    @Query("select r from RoleEntity r where r.code = 'USER_SSO' and r.systemCode = 'SSO'")
    RoleEntity getDefaultRole();

    @Query("select r from RoleEntity  r where r.code = 'ADMIN_SSO' and r.systemCode = 'SSO'")
    RoleEntity getAdminRole();
}
