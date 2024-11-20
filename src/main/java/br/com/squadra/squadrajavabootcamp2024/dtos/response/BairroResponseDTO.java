package br.com.squadra.squadrajavabootcamp2024.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BairroResponseDTO {

    private Long codigoBairro;

    private Long codigoMunicipio;

    private String nome;

    private Integer status;

    private MunicipioResponseDTO municipio;
}
