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
public class MunicipioResponseDTO {

    private Long codigoMunicipio;

    private Long codigoUF;

    private String nome;

    private Integer status;

    private UFResponseDTO uf;
}
