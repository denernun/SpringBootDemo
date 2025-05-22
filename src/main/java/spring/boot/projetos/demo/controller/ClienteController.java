package spring.boot.projetos.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
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
    private List<ClienteResponseDTO> getAll() {
        List<ClienteResponseDTO> clientes = clientesService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(clientes).getBody();
    }

    @PostMapping
    private ResponseEntity<Cliente> create(@Valid @RequestBody ClienteDTO clienteDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            throw new RuntimeException("Erro de validação: " + errors);
        }
        Cliente cliente = clientesService.create(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PatchMapping("/{id}")
    private Cliente update(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        return clientesService.update(id, clienteDTO);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id) {
        clientesService.delete(id);
    }

}
