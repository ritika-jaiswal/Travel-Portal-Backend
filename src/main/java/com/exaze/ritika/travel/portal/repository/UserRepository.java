package com.exaze.ritika.travel.portal.repository;

import com.exaze.ritika.travel.portal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
