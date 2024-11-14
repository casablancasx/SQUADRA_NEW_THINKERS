package br.com.squadra.squadrajavabootcamp2024.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UfResponseDTO {

    private Long codigoUF;

    private String sigla;

    private String nome;

    private Integer status;
}
