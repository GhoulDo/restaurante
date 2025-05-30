package com.proyecto.restaurante.service;

import com.proyecto.restaurante.dto.MesaDTO;
import com.proyecto.restaurante.model.Mesa;
import com.proyecto.restaurante.repository.MesaRepository;
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
public class MesaService {

    private final MesaRepository mesaRepository;

    public List<MesaDTO> findAll() {
        return mesaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MesaDTO findById(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        return convertToDTO(mesa);
    }

    public List<MesaDTO> findByEstado(Mesa.EstadoMesa estado) {
        return mesaRepository.findByEstado(estado).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MesaDTO save(MesaDTO mesaDTO) {
        // Verificar que no existe una mesa con ese número
        if (mesaRepository.existsByNumeroMesa(mesaDTO.getNumeroMesa())) {
            throw new RuntimeException("Ya existe una mesa con ese número");
        }

        Mesa mesa = new Mesa();
        mesa.setNumeroMesa(mesaDTO.getNumeroMesa());
        mesa.setCapacidad(mesaDTO.getCapacidad());
        mesa.setEstado(mesaDTO.getEstado() != null ? mesaDTO.getEstado() : Mesa.EstadoMesa.LIBRE);

        Mesa savedMesa = mesaRepository.save(mesa);
        log.info("Mesa creada: Número {} con capacidad {}", savedMesa.getNumeroMesa(), savedMesa.getCapacidad());

        return convertToDTO(savedMesa);
    }

    public MesaDTO update(Long id, MesaDTO mesaDTO) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        // Verificar que no existe otra mesa con ese número
        if (!mesa.getNumeroMesa().equals(mesaDTO.getNumeroMesa()) &&
                mesaRepository.existsByNumeroMesa(mesaDTO.getNumeroMesa())) {
            throw new RuntimeException("Ya existe una mesa con ese número");
        }

        mesa.setNumeroMesa(mesaDTO.getNumeroMesa());
        mesa.setCapacidad(mesaDTO.getCapacidad());
        mesa.setEstado(mesaDTO.getEstado());

        Mesa updatedMesa = mesaRepository.save(mesa);
        return convertToDTO(updatedMesa);
    }

    public MesaDTO cambiarEstado(Long id, Mesa.EstadoMesa estado) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        mesa.setEstado(estado);
        Mesa updatedMesa = mesaRepository.save(mesa);

        log.info("Estado de mesa {} cambiado a: {}", mesa.getNumeroMesa(), estado);
        return convertToDTO(updatedMesa);
    }

    public void delete(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        // Verificar que la mesa no tiene pedidos activos
        if (mesa.getPedidos() != null && !mesa.getPedidos().isEmpty()) {
            boolean tienePedidosActivos = mesa.getPedidos().stream()
                    .anyMatch(pedido -> pedido.getEstado() != com.proyecto.restaurante.model.Pedido.EstadoPedido.SERVIDO
                            &&
                            pedido.getEstado() != com.proyecto.restaurante.model.Pedido.EstadoPedido.CANCELADO);

            if (tienePedidosActivos) {
                throw new RuntimeException("No se puede eliminar una mesa con pedidos activos");
            }
        }

        mesaRepository.deleteById(id);
    }

    private MesaDTO convertToDTO(Mesa mesa) {
        MesaDTO dto = new MesaDTO();
        dto.setId(mesa.getId());
        dto.setNumeroMesa(mesa.getNumeroMesa());
        dto.setCapacidad(mesa.getCapacidad());
        dto.setEstado(mesa.getEstado());
        return dto;
    }
}
