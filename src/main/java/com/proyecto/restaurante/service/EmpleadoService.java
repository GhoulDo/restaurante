package com.proyecto.restaurante.service;

import com.proyecto.restaurante.dto.EmpleadoDTO;
import com.proyecto.restaurante.model.Empleado;
import com.proyecto.restaurante.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public List<EmpleadoDTO> findAll() {
        return empleadoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EmpleadoDTO findById(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        return convertToDTO(empleado);
    }

    public EmpleadoDTO save(EmpleadoDTO empleadoDTO) {
        Empleado empleado = convertToEntity(empleadoDTO);
        Empleado savedEmpleado = empleadoRepository.save(empleado);
        return convertToDTO(savedEmpleado);
    }

    public EmpleadoDTO update(Long id, EmpleadoDTO empleadoDTO) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setRol(empleadoDTO.getRol());
        empleado.setTelefono(empleadoDTO.getTelefono());
        empleado.setCorreo(empleadoDTO.getCorreo());

        Empleado updatedEmpleado = empleadoRepository.save(empleado);
        return convertToDTO(updatedEmpleado);
    }

    public void delete(Long id) {
        empleadoRepository.deleteById(id);
    }

    public List<EmpleadoDTO> findByRol(Empleado.RolEmpleado rol) {
        return empleadoRepository.findByRol(rol).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private EmpleadoDTO convertToDTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(empleado.getId());
        dto.setNombre(empleado.getNombre());
        dto.setRol(empleado.getRol());
        dto.setTelefono(empleado.getTelefono());
        dto.setCorreo(empleado.getCorreo());
        return dto;
    }

    private Empleado convertToEntity(EmpleadoDTO dto) {
        Empleado empleado = new Empleado();
        if (dto.getId() != null) {
            empleado.setId(dto.getId());
        }
        empleado.setNombre(dto.getNombre());
        empleado.setRol(dto.getRol());
        empleado.setTelefono(dto.getTelefono());
        empleado.setCorreo(dto.getCorreo());
        return empleado;
    }
}
