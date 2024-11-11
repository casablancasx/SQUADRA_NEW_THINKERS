package br.com.squadra.squadrajavabootcamp2024.modules.pessoa.service;

import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.dto.PessoaCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.dto.PessoaResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.mapper.PessoaMapper;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.model.PessoaModel;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;


    public List<PessoaResponseDTO> cadastrarPessoa(PessoaCreateDTO request) {

        if (pessoaRepository.existsByLogin(request.getLogin())) {
            throw new ResourceAlreadyExistException("Não foi possível incluir pessoa no banco de dados. Já existe uma pessoa de login " + request.getLogin() + " cadastrado.");
        }

        PessoaModel pessoaModel = pessoaMapper.toEntity(request);
        pessoaModel.getEnderecos().forEach(endereco -> endereco.setPessoa(pessoaModel));
        pessoaRepository.save(pessoaModel);

        return pessoaRepository.findAllByOrderByCodigoPessoaDesc().stream()
                .map(pessoaMapper::toResponseDTO)
                .toList();
    }
}

