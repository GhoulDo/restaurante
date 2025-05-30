package com.proyecto.restaurante.service;

import com.proyecto.restaurante.dto.ClienteDTO;
import com.proyecto.restaurante.model.Cliente;
import com.proyecto.restaurante.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return convertToDTO(cliente);
    }

    public ClienteDTO save(ClienteDTO clienteDTO) {
        Cliente cliente = convertToEntity(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);
        return convertToDTO(savedCliente);
    }

    public ClienteDTO update(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setNombre(clienteDTO.getNombre());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setCorreo(clienteDTO.getCorreo());

        Cliente updatedCliente = clienteRepository.save(cliente);
        return convertToDTO(updatedCliente);
    }

    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    public ClienteDTO findByTelefono(String telefono) {
        Cliente cliente = clienteRepository.findByTelefono(telefono)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return convertToDTO(cliente);
    }

    public List<ClienteDTO> findByNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteDTO> findByNombreContaining(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setTelefono(cliente.getTelefono());
        dto.setCorreo(cliente.getCorreo());
        return dto;
    }

    private Cliente convertToEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        if (dto.getId() != null) {
            cliente.setId(dto.getId());
        }
        cliente.setNombre(dto.getNombre());
        cliente.setTelefono(dto.getTelefono());
        cliente.setCorreo(dto.getCorreo());
        return cliente;
    }
}
