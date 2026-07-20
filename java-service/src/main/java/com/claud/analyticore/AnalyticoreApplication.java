package com.claud.analyticore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// kguanoluisa, Clase principal Spring Boot del servicio de análisis, sin nuevas variables, 2026-07-17
// kguanoluisa, Funcionamiento: arranca el contenedor Spring, escanea componentes y levanta Tomcat en el puerto configurado, sin nuevas variables, 2026-07-17
@SpringBootApplication
public class AnalyticoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyticoreApplication.class, args);
    }
}
