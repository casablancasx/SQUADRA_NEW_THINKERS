package br.com.squadra.squadrajavabootcamp2024.uf.service;

import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.uf.dto.UfRequestDTO;
import br.com.squadra.squadrajavabootcamp2024.uf.dto.UfResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.uf.mapper.UfMapper;
import br.com.squadra.squadrajavabootcamp2024.uf.model.UfModel;
import br.com.squadra.squadrajavabootcamp2024.uf.repository.UfRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UfService {

    private final UfRepository repository;

    private final UfMapper mapper;


    public List<UfResponseDTO> cadastrarUF(UfRequestDTO requestDTO){
        try {
            UfModel model = mapper.toEntity(requestDTO);
            repository.save(model);
            List<UfModel> todosUfs = repository.findAllByOrderByCodigoUFDesc();
            return todosUfs.stream().map(mapper::toResponseDTO).toList();

        }catch (DataIntegrityViolationException e){
            throw new ResourceAlreadyExistException("Não foi possível incluir UF no banco de dados. Já existe uma UF com a sigla ou nome informado.");
        }
    }

    public List<UfResponseDTO> buscarSomentePorStatus(Integer status) {
        List<UfModel> lista = repository.findByStatusOrderByCodigoUFDesc(status);
        return lista.stream().map(mapper::toResponseDTO).toList();
    }

    public Object buscarPorCodigoUFOrSiglaOrNome(Long codigoUF, String sigla, String nome) {
        Optional<UfModel> optional = repository.findByCodigoUFOrSiglaOrNome(codigoUF, sigla, nome);
        if (optional.isEmpty()){
            return Collections.emptyList();
        }
        return mapper.toResponseDTO(optional.get());
    }

    public List<UfResponseDTO> atualizarUF(UfModel updatedUf){

        try{
            Optional<UfModel> optional = repository.findByCodigoUF(updatedUf.getCodigoUF());
            if (optional.isEmpty()){
                throw new ResourceNotFoundException("O códigoUF("+updatedUf.getCodigoUF()+") não foi encontrado.");
            }

            UfModel modelAtualizado = UfModel.builder().
                    codigoUF(updatedUf.getCodigoUF()).
                    sigla(updatedUf.getSigla()).
                    nome(updatedUf.getNome()).
                    status(updatedUf.getStatus()).
                    build();

            repository.save(modelAtualizado);
            return repository.findAllByOrderByCodigoUFDesc().stream().map(mapper::toResponseDTO).toList();
        }catch (DataIntegrityViolationException e){
            throw new ResourceAlreadyExistException("Não foi possível atualizar UF no banco de dados. Já existe uma UF com a sigla ou nome informado.");
        }
    }
}
