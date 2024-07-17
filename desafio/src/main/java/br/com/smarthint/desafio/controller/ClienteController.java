package br.com.smarthint.desafio.controller;

import br.com.smarthint.desafio.dto.ClienteDTO;
import br.com.smarthint.desafio.model.Cliente;
import br.com.smarthint.desafio.repository.ClienteRepository;
import br.com.smarthint.desafio.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ClienteService service;

    @GetMapping("/findAll")
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> clientes = new ArrayList<>();
        clientes = (List<Cliente>) clienteRepository.findAll();
        return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.ACCEPTED);
    }

    @PostMapping("/salvar")
    public ResponseEntity<String> salvarCliente(@RequestBody ClienteDTO dto) {
        Cliente cli = mountClient(dto);
        cli.setDataCadastro(Calendar.getInstance().getTime());
        clienteRepository.save(cli);
        return new ResponseEntity<String>("OK", HttpStatus.ACCEPTED);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Cliente>> filtrarCliente(@RequestBody String filtro) {
        List<Cliente> lst = service.findByName(filtro);
        return new ResponseEntity<List<Cliente>>(lst, HttpStatus.ACCEPTED);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<String> alterarCliente(@PathVariable String id, @RequestBody ClienteDTO dto) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);

        if (!optionalCliente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Cliente cliente = mountClient(dto);
        cliente.setId(id);
        clienteRepository.save(cliente);

        return new ResponseEntity<String>("OK", HttpStatus.ACCEPTED);
    }

    private Cliente mountClient(ClienteDTO dto) {
        return Cliente.builder()
                .email(dto.getEmail())
                .bloqueado(dto.getBloqueado())
                .cpfCnpj(dto.getCpfCnpj())
                .dataNascimento(dto.getDataNascimento())
                .nome(dto.getNome())
                .genero(dto.getGenero())
                .inscricaoEstadual(dto.getInscricaoEstadual())
                .telefone(dto.getTelefone())
                .tipoPessoa(dto.getTipoPessoa())
                .build();
    }

    @PatchMapping("/altBloq/{id}")
    public ResponseEntity<String> editarCliente(@PathVariable String id, @RequestBody Boolean bloqueado) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);

        if (!optionalCliente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Cliente cliente = optionalCliente.get();
        cliente.setBloqueado(bloqueado);
        clienteRepository.save(cliente);

        System.out.println(id + " - " + bloqueado);
        return new ResponseEntity<String>("OK", HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable("id") String id) {
        Optional<Cliente> c = clienteRepository.findById(id);
        return c.map(cliente ->
                new ResponseEntity<>(cliente, HttpStatus.ACCEPTED))
                .orElseGet(() -> new ResponseEntity<>(
                Cliente.builder()
                        .erro("Cliente não encontrado")
                        .build()
                , HttpStatus.NOT_FOUND));
    }

}

