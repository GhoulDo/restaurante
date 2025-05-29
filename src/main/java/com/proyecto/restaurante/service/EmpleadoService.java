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
        return new EmpleadoDTO(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getRol(),
                empleado.getTelefono(),
                empleado.getCorreo());
    }

    private Empleado convertToEntity(EmpleadoDTO dto) {
        return new Empleado(
                dto.getId(),
                dto.getNombre(),
                dto.getRol(),
                dto.getTelefono(),
                dto.getCorreo());
    }
}
