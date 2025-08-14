package com.demo.demo1.Controller;

import com.demo.demo1.Entity.Bayi;
import com.demo.demo1.Service.BayiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bayiler")
public class BayiController {

    private final BayiService bayiService;

    public BayiController(BayiService bayiService) {
        this.bayiService = bayiService;
    }

    @PostMapping
    public ResponseEntity<Bayi> bayiEkle(@RequestBody Bayi bayi) {
        return ResponseEntity.ok(bayiService.bayiEkle(bayi));
    }

    @GetMapping
    public ResponseEntity<List<Bayi>> tumBayiler() {
        return ResponseEntity.ok(bayiService.tumBayiler());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bayi> bayiGetir(@PathVariable Long id) {
        return bayiService.bayiGetir(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> bayiSil(@PathVariable Long id) {
        bayiService.bayiSil(id);
        return ResponseEntity.noContent().build();
    }
}
