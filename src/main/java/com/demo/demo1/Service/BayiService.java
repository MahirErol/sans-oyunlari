package com.demo.demo1.Service;

import com.demo.demo1.Entity.Bayi;
import com.demo.demo1.Repository.BayiRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BayiService {

    private final BayiRepository bayiRepository;

    public BayiService(BayiRepository bayiRepository) {
        this.bayiRepository = bayiRepository;
    }

    public Bayi bayiEkle(Bayi bayi) {
        return bayiRepository.save(bayi);
    }

    public List<Bayi> tumBayiler() {
        return bayiRepository.findAll();
    }

    public Optional<Bayi> bayiGetir(Long id) {
        return bayiRepository.findById(id);
    }

    public void bayiSil(Long id) {
        bayiRepository.deleteById(id);
    }
}
