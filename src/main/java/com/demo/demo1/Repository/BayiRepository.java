package com.demo.demo1.Repository;

import com.demo.demo1.Entity.Bayi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BayiRepository extends JpaRepository<Bayi, Long> {
    Optional<Bayi> findByIsim(String isim);
}
