package com.muchiri.jwtimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muchiri.jwtimpl.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
