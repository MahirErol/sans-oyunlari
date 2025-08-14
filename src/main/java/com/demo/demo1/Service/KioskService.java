package com.demo.demo1.Service;

import com.demo.demo1.Entity.Bayi;
import com.demo.demo1.Entity.Kiosk;
import com.demo.demo1.Repository.BayiRepository;
import com.demo.demo1.Repository.KioskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KioskService {

    private final KioskRepository kioskRepository;
    private final BayiRepository bayiRepository;

    public KioskService(KioskRepository kioskRepository, BayiRepository bayiRepository) {
        this.kioskRepository = kioskRepository;
        this.bayiRepository = bayiRepository;
    }

    public Kiosk kioskEkle(Long bayiId, Kiosk kiosk) {
        Bayi bayi = bayiRepository.findById(bayiId)
                .orElseThrow(() -> new RuntimeException("Bayi bulunamadı"));

        kiosk.setBayi(bayi);
        return kioskRepository.save(kiosk);
    }

    public List<Kiosk> bayiyeAitKiosklar(Long bayiId) {
        return kioskRepository.findByBayiId(bayiId);
    }

    public void kioskSil(Long kioskId) {
        kioskRepository.deleteById(kioskId);
    }
}
