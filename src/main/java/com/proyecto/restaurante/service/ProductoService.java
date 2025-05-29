package com.proyecto.restaurante.service;

import com.proyecto.restaurante.dto.ProductoDTO;
import com.proyecto.restaurante.model.Categoria;
import com.proyecto.restaurante.model.Inventario;
import com.proyecto.restaurante.model.Producto;
import com.proyecto.restaurante.repository.CategoriaRepository;
import com.proyecto.restaurante.repository.InventarioRepository;
import com.proyecto.restaurante.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final InventarioRepository inventarioRepository;

    public List<ProductoDTO> findAll() {
        return productoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return convertToDTO(producto);
    }

    public ProductoDTO save(ProductoDTO productoDTO) {
        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCategoria(categoria);

        Producto savedProducto = productoRepository.save(producto);

        // Crear inventario inicial si se proporciona
        if (productoDTO.getCantidadStock() != null) {
            Inventario inventario = new Inventario();
            inventario.setProducto(savedProducto);
            inventario.setCantidadStock(productoDTO.getCantidadStock());
            inventarioRepository.save(inventario);
        }

        return convertToDTO(savedProducto);
    }

    public ProductoDTO update(Long id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCategoria(categoria);

        Producto updatedProducto = productoRepository.save(producto);
        return convertToDTO(updatedProducto);
    }

    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    public List<ProductoDTO> findByCategoria(Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        return productoRepository.findByCategoria(categoria).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> findProductosDisponibles() {
        return productoRepository.findProductosDisponibles().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> findByNombreContaining(String nombre) {
        return productoRepository.findByNombreContaining(nombre).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductoDTO convertToDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());

        if (producto.getCategoria() != null) {
            dto.setCategoriaId(producto.getCategoria().getId());
            dto.setNombreCategoria(producto.getCategoria().getNombre());
        }

        if (producto.getInventario() != null) {
            dto.setCantidadStock(producto.getInventario().getCantidadStock());
        }

        return dto;
    }
}
