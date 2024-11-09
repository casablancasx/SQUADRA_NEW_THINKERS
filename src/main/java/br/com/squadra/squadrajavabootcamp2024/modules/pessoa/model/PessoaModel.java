package br.com.squadra.squadrajavabootcamp2024.modules.pessoa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoPessoa;

    private String nome;

    private String sobrenome;

    private Integer idade;

    private String login;

    private String senha;

    private Integer status;

    @OneToMany(mappedBy = "pessoaModel", cascade = CascadeType.ALL)
    private List<EnderecoModel> enderecos;
}
