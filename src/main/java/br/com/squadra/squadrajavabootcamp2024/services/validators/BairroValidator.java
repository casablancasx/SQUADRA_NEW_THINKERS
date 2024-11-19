package br.com.squadra.squadrajavabootcamp2024.services.validators;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.EnderecoCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.repositories.BairroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BairroValidator {

    private final BairroRepository bairroRepository;



    public void verificarDuplicidadeDeNome(String nome) {
        if (bairroRepository.existsByNome(nome)) {
            throw new ResourceAlreadyExistException("Não foi possível incluir bairro no banco de dados. Já existe um bairro de nome " + nome + " cadastrado.");
        }
    }

    public void verificarNomeUnicoExcetoParaBairro(String nome, Long id) {
        if (bairroRepository.existsByNomeAndCodigoBairroNot(nome, id)) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar bairro no banco de dados. Já existe um bairro de nome " + nome + " cadastrado.");
        }
    }

    public void verificarSeBairrosEstaoCadastradosNoBanco(List<EnderecoCreateDTO> enderecoCreate){

        for (EnderecoCreateDTO endereco : enderecoCreate) {
            if (!bairroRepository.existsByCodigoBairro(endereco.getCodigoBairro())) {
                throw new ResourceAlreadyExistException("Não foi possível incluir pessoa no banco de dados. Não existe um bairro de código " + endereco.getCodigoBairro() + " cadastrado.");
            }
        }

    }
}
