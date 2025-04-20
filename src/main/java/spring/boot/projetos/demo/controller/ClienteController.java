package spring.boot.projetos.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.boot.projetos.demo.dto.ClienteDTO;
import spring.boot.projetos.demo.dto.ClienteResponseDTO;
import spring.boot.projetos.demo.model.Cliente;
import spring.boot.projetos.demo.service.ClienteService;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clientesService;

    @GetMapping()
    public List<ClienteResponseDTO> getAll() {
        return clientesService.getAll();
    }

    @PostMapping
    private Cliente create(@RequestBody ClienteDTO clienteDTO) {
        return clientesService.create(clienteDTO);
    }

    @PutMapping("/{id}")
    private Cliente update(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        return clientesService.update(id, clienteDTO);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id) {
        clientesService.delete(id);
    }

}
