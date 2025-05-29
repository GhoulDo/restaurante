package com.proyecto.restaurante.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
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
            log.info("✅ Keep-alive ping exitoso - Servicio activo");
        } catch (Exception e) {
            log.warn("⚠️ Keep-alive ping falló: {}", e.getMessage());
        }
    }

    // Ping cada 5 minutos durante horas de trabajo (8 AM - 10 PM)
    @Scheduled(cron = "0 */5 8-22 * * *") // Cada 5 minutos entre 8 AM y 10 PM
    @Async
    public void keepAliveWorkHours() {
        try {
            String url = baseUrl + "/";
            String response = restTemplate.getForObject(url, String.class);
            log.info("🚀 Keep-alive horario laboral - Servicio optimizado");
        } catch (Exception e) {
            log.warn("⚠️ Keep-alive horario laboral falló: {}", e.getMessage());
        }
    }

    // Ping cada 3 minutos durante horas pico (12 PM - 2 PM y 7 PM - 9 PM)
    @Scheduled(cron = "0 */3 12-14,19-21 * * *") // Cada 3 minutos en horas pico
    @Async
    public void keepAlivePeakHours() {
        try {
            String url = baseUrl + "/api/categorias";
            String response = restTemplate.getForObject(url, String.class);
            log.info("🔥 Keep-alive horas pico - Máximo rendimiento");
        } catch (Exception e) {
            log.warn("⚠️ Keep-alive horas pico falló: {}", e.getMessage());
        }
    }

    // Auto-ping al inicio de la aplicación
    @Scheduled(initialDelay = 30000, fixedRate = 1800000) // Cada 30 minutos después de 30 segundos
    @Async
    public void warmUpService() {
        try {
            String url = baseUrl + "/health";
            restTemplate.getForObject(url, String.class);
            log.info("🌡️ Servicio calentado - Listo para recibir requests");
        } catch (Exception e) {
            log.warn("⚠️ Calentamiento de servicio falló: {}", e.getMessage());
        }
    }
}
