package br.com.squadra.squadrajavabootcamp2024.modules.uf.service;

import br.com.squadra.squadrajavabootcamp2024.exceptions.InvalidArgumentTypeException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.dto.UfRequestDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.dto.UfResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.mapper.UfMapper;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.model.UfModel;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.repository.UfRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
        }catch (MethodArgumentTypeMismatchException e){
            throw new InvalidArgumentTypeException("O códigoUF informado é inválido.");
        }
    }

    public Object buscarPorFiltro(Long codigoUF, String sigla, String nome, Integer status){
        List<UfModel> listaUfs = repository.findElementsByCodigoUFOrSiglaOrNomeOrStatus(codigoUF, sigla, nome, status);
        if (retornoDeveriaSerLista(codigoUF, sigla, nome, status, listaUfs)){
            return listaUfs.stream().map(mapper::toResponseDTO).toList();
        }
        return listaUfs.isEmpty() ? Collections.emptyList() : mapper.toResponseDTO(listaUfs.get(0));
    }


    private boolean retornoDeveriaSerLista(Long codigoUF, String sigla, String nome, Integer status, List<UfModel> listaUfs){
        return (codigoUF == null && sigla == null && nome == null && status != null) || listaUfs.size() > 1;
    }

    public List<UfResponseDTO> atualizarUF(UfModel updatedUf){
        try{
            UfModel uf = repository.findByCodigoUF(updatedUf.getCodigoUF()).orElseThrow(() -> new ResourceNotFoundException("O códigoUF("+updatedUf.getCodigoUF()+") não foi encontrado."));
            mapper.atualizarUF(updatedUf, uf);
            return repository.findAllByOrderByCodigoUFDesc().stream().map(mapper::toResponseDTO).toList();
        }catch (DataIntegrityViolationException e){
            throw new ResourceAlreadyExistException("Não foi possível atualizar UF no banco de dados. Já existe uma UF com a sigla ou nome informado.");
        }
    }

    public List<UfResponseDTO> deletarUF(Long codigoUF) {
        Optional<UfModel> optional = repository.findByCodigoUF(codigoUF);
        if (optional.isEmpty()){
            throw new ResourceNotFoundException("O códigoUF("+codigoUF+") não foi encontrado.");
        }
        repository.delete(optional.get());
        return repository.findAllByOrderByCodigoUFDesc().stream().map(mapper::toResponseDTO).toList();
    }
}
