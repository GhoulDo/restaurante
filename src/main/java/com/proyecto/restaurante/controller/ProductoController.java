package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.ProductoDTO;
import com.proyecto.restaurante.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        try {
            log.info("GET /api/productos - Obteniendo todos los productos");
            List<ProductoDTO> productos = productoService.findAll();
            log.info("GET /api/productos - Retornando {} productos", productos.size());
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            log.error("Error en GET /api/productos: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ProductoDTO>> getProductosDisponibles() {
        try {
            log.info("GET /api/productos/disponibles - Obteniendo productos con stock");
            List<ProductoDTO> productos = productoService.findProductosDisponibles();
            log.info("GET /api/productos/disponibles - Retornando {} productos disponibles", productos.size());
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            log.error("Error en GET /api/productos/disponibles: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        try {
            log.info("GET /api/productos/{} - Obteniendo producto por ID", id);
            ProductoDTO producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            log.error("Error en GET /api/productos/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoDTO>> getProductosByCategoria(@PathVariable Long categoriaId) {
        try {
            log.info("GET /api/productos/categoria/{} - Obteniendo productos por categoría", categoriaId);
            List<ProductoDTO> productos = productoService.findByCategoria(categoriaId);
            log.info("GET /api/productos/categoria/{} - Retornando {} productos", categoriaId, productos.size());
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            log.error("Error en GET /api/productos/categoria/{}: {}", categoriaId, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarProductos(@RequestParam String nombre) {
        try {
            log.info("GET /api/productos/buscar?nombre={} - Buscando productos", nombre);
            List<ProductoDTO> productos = productoService.findByNombreContaining(nombre);
            log.info("GET /api/productos/buscar - Encontrados {} productos", productos.size());
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            log.error("Error en GET /api/productos/buscar: {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        try {
            log.info("POST /api/productos - Creando nuevo producto: {}", productoDTO.getNombre());
            ProductoDTO nuevoProducto = productoService.save(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (Exception e) {
            log.error("Error en POST /api/productos: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id,
            @Valid @RequestBody ProductoDTO productoDTO) {
        try {
            log.info("PUT /api/productos/{} - Actualizando producto", id);
            ProductoDTO productoActualizado = productoService.update(id, productoDTO);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            log.error("Error en PUT /api/productos/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        try {
            log.info("DELETE /api/productos/{} - Eliminando producto", id);
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error en DELETE /api/productos/{}: {}", id, e.getMessage());
            throw e;
        }
    }
}
