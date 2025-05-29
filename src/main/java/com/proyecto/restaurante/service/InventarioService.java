package com.proyecto.restaurante.service;

import com.proyecto.restaurante.dto.InventarioDTO;
import com.proyecto.restaurante.model.Inventario;
import com.proyecto.restaurante.model.Producto;
import com.proyecto.restaurante.repository.InventarioRepository;
import com.proyecto.restaurante.repository.ProductoRepository;
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

    public InventarioDTO findByProductoId(Long productoId) {
        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario para el producto no encontrado"));
        return convertToDTO(inventario);
    }

    public List<InventarioDTO> findByStockBajo(Integer minimo) {
        return inventarioRepository.findByCantidadStockLessThan(minimo).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public InventarioDTO save(InventarioDTO inventarioDTO) {
        // Verificar que el producto existe
        Producto producto = productoRepository.findById(inventarioDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Verificar que no existe ya un inventario para este producto
        if (inventarioRepository.findByProductoId(inventarioDTO.getProductoId()).isPresent()) {
            throw new RuntimeException("Ya existe un registro de inventario para este producto");
        }

        Inventario inventario = new Inventario();
        inventario.setProducto(producto);
        inventario.setCantidadStock(inventarioDTO.getCantidadStock());

        Inventario savedInventario = inventarioRepository.save(inventario);
        log.info("Inventario creado para producto ID: {} con stock: {}",
                producto.getId(), savedInventario.getCantidadStock());

        return convertToDTO(savedInventario);
    }

    public InventarioDTO update(Long id, InventarioDTO inventarioDTO) {
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        // Si se cambia el producto, verificar que existe
        if (!inventario.getProducto().getId().equals(inventarioDTO.getProductoId())) {
            Producto producto = productoRepository.findById(inventarioDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            inventario.setProducto(producto);
        }

        inventario.setCantidadStock(inventarioDTO.getCantidadStock());

        Inventario updatedInventario = inventarioRepository.save(inventario);
        return convertToDTO(updatedInventario);
    }

    public InventarioDTO updateStock(Long productoId, Integer cantidad) {
        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario para el producto no encontrado"));

        inventario.setCantidadStock(cantidad);
        Inventario updatedInventario = inventarioRepository.save(inventario);

        log.info("Stock actualizado para producto ID: {} a cantidad: {}", productoId, cantidad);
        return convertToDTO(updatedInventario);
    }

    public void delete(Long id) {
        if (!inventarioRepository.existsById(id)) {
            throw new RuntimeException("Inventario no encontrado");
        }
        inventarioRepository.deleteById(id);
    }

    private InventarioDTO convertToDTO(Inventario inventario) {
        InventarioDTO dto = new InventarioDTO();
        dto.setId(inventario.getId());
        dto.setProductoId(inventario.getProducto().getId());
        dto.setNombreProducto(inventario.getProducto().getNombre());
        dto.setCantidadStock(inventario.getCantidadStock());
        dto.setFechaActualizacion(inventario.getFechaActualizacion());
        return dto;
    }
}
