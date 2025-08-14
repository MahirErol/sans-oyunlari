package com.demo.demo1.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Kupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime oynanmaTarihi;
    private Double tutar;
    private String oyunTuru;

    // Bu kupon kiosk'tan mı oynandı?
    @ManyToOne
    @JoinColumn(name = "kiosk_id", nullable = true)
    private Kiosk kiosk;

    // Bu kupon kasadan mı (bayiden) oynandı?
    @ManyToOne
    @JoinColumn(name = "bayi_id", nullable = true)
    private Bayi bayi;
}
