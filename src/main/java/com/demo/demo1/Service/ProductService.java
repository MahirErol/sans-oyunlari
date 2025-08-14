package com.demo.demo1.Service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    private final Map<Integer, String> products = new HashMap<>();

    public ProductService() {
        products.put(1, "Kalem");
        products.put(2, "Defter");
        products.put(3, "Silgi");
    }

    // Cache’den önce bu metod her çağrıldığında çalışır
    @Cacheable(value = "products", key = "#id")
    public String getProductById(Integer id) {
        System.out.println("DB’den ürün alınıyor: " + id);
        return products.get(id);
    }

    @CachePut(value = "products", key = "#id")
    public String updateProduct(Integer id, String name) {
        products.put(id, name);
        return name;
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Integer id) {
        products.remove(id);
    }
}

