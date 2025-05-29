package com.proyecto.restaurante.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HealthController {

    @Value("${app.base-url:http://localhost:8081}")
    private String baseUrl;

    private final LocalDateTime startTime = LocalDateTime.now();

    @GetMapping
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "🍽️ Sistema Restaurante API");
        response.put("status", "✅ RUNNING");
        response.put("timestamp", LocalDateTime.now());
        response.put("timezone", ZoneId.systemDefault().toString());
        response.put("version", "1.0.0");
        response.put("api_base_url", baseUrl + "/api");
        response.put("uptime_since", startTime);
        response.put("keep_alive", "🤖 Activo");

        // Información adicional
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("health", "/health");
        endpoints.put("categorias", "/api/categorias");
        endpoints.put("productos", "/api/productos");
        endpoints.put("mesas", "/api/mesas");
        endpoints.put("pedidos", "/api/pedidos");
        endpoints.put("empleados", "/api/empleados");
        endpoints.put("clientes", "/api/clientes");
        endpoints.put("inventario", "/api/inventario");
        endpoints.put("facturas", "/api/facturas");

        response.put("available_endpoints", endpoints);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Sistema Restaurante");
        response.put("timestamp", LocalDateTime.now());
        response.put("uptime_since", startTime);

        // Calcular uptime
        long uptimeMinutes = java.time.Duration.between(startTime, LocalDateTime.now()).toMinutes();
        response.put("uptime_minutes", uptimeMinutes);
        response.put("keep_alive_status", "🟢 ACTIVE");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ping")
    public ResponseEntity<Map<String, String>> ping() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "pong");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", "alive");
        return ResponseEntity.ok(response);
    }
}
