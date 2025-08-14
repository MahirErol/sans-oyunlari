package com.demo.demo1.Controller;

import com.demo.demo1.Entity.Kupon;
import com.demo.demo1.Service.KuponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kuponlar")
public class KuponController {

    private final KuponService kuponService;

    public KuponController(KuponService kuponService) {
        this.kuponService = kuponService;
    }

    @PostMapping("/kiosk/{kioskId}")
    public ResponseEntity<Kupon> kioskKuponOyna(@PathVariable Long kioskId, @RequestBody Kupon kupon) {
        return ResponseEntity.ok(kuponService.kuponOynaKioskIle(kioskId, kupon));
    }

    @PostMapping("/bayi/{bayiId}")
    public ResponseEntity<Kupon> bayiKuponOyna(@PathVariable Long bayiId, @RequestBody Kupon kupon) {
        return ResponseEntity.ok(kuponService.kuponOynaBayiIle(bayiId, kupon));
    }

    @GetMapping("/bayi/{bayiId}")
    public ResponseEntity<List<Kupon>> bayidenOynananKuponlar(@PathVariable Long bayiId) {
        return ResponseEntity.ok(kuponService.bayidenOynananKuponlar(bayiId));
    }

    @GetMapping("/kiosk/{kioskId}")
    public ResponseEntity<List<Kupon>> kioskKuponlari(@PathVariable Long kioskId) {
        return ResponseEntity.ok(kuponService.kioskKuponlari(kioskId));
    }
}

