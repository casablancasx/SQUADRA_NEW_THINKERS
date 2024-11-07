package br.com.squadra.squadrajavabootcamp2024.modules.uf.dto;

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
public class UfResponseDTO {

    private Long codigoUF;

    private String sigla;

    private String nome;

    private Integer status;

}
