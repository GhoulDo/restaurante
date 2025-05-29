package com.proyecto.restaurante.service;

import com.proyecto.restaurante.dto.InventarioDTO;
import com.proyecto.restaurante.model.Inventario;
import com.proyecto.restaurante.model.Producto;
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
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;

    public List<InventarioDTO> findAll() {
        return inventarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public InventarioDTO findById(Long id) {
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        return convertToDTO(inventario);
    }

    public InventarioDTO save(InventarioDTO inventarioDTO) {
        Producto producto = productoRepository.findById(inventarioDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Inventario inventario = new Inventario();
        inventario.setProducto(producto);
        inventario.setCantidadStock(inventarioDTO.getCantidadStock());

        Inventario savedInventario = inventarioRepository.save(inventario);
        return convertToDTO(savedInventario);
    }

    public InventarioDTO update(Long id, InventarioDTO inventarioDTO) {
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        inventario.setCantidadStock(inventarioDTO.getCantidadStock());
        Inventario updatedInventario = inventarioRepository.save(inventario);
        return convertToDTO(updatedInventario);
    }

    public InventarioDTO actualizarStock(Long productoId, Integer cantidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Inventario inventario = inventarioRepository.findByProducto(producto)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado para el producto"));

        inventario.setCantidadStock(cantidad);
        Inventario updatedInventario = inventarioRepository.save(inventario);
        return convertToDTO(updatedInventario);
    }

    public List<InventarioDTO> findStockBajo(Integer minimo) {
        return inventarioRepository.findProductosConStockBajo(minimo).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        inventarioRepository.deleteById(id);
    }

    private InventarioDTO convertToDTO(Inventario inventario) {
        return new InventarioDTO(
                inventario.getId(),
                inventario.getProducto().getId(),
                inventario.getProducto().getNombre(),
                inventario.getCantidadStock(),
                inventario.getFechaActualizacion());
    }

    public InventarioDTO findByProductoId(Long productoId) {
        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado para el producto"));
        return convertToDTO(inventario);
    }
}
