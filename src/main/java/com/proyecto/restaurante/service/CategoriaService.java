package com.proyecto.restaurante.service;

import com.proyecto.restaurante.dto.CategoriaDTO;
import com.proyecto.restaurante.model.Categoria;
import com.proyecto.restaurante.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        return convertToDTO(categoria);
    }

    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        // Verificar si ya existe una categoría con ese nombre
        if (categoriaRepository.existsByNombre(categoriaDTO.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());

        Categoria savedCategoria = categoriaRepository.save(categoria);
        return convertToDTO(savedCategoria);
    }

    public CategoriaDTO update(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Verificar si ya existe otra categoría con ese nombre
        if (categoriaRepository.existsByNombre(categoriaDTO.getNombre()) &&
                !categoria.getNombre().equals(categoriaDTO.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }

        categoria.setNombre(categoriaDTO.getNombre());

        Categoria updatedCategoria = categoriaRepository.save(categoria);
        return convertToDTO(updatedCategoria);
    }

    public void delete(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (categoria.getCantidadProductos() > 0) {
            throw new RuntimeException("No se puede eliminar una categoría que tiene productos asociados");
        }

        categoriaRepository.deleteById(id);
    }

    private CategoriaDTO convertToDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());

        // Manejo seguro de la cantidad de productos
        try {
            dto.setCantidadProductos(categoria.getCantidadProductos());
        } catch (Exception e) {
            log.warn("Error al obtener cantidad de productos para categoría {}: {}", categoria.getId(), e.getMessage());
            dto.setCantidadProductos(0);
        }

        return dto;
    }
}
