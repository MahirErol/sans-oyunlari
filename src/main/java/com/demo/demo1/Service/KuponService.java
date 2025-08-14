package com.demo.demo1.Service;

import com.demo.demo1.Entity.Bayi;
import com.demo.demo1.Entity.Kiosk;
import com.demo.demo1.Entity.Kupon;
import com.demo.demo1.Repository.BayiRepository;
import com.demo.demo1.Repository.KioskRepository;
import com.demo.demo1.Repository.KuponRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KuponService {

    private final KuponRepository kuponRepository;
    private final KioskRepository kioskRepository;
    private final BayiRepository bayiRepository;

    public KuponService(KuponRepository kuponRepository,
                        KioskRepository kioskRepository,
                        BayiRepository bayiRepository) {
        this.kuponRepository = kuponRepository;
        this.kioskRepository = kioskRepository;
        this.bayiRepository = bayiRepository;
    }

    public Kupon kuponOynaKioskIle(Long kioskId, Kupon kupon) {
        Kiosk kiosk = kioskRepository.findById(kioskId)
                .orElseThrow(() -> new RuntimeException("Kiosk bulunamadı"));

        kupon.setKiosk(kiosk);
        return kuponRepository.save(kupon);
    }

    public Kupon kuponOynaBayiIle(Long bayiId, Kupon kupon) {
        Bayi bayi = bayiRepository.findById(bayiId)
                .orElseThrow(() -> new RuntimeException("Bayi bulunamadı"));

        kupon.setBayi(bayi);
        return kuponRepository.save(kupon);
    }

    public List<Kupon> bayidenOynananKuponlar(Long bayiId) {
        return kuponRepository.findByBayiIdAndKioskIsNull(bayiId);
    }

    public List<Kupon> kioskKuponlari(Long kioskId) {
        return kuponRepository.findByKioskId(kioskId);
    }
}
