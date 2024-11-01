package br.com.squadra.squadrajavabootcamp2024.uf.service;

import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.uf.dto.UfRequestDTO;
import br.com.squadra.squadrajavabootcamp2024.uf.dto.UfResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.uf.mapper.UfMapper;
import br.com.squadra.squadrajavabootcamp2024.uf.model.UfModel;
import br.com.squadra.squadrajavabootcamp2024.uf.repository.UfRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
