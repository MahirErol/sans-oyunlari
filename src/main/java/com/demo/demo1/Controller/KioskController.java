package com.demo.demo1.Controller;

import com.demo.demo1.Entity.Kiosk;
import com.demo.demo1.Service.KioskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kiosklar")
public class KioskController {

    private final KioskService kioskService;

    public KioskController(KioskService kioskService) {
        this.kioskService = kioskService;
    }

    @PostMapping("/bayi/{bayiId}")
    public ResponseEntity<Kiosk> kioskEkle(@PathVariable Long bayiId, @RequestBody Kiosk kiosk) {
        return ResponseEntity.ok(kioskService.kioskEkle(bayiId, kiosk));
    }

    @GetMapping("/bayi/{bayiId}")
    public ResponseEntity<List<Kiosk>> bayiyeAitKiosklar(@PathVariable Long bayiId) {
        return ResponseEntity.ok(kioskService.bayiyeAitKiosklar(bayiId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kioskSil(@PathVariable Long id) {
        kioskService.kioskSil(id);
        return ResponseEntity.noContent().build();
    }
}

