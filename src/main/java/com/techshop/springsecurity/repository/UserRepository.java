package com.techshop.springsecurity.repository;

import com.techshop.springsecurity.entity.UserInfo;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Integer> {
    Optional<UserInfo> findByName(String userName);

}
