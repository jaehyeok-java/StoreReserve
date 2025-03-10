package com.project.storereserve.domain.reposiotry;

import com.project.storereserve.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {

    Optional<Store> findByNameAndLocation(String name, String Location);

    List<Store> findByNameContainingOrLocationContaining(String name, String Location);

    Optional<Store> findById(Integer id);
}
