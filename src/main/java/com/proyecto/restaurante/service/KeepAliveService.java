package com.proyecto.restaurante.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@EnableScheduling
@Slf4j
public class KeepAliveService {

    @Value("${app.base-url:http://localhost:8081}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Ping cada 10 minutos para mantener el servicio activo
    @Scheduled(fixedRate = 600000) // 10 minutos = 600,000 ms
    @Async
    public void keepAlive() {
        try {
            String url = baseUrl + "/health";
            String response = restTemplate.getForObject(url, String.class);
            log.info("Keep-alive ping successful: {}", response);
        } catch (Exception e) {
            log.warn("Keep-alive ping failed: {}", e.getMessage());
        }
    }

    // Ping cada 5 minutos durante horas de trabajo (más frecuente)
    @Scheduled(cron = "0 */5 8-22 * * *") // Cada 5 minutos entre 8 AM y 10 PM
    @Async
    public void keepAliveWorkHours() {
        try {
            String url = baseUrl + "/";
            String response = restTemplate.getForObject(url, String.class);
            log.info("Work hours keep-alive ping successful");
        } catch (Exception e) {
            log.warn("Work hours keep-alive ping failed: {}", e.getMessage());
        }
    }
}
