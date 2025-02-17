package com.philately.user.dao;

import com.philately.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> getUserByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);
}
