package spring.boot.projetos.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.boot.projetos.demo.dto.ClienteDTO;
import spring.boot.projetos.demo.dto.ClienteResponseDTO;
import spring.boot.projetos.demo.dto.ClienteTelefoneResponseDTO;
import spring.boot.projetos.demo.model.Cliente;
import spring.boot.projetos.demo.model.ClienteTelefone;
import spring.boot.projetos.demo.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clientesRepository;

    public List<ClienteResponseDTO> getAll() {
        return clientesRepository.findAll().stream().map(cliente -> {
            ClienteResponseDTO dto = new ClienteResponseDTO();
            dto.setId(cliente.getId());
            dto.setNome(cliente.getNome());
            List<ClienteTelefoneResponseDTO> telefonesDTO = cliente.getTelefones().stream()
                    .map(telefone -> {
                        ClienteTelefoneResponseDTO telefoneDTO = new ClienteTelefoneResponseDTO();
                        telefoneDTO.setId(telefone.getId());
                        telefoneDTO.setNumero(telefone.getNumero());
                        return telefoneDTO;
                    }).collect(Collectors.toList());
            dto.setTelefones(telefonesDTO);
            return dto;
        }).collect(Collectors.toList());
    }

    public Cliente create(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        if (clienteDTO.getTelefones() != null && clienteDTO.getTelefones().size() > 0) {
            List<ClienteTelefone> telefones = clienteDTO.getTelefones().stream()
                    .map(telefoneDTO -> {
                        ClienteTelefone telefone = new ClienteTelefone();
                        telefone.setNumero(telefoneDTO.getNumero());
                        telefone.setCliente(cliente);
                        return telefone;
                    }).collect(Collectors.toList());
            cliente.setTelefones(telefones);
        }
        clientesRepository.save(cliente);
        return cliente;
    }

    public Cliente update(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.setNome(clienteDTO.getNome());
        if (clienteDTO.getTelefones() != null && clienteDTO.getTelefones().size() > 0) {
            List<ClienteTelefone> telefones = clienteDTO.getTelefones().stream()
                    .map(telefoneDTO -> {
                        ClienteTelefone telefone = new ClienteTelefone();
                        telefone.setNumero(telefoneDTO.getNumero());
                        telefone.setCliente(cliente);
                        return telefone;
                    }).collect(Collectors.toList());
            cliente.setTelefones(telefones);
        }
        return clientesRepository.save(cliente);
    }

    public void delete(Long id) {
        Cliente cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));
        clientesRepository.delete(cliente);
    }

}
