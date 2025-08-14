package com.demo.demo1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DbConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testConnectionAndQuery() throws Exception {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            // Basit tablo oluşturma
            stmt.execute("CREATE TABLE IF NOT EXISTS test_table (id SERIAL PRIMARY KEY, name VARCHAR(50));");

            // Veri ekleme
            stmt.execute("INSERT INTO test_table (name) VALUES ('testname');");

            // Veri okuma
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM test_table;");
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("test_table row count: " + count);
                assertTrue(count > 0);
            }
        }
    }
}
