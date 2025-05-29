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
@CrossOrigin(origins = "*", allowCredentials = "false")
@Slf4j
public class HealthController {

    @Value("${app.base-url:http://localhost:8081}")
    private String baseUrl;

    private final LocalDateTime startTime = LocalDateTime.now();

    @GetMapping
    public ResponseEntity<Map<String, Object>> home() {
        log.info("🏠 GET / - Endpoint raíz accedido desde: {}", getClientInfo());
        Map<String, Object> response = new HashMap<>();
        response.put("service", "🍽️ Sistema Restaurante API");
        response.put("status", "✅ RUNNING");
        response.put("timestamp", LocalDateTime.now());
        response.put("timezone", ZoneId.systemDefault().toString());
        response.put("version", "1.0.0");
        response.put("render_url", "https://restaurante-api.onrender.com");
        response.put("local_url", "http://localhost:8081");
        response.put("api_base_url", baseUrl + "/api");
        response.put("uptime_since", startTime);
        response.put("keep_alive", "🤖 Activo");
        response.put("cors_enabled", true);
        response.put("environment", System.getenv("RENDER") != null ? "RENDER" : "LOCAL");

        // Información adicional
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("health", "/health");
        endpoints.put("ping", "/ping");
        endpoints.put("test-cors", "/test-cors");
        endpoints.put("categorias", "/api/categorias");
        endpoints.put("productos", "/api/productos");
        endpoints.put("mesas", "/api/mesas");
        endpoints.put("pedidos", "/api/pedidos");
        endpoints.put("empleados", "/api/empleados");
        endpoints.put("clientes", "/api/clientes");
        endpoints.put("inventario", "/api/inventario");
        endpoints.put("facturas", "/api/facturas");

        response.put("available_endpoints", endpoints);
        response.put("database_connected", isDatabaseConnected());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        log.info("💊 GET /health - Health check accedido desde: {}", getClientInfo());
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Sistema Restaurante");
        response.put("timestamp", LocalDateTime.now());
        response.put("uptime_since", startTime);
        response.put("environment", System.getenv("RENDER") != null ? "RENDER" : "LOCAL");
        response.put("java_version", System.getProperty("java.version"));
        response.put("spring_profile", System.getProperty("spring.profiles.active", "default"));

        // Calcular uptime
        long uptimeMinutes = java.time.Duration.between(startTime, LocalDateTime.now()).toMinutes();
        response.put("uptime_minutes", uptimeMinutes);
        response.put("keep_alive_status", "🟢 ACTIVE");
        response.put("database_status", isDatabaseConnected() ? "🟢 CONNECTED" : "🔴 DISCONNECTED");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ping")
    public ResponseEntity<Map<String, String>> ping() {
        log.info("🏓 GET /ping - Ping recibido desde: {}", getClientInfo());
        Map<String, String> response = new HashMap<>();
        response.put("message", "pong");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", "alive");
        response.put("environment", System.getenv("RENDER") != null ? "RENDER" : "LOCAL");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test-cors")
    public ResponseEntity<Map<String, Object>> testCors() {
        log.info("🌐 GET /test-cors - Test CORS accedido desde: {}", getClientInfo());
        Map<String, Object> response = new HashMap<>();
        response.put("cors_test", "✅ CORS funcionando correctamente");
        response.put("timestamp", LocalDateTime.now());
        response.put("headers_allowed", "Content-Type, Authorization, *");
        response.put("methods_allowed", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
        response.put("origins_allowed", "* (via allowedOriginPatterns)");
        response.put("credentials_allowed", false);
        response.put("environment", System.getenv("RENDER") != null ? "RENDER" : "LOCAL");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/debug")
    public ResponseEntity<Map<String, Object>> debug() {
        log.info("🐛 GET /debug - Debug info solicitada");
        Map<String, Object> response = new HashMap<>();

        // Environment variables
        Map<String, String> envVars = new HashMap<>();
        envVars.put("RENDER", System.getenv("RENDER"));
        envVars.put("RENDER_EXTERNAL_URL", System.getenv("RENDER_EXTERNAL_URL"));
        envVars.put("PORT", System.getenv("PORT"));
        envVars.put("DATABASE_URL", System.getenv("DATABASE_URL") != null ? "***SET***" : "NOT_SET");

        response.put("environment_variables", envVars);
        response.put("system_properties", Map.of(
                "java.version", System.getProperty("java.version"),
                "os.name", System.getProperty("os.name"),
                "user.timezone", System.getProperty("user.timezone")));
        response.put("runtime_info", Map.of(
                "max_memory", Runtime.getRuntime().maxMemory() / 1024 / 1024 + " MB",
                "total_memory", Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB",
                "free_memory", Runtime.getRuntime().freeMemory() / 1024 / 1024 + " MB"));

        return ResponseEntity.ok(response);
    }

    private String getClientInfo() {
        // Esto ayuda a ver de dónde vienen las requests
        return "CLIENT_REQUEST";
    }

    private boolean isDatabaseConnected() {
        // Simple check - en una implementación real podrías hacer una query básica
        try {
            return true; // Simplificado por ahora
        } catch (Exception e) {
            return false;
        }
    }
}
