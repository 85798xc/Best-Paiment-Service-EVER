package org.example.paymentderviceaplicationii.repository;

import org.example.paymentderviceaplicationii.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    void deleteByUsername(String username);
    void deleteByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
