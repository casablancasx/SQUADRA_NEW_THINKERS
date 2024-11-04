package br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MunicipioRequestDTO {

    private Long codigoUF;

    private String nome;

    private Integer status;
}
