package br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BairroCreateDTO {

    private Long codigoMunicipio;

    private String nome;

    private Integer status;

}
