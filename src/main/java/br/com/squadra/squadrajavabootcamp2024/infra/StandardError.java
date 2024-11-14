package br.com.squadra.squadrajavabootcamp2024.infra;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardError {

    private String mensagem;

    private Integer status;
}
