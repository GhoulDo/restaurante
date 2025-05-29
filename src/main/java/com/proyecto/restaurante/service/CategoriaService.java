package com.proyecto.restaurante.service;

import com.proyecto.restaurante.dto.CategoriaDTO;
import com.proyecto.restaurante.model.Categoria;
import com.proyecto.restaurante.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
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
        if (categoriaRepository.existsByNombre(categoriaDTO.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        Categoria categoria = convertToEntity(categoriaDTO);
        Categoria savedCategoria = categoriaRepository.save(categoria);
        return convertToDTO(savedCategoria);
    }

    public CategoriaDTO update(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (!categoria.getNombre().equals(categoriaDTO.getNombre()) &&
                categoriaRepository.existsByNombre(categoriaDTO.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }

        categoria.setNombre(categoriaDTO.getNombre());
        Categoria updatedCategoria = categoriaRepository.save(categoria);
        return convertToDTO(updatedCategoria);
    }

    public void delete(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (!categoria.getProductos().isEmpty()) {
            throw new RuntimeException("No se puede eliminar una categoría que tiene productos asociados");
        }

        categoriaRepository.deleteById(id);
    }

    private CategoriaDTO convertToDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getProductos().size());
    }

    private Categoria convertToEntity(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());
        categoria.setNombre(dto.getNombre());
        return categoria;
    }
}
