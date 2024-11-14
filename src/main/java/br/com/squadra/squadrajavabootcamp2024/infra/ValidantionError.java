package br.com.squadra.squadrajavabootcamp2024.infra;


import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class ValidantionError extends StandardError {

    private String nomeCampo;

}
