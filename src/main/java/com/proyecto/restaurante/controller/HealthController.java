package com.proyecto.restaurante.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
@Slf4j
public class HealthController {

    @Value("${app.base-url:http://localhost:8081}")
    private String baseUrl;

    private final LocalDateTime startTime = LocalDateTime.now();

    @GetMapping
    public ResponseEntity<Map<String, Object>> home() {
        log.info("GET / - Endpoint raíz accedido");
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
        endpoints.put("ping", "/ping");
        endpoints.put("categorias", "/api/categorias");
        endpoints.put("productos", "/api/productos");
        endpoints.put("mesas", "/api/mesas");
        endpoints.put("pedidos", "/api/pedidos");
        endpoints.put("empleados", "/api/empleados");
        endpoints.put("clientes", "/api/clientes");
        endpoints.put("inventario", "/api/inventario");
        endpoints.put("facturas", "/api/facturas");

        response.put("available_endpoints", endpoints);
        response.put("cors_enabled", true);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        log.info("GET /health - Health check accedido");
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
        log.info("GET /ping - Ping recibido");
        Map<String, String> response = new HashMap<>();
        response.put("message", "pong");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", "alive");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test-cors")
    public ResponseEntity<Map<String, Object>> testCors() {
        log.info("GET /test-cors - Test CORS accedido");
        Map<String, Object> response = new HashMap<>();
        response.put("cors_test", "✅ CORS funcionando correctamente");
        response.put("timestamp", LocalDateTime.now());
        response.put("headers_allowed", "Content-Type, Authorization, *");
        response.put("methods_allowed", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
        response.put("origins_allowed", "*");

        return ResponseEntity.ok(response);
    }
}
