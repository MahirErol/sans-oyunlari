package com.demo.demo1.Repository;

import com.demo.demo1.Entity.Kupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KuponRepository extends JpaRepository<Kupon, Long> {

    // Kiosktan oynanan kuponlar
    List<Kupon> findByKioskId(Long kioskId);

    // Bayiden (kasadan) oynanan kuponlar
    List<Kupon> findByBayiIdAndKioskIsNull(Long bayiId);
}
