package com.proyecto.restaurante.service;

import com.proyecto.restaurante.dto.MesaDTO;
import com.proyecto.restaurante.model.Mesa;
import com.proyecto.restaurante.repository.MesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
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

    public MesaDTO save(MesaDTO mesaDTO) {
        if (mesaRepository.existsByNumeroMesa(mesaDTO.getNumeroMesa())) {
            throw new RuntimeException("Ya existe una mesa con ese número");
        }
        Mesa mesa = convertToEntity(mesaDTO);
        Mesa savedMesa = mesaRepository.save(mesa);
        return convertToDTO(savedMesa);
    }

    public MesaDTO update(Long id, MesaDTO mesaDTO) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        if (!mesa.getNumeroMesa().equals(mesaDTO.getNumeroMesa()) &&
                mesaRepository.existsByNumeroMesa(mesaDTO.getNumeroMesa())) {
            throw new RuntimeException("Ya existe una mesa con ese número");
        }

        mesa.setNumeroMesa(mesaDTO.getNumeroMesa());
        mesa.setEstado(mesaDTO.getEstado());

        Mesa updatedMesa = mesaRepository.save(mesa);
        return convertToDTO(updatedMesa);
    }

    public void delete(Long id) {
        mesaRepository.deleteById(id);
    }

    public List<MesaDTO> findByEstado(Mesa.EstadoMesa estado) {
        return mesaRepository.findByEstado(estado).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MesaDTO cambiarEstado(Long id, Mesa.EstadoMesa nuevoEstado) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        mesa.setEstado(nuevoEstado);
        Mesa updatedMesa = mesaRepository.save(mesa);
        return convertToDTO(updatedMesa);
    }

    private MesaDTO convertToDTO(Mesa mesa) {
        return new MesaDTO(
                mesa.getId(),
                mesa.getNumeroMesa(),
                mesa.getEstado());
    }

    private Mesa convertToEntity(MesaDTO dto) {
        return new Mesa(
                dto.getId(),
                dto.getNumeroMesa(),
                dto.getEstado() != null ? dto.getEstado() : Mesa.EstadoMesa.LIBRE);
    }
}
