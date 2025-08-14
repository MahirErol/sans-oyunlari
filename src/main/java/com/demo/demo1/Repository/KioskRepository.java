package com.demo.demo1.Repository;

import com.demo.demo1.Entity.Kiosk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KioskRepository extends JpaRepository<Kiosk, Long> {
    List<Kiosk> findByBayiId(Long bayiId);
}

