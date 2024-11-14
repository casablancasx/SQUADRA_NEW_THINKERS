package br.com.squadra.squadrajavabootcamp2024.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoEndereco;

    private String nomeRua;

    private String numero;

    private String complemento;

    private String cep;

    @ManyToOne
    @JoinColumn(name = "codigoBairro")
    private BairroModel bairro;

    @ManyToOne
    @JoinColumn(name = "codigoPessoa", nullable = false)
    private PessoaModel pessoa;
}
