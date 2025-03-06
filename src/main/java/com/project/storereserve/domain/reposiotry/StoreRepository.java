package com.project.storereserve.domain.reposiotry;

import com.project.storereserve.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {

}
