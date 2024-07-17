package br.com.smarthint.desafio.service;

import br.com.smarthint.desafio.dto.ClienteDTO;
import br.com.smarthint.desafio.model.Cliente;
import br.com.smarthint.desafio.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClienteService  {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository cr) {
        this.clienteRepository = cr;
    }

    public List<Cliente> findByName(String nome) {
        Iterable<Cliente> lstAll = clienteRepository.findAll();
        List<Cliente> lstNew = new ArrayList<>();

        for (Cliente cl : lstAll) {
            if (cl.getNome().toUpperCase().contains(nome.toUpperCase())) {
                System.out.println(cl);
                lstNew.add(cl);
            }
        }

        return lstNew;
    }
}
