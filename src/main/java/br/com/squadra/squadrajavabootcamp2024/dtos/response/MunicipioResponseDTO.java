package br.com.squadra.squadrajavabootcamp2024.dtos.response;

import lombok.*;

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
}
