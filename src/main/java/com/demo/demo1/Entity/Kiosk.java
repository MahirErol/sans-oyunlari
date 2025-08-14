package com.demo.demo1.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Kiosk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kioskKodu;
    private String konum;

    @ManyToOne
    @JoinColumn(name = "bayi_id")
    private Bayi bayi;

    @OneToMany(mappedBy = "kiosk", cascade = CascadeType.ALL)
    private List<Kupon> kuponlar;
}

