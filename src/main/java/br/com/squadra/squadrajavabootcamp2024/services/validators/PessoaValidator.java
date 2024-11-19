package br.com.squadra.squadrajavabootcamp2024.services.validators;

import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PessoaValidator {

    private final PessoaRepository pessoaRepository;

    public void verificarDuplicidadeDeLogin(String login) {
        if (pessoaRepository.existsByLogin(login)) {
            throw new ResourceAlreadyExistException("Não foi possível incluir pessoa no banco de dados. Já existe uma pessoa de login " + login + " cadastrada.");
        }
    }


    public void verificarDuplicidadeDeLoginExcetoParaPessoa(String login, Long id) {
        if (pessoaRepository.existsByLoginAndAndCodigoPessoaNot(login, id)) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar pessoa no banco de dados. Já existe uma pessoa de login " + login + " cadastrada.");
        }
    }
}
