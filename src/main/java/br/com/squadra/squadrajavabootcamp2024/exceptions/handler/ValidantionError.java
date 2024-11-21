package br.com.squadra.squadrajavabootcamp2024.exceptions.handler;


import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class ValidantionError extends StandardError {

    private String nomeCampo;

}
