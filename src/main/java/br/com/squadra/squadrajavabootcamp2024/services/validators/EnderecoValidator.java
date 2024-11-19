package br.com.squadra.squadrajavabootcamp2024.services.validators;

import br.com.squadra.squadrajavabootcamp2024.dtos.update.EnderecoUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoValidator {

    private final EnderecoRepository enderecoRepository;



    public void verificarSeEnderecosEstaoCadastradosNoBanco(List<EnderecoUpdateDTO> enderecosAtualizados) {
        for (EnderecoUpdateDTO endereco : enderecosAtualizados) {
            if (!enderecoRepository.existsByCodigoEndereco(endereco.getCodigoEndereco()) && endereco.getCodigoEndereco() != null) {
                throw new ResourceNotFoundException("Não foi possível atualizar pessoa no banco de dados. Não existe um endereço com código " + endereco.getCodigoEndereco() + " cadastrado.");
            }
        }
    }
}
