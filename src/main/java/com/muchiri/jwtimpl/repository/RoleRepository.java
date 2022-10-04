package com.muchiri.jwtimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muchiri.jwtimpl.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
