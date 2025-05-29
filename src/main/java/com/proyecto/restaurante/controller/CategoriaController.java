package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.CategoriaDTO;
import com.proyecto.restaurante.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Validated
@Slf4j
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        try {
            log.info("GET /api/categorias - Obteniendo todas las categorías");
            List<CategoriaDTO> categorias = categoriaService.findAll();
            log.info("GET /api/categorias - Retornando {} categorías", categorias.size());
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            log.error("Error en GET /api/categorias: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable Long id) {
        try {
            log.info("GET /api/categorias/{} - Obteniendo categoría por ID", id);
            CategoriaDTO categoria = categoriaService.findById(id);
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            log.error("Error en GET /api/categorias/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> createCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        try {
            log.info("POST /api/categorias - Creando categoría: {}", categoriaDTO.getNombre());
            CategoriaDTO nuevaCategoria = categoriaService.save(categoriaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
        } catch (Exception e) {
            log.error("Error en POST /api/categorias: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> updateCategoria(@PathVariable Long id,
            @Valid @RequestBody CategoriaDTO categoriaDTO) {
        try {
            log.info("PUT /api/categorias/{} - Actualizando categoría", id);
            CategoriaDTO categoriaActualizada = categoriaService.update(id, categoriaDTO);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (Exception e) {
            log.error("Error en PUT /api/categorias/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        try {
            log.info("DELETE /api/categorias/{} - Eliminando categoría", id);
            categoriaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error en DELETE /api/categorias/{}: {}", id, e.getMessage());
            throw e;
        }
    }
}
