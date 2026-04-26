package org.eusebiu.repository;

import org.eusebiu.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByRole(String role);
    List<User> findByRoleAndApproved(String role, Boolean approved);
    User findByVerificationToken(String token);
}
