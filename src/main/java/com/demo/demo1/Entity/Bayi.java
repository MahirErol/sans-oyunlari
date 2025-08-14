package com.demo.demo1.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Bayi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isim;
    private String adres;
    private String telefon;

    @OneToMany(mappedBy = "bayi", cascade = CascadeType.ALL)
    private List<Kiosk> kiosklar;

    @OneToMany(mappedBy = "bayi", cascade = CascadeType.ALL)
    private List<Kupon> kasadanOynananKuponlar;
}
