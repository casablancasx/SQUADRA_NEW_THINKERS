package br.com.squadra.squadrajavabootcamp2024.modules.pessoa.service;

import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.dto.PessoaCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.dto.PessoaResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.mapper.EnderecoMapper;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.mapper.PessoaMapper;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.model.PessoaModel;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;

    private final PessoaMapper pessoaMapper;


    @Transactional
    public List<PessoaResponseDTO> cadastrarPessoa(PessoaCreateDTO request){
        try {

            PessoaModel pessoaModel = pessoaMapper.toEntity(request);
            repository.save(pessoaModel);
            return repository.findAllByOrderByCodigoPessoaDesc().stream().map(pessoaMapper::toResponseDTO).toList();

        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistException("Já existe uma pessoa com o login informado");
        }
    }
}
