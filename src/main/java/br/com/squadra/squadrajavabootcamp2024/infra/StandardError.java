package br.com.squadra.squadrajavabootcamp2024.infra;

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
public class StandardError {

    private String mensagem;

    private Integer status;
}
