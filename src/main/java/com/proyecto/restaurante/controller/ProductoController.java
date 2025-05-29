package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.ProductoDTO;
import com.proyecto.restaurante.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        List<ProductoDTO> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ProductoDTO>> getProductosDisponibles() {
        List<ProductoDTO> productos = productoService.findProductosDisponibles();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        ProductoDTO producto = productoService.findById(id);
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        ProductoDTO nuevoProducto = productoService.save(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id,
            @Valid @RequestBody ProductoDTO productoDTO) {
        ProductoDTO productoActualizado = productoService.update(id, productoDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoDTO>> getProductosByCategoria(@PathVariable Long categoriaId) {
        List<ProductoDTO> productos = productoService.findByCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }
}
