package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository  extends JpaRepository<Theatre, Long> {

    List<Theatre> findByName(String name);
}
