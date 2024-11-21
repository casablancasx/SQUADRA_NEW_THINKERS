package br.com.squadra.squadrajavabootcamp2024.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaResponseDTO {

    private Long codigoPessoa;

    private String nome;

    private String sobrenome;

    private Integer idade;

    private String login;

    private String senha;

    private Integer status;

    private List<EnderecoResponseDTO> enderecos;

}
