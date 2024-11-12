package br.com.squadra.squadrajavabootcamp2024.dtos.update;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoUpdateDTO {

    private Long codigoEndereco;

    private Long codigoPessoa;

    private Long codigoBairro;

    private String nomeRua;

    private String numero;

    private String complemento;

    private String cep;

}
