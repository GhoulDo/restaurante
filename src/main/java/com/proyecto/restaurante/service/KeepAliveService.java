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

    // Ping cada 5 minutos para mantener el servicio activo en Render
    @Scheduled(fixedRate = 300000) // 5 minutos = 300,000 ms
    @Async
    public void keepAlive() {
        if (isRenderEnvironment()) {
            try {
                String url = getExternalUrl() + "/health";
                log.info("🔄 Keep-alive ping to: {}", url);
                String response = restTemplate.getForObject(url, String.class);
                log.info("✅ Keep-alive ping exitoso - Servicio activo en Render");
            } catch (Exception e) {
                log.warn("⚠️ Keep-alive ping falló: {} - URL: {}", e.getMessage(), getExternalUrl());

                // Intento alternativo con ping
                try {
                    String pingUrl = getExternalUrl() + "/ping";
                    restTemplate.getForObject(pingUrl, String.class);
                    log.info("✅ Keep-alive ping alternativo exitoso");
                } catch (Exception e2) {
                    log.error("❌ Keep-alive ping alternativo también falló: {}", e2.getMessage());
                }
            }
        } else {
            log.debug("🏠 Keep-alive skipped - ambiente local");
        }
    }

    // Ping cada 2 minutos durante horas de trabajo (8 AM - 10 PM)
    @Scheduled(cron = "0 */2 8-22 * * *") // Cada 2 minutos entre 8 AM y 10 PM
    @Async
    public void keepAliveWorkHours() {
        if (isRenderEnvironment()) {
            try {
                String url = getExternalUrl() + "/";
                String response = restTemplate.getForObject(url, String.class);
                log.info("🚀 Keep-alive horario laboral - Servicio optimizado");
            } catch (Exception e) {
                log.warn("⚠️ Keep-alive horario laboral falló: {}", e.getMessage());
            }
        }
    }

    // Auto-ping al inicio de la aplicación
    @Scheduled(initialDelay = 30000, fixedRate = 1200000) // Cada 20 minutos después de 30 segundos
    @Async
    public void warmUpService() {
        if (isRenderEnvironment()) {
            try {
                String url = getExternalUrl() + "/debug";
                restTemplate.getForObject(url, String.class);
                log.info("🌡️ Servicio calentado - Debug info obtenida");
            } catch (Exception e) {
                log.warn("⚠️ Calentamiento de servicio falló: {}", e.getMessage());
            }
        }
    }

    private boolean isRenderEnvironment() {
        return System.getenv("RENDER") != null;
    }

    private String getExternalUrl() {
        String renderUrl = System.getenv("RENDER_EXTERNAL_URL");
        if (renderUrl != null && !renderUrl.isEmpty()) {
            return renderUrl;
        }
        return "https://restaurante-api.onrender.com";
    }
}
