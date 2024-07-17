package br.com.smarthint.desafio.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;

@Builder
@Data
@ToString
public class ClienteDTO {
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String tipoPessoa;
    private String cpfCnpj;
    private String inscricaoEstadual;
    private String genero;
    private Date dataNascimento;
    private Date dataCadastro;
    private Boolean bloqueado;
    private String erro;
}
