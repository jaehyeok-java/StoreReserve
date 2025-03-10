package com.project.storereserve.domain.reposiotry;

import com.project.storereserve.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Optional<User> findById(Integer id);

}
