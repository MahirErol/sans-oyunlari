# Şans Oyunları

Spring Boot (Maven) ile geliştirilmiş örnek bir "Şans Oyunları" backend uygulaması. Proje; bayi/kiosk yönetimi, kupon oluşturma ve listeleme, Redis ile caching ve PostgreSQL üzerinde JPA ile veri kalıcılığını içerir.

## İçerik
- **Teknolojiler**: Spring Boot, Spring Web, Spring Data JPA (Hibernate), Spring Cache, Spring Data Redis, PostgreSQL, Maven, Lombok
- **Modüller**:
  - **Bayi**: Bayi CRUD ve ilişkili kiosk/kupon yönetimi
  - **Kiosk**: Bayiye bağlı kiosk oluşturma/silme ve listeleme
  - **Kupon**: Kiosk veya bayiden (kasadan) kupon oynama ve listeleme
  - **Ürün (Cache Örneği)**: Basit bir ürün servisi ile cache davranışının gösterimi
  - **Redis**: Basit key/value set-get-delete uç noktaları

## Gereksinimler
- Java 17+ (önerilir)
- Maven 3.8+
- PostgreSQL 13+
- Redis 6+

## Kurulum
1) Depoyu klonlayın:
```bash
git clone https://github.com/MahirErol/sans-oyunlari.git
cd sans-oyunlari
```

2) PostgreSQL ve Redis’i çalıştırın:
- PostgreSQL için bir veritabanı ve kullanıcı oluşturun (örnek):
```sql
CREATE DATABASE mydb;
CREATE USER admin WITH PASSWORD 'admin';
GRANT ALL PRIVILEGES ON DATABASE mydb TO admin;
```
- Redis’i varsayılan portta (6379) başlatın.

3) Uygulama ayarlarını yapın: `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:postgresql://host.docker.internal:5432/mydb
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.cache.type=redis
```
- Not: Uygulamayı Docker dışında yerel makinede çalıştırıyorsanız `spring.datasource.url` için `host.docker.internal` yerine çoğunlukla `localhost` kullanmanız gerekir:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
```

4) Çalıştırma:
```bash
mvn spring-boot:run
```
- Alternatif:
```bash
mvn clean package
java -jar target/*.jar
```
- Varsayılan port: `http://localhost:8080`

## Veri Modeli (Özet)
- `Bayi { id, isim, adres, telefon, kiosklar[], kasadanOynananKuponlar[] }`
- `Kiosk { id, kioskKodu, konum, bayi, kuponlar[] }`
- `Kupon { id, oynanmaTarihi, tutar, oyunTuru, kiosk?, bayi? }`

## Uç Noktalar
Aşağıdaki örneklerde JSON gövde ve tipik kullanım gösterilmiştir.

### Bayi
- **Bayi ekle**
  - POST `/api/bayiler`
  - Body:
    ```json
    {
      "isim": "Merkez Bayi",
      "adres": "İstanbul",
      "telefon": "+90 555 000 0000"
    }
    ```
- **Tüm bayiler**
  - GET `/api/bayiler`
- **Bayi getir**
  - GET `/api/bayiler/{id}`
- **Bayi sil**
  - DELETE `/api/bayiler/{id}`

### Kiosk
- **Kiosk ekle (bayiye bağlı)**
  - POST `/api/kiosklar/bayi/{bayiId}`
  - Body:
    ```json
    {
      "kioskKodu": "KSK-001",
      "konum": "Kadıköy"
    }
    ```
- **Bayiye ait kiosklar**
  - GET `/api/kiosklar/bayi/{bayiId}`
- **Kiosk sil**
  - DELETE `/api/kiosklar/{id}`

### Kupon
- **Kupon oyna (kiosk ile)**
  - POST `/api/kuponlar/kiosk/{kioskId}`
  - Body:
    ```json
    {
      "oynanmaTarihi": "2025-01-01T12:00:00",
      "tutar": 50.0,
      "oyunTuru": "Sayısal"
    }
    ```
- **Kupon oyna (bayi/kasa ile)**
  - POST `/api/kuponlar/bayi/{bayiId}`
  - Body: (yukarıdakiyle aynı yapı)
- **Bayiden oynanan kuponlar**
  - GET `/api/kuponlar/bayi/{bayiId}`
- **Kioska ait kuponlar**
  - GET `/api/kuponlar/kiosk/{kioskId}`

### Ürün (Cache Örneği)
- Base path: `/products`
- **Getir (cacheable)**: GET `/products/{id}`
- **Güncelle (cache put)**: PUT `/products/{id}` (Body: düz metin ürün adı)
- **Sil (cache evict)**: DELETE `/products/{id}`

Cache davranışı: `ProductService` üzerindeki `@Cacheable`, `@CachePut`, `@CacheEvict` anotasyonları ile ilk GET çağrısında "DB’den ürün alınıyor" logu yazılır; sonraki çağrılarda Redis cache’den döner.

### Redis (Yardımcı Uç Noktalar)
- **Set**: POST `/redis/set/{key}` (Body: değer, düz metin)
- **Get**: GET `/redis/get/{key}`
- **Delete**: DELETE `/redis/delete/{key}`

## Örnek İstekler (curl)
```bash
# Bayi ekle
curl -X POST http://localhost:8080/api/bayiler \
  -H "Content-Type: application/json" \
  -d '{"isim":"Merkez Bayi","adres":"İstanbul","telefon":"+905550000000"}'

# Bayiye kiosk ekle (bayiId=1 varsayımıyla)
curl -X POST http://localhost:8080/api/kiosklar/bayi/1 \
  -H "Content-Type: application/json" \
  -d '{"kioskKodu":"KSK-001","konum":"Kadıköy"}'

# Kiosk ile kupon oyna (kioskId=1)
curl -X POST http://localhost:8080/api/kuponlar/kiosk/1 \
  -H "Content-Type: application/json" \
  -d '{"oynanmaTarihi":"2025-01-01T12:00:00","tutar":50.0,"oyunTuru":"Sayısal"}'

# Cache örneği
curl http://localhost:8080/products/1
curl -X PUT http://localhost:8080/products/1 -H "Content-Type: text/plain" -d "Kalem (Güncel)"
```

## Testler
- `DbConnectionTest` basit bir bağlantı testi yapar, örnek tablo oluşturup satır sayısını kontrol eder. Bu test için veritabanı bağlantısının doğru yapılandırılmış olması gerekir.

## Geliştirme Notları
- `spring.jpa.hibernate.ddl-auto=update` geliştirme için uygundur. Üretimde migration aracı (ör. Flyway/Liquibase) önerilir.
- `spring.datasource.url` ortamınıza göre güncellenmelidir. Docker konteyneri içinden host’a erişimde `host.docker.internal` kullanılabilir; yerel çalışmada genellikle `localhost`.
- Redis için değer serileştirme `GenericJackson2JsonRedisSerializer` ile yapılır; anahtarlar `StringRedisSerializer`.

## Katkı
- Fork → Branch → PR akışını izleyin. Anlamlı commit mesajları ve açıklayıcı PR içerikleri oluşturun.
