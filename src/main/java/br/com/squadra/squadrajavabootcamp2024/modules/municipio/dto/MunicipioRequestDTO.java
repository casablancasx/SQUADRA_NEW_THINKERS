package br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MunicipioRequestDTO {

    @NotNull(message = "O campo codigoUF é obrigatório")
    private Long codigoUF;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @NotNull(message = "O campo status é obrigatório")
    private Integer status;
}
