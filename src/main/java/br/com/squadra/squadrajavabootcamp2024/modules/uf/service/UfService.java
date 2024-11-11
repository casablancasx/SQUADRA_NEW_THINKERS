package br.com.squadra.squadrajavabootcamp2024.modules.uf.service;

import br.com.squadra.squadrajavabootcamp2024.exceptions.InvalidArgumentTypeException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.dto.UfCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.dto.UfResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.dto.UfUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.mapper.UfMapper;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.model.UfModel;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.repository.UfRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UfService {

    private final UfRepository repository;

    private final UfMapper mapper;



    @jakarta.transaction.Transactional
    @Transactional
    public List<UfResponseDTO> cadastrarUF(UfCreateDTO requestDTO) throws ResourceAlreadyExistException {

        if (repository.existsBySiglaOrNome(requestDTO.getSigla(), requestDTO.getNome())){
            throw new ResourceAlreadyExistException("Não foi possível incluir UF no banco de dados. Já existe uma UF com a sigla ou nome informado.");
        }

        UfModel model = mapper.toEntity(requestDTO);
        repository.save(model);
        List<UfModel> todosUfs = repository.findAllByOrderByCodigoUFDesc();
        return todosUfs.stream().map(mapper::toResponseDTO).toList();
    }

    public Object buscarPorFiltro(Long codigoUF, String sigla, String nome, Integer status){
        List<UfModel> listaUfs = repository.findByFiltro(codigoUF, sigla, nome, status);
        if (retornoDeveriaSerLista(codigoUF, sigla, nome, status, listaUfs)){
            return listaUfs.stream().map(mapper::toResponseDTO).toList();
        }
        return listaUfs.isEmpty() ? Collections.emptyList() : mapper.toResponseDTO(listaUfs.get(0));
    }


    public List<UfResponseDTO> atualizarUF(UfUpdateDTO ufAtualizada){
        try{
            UfModel ufExistente = repository.findByCodigoUF(ufAtualizada.getCodigoUF()).orElseThrow(() -> new ResourceNotFoundException("O códigoUF("+ufAtualizada.getCodigoUF()+") não foi encontrado."));
            mapper.atualizarUF(ufAtualizada, ufExistente);
            repository.save(ufExistente);
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


    private boolean retornoDeveriaSerLista(Long codigoUF, String sigla, String nome, Integer status, List<UfModel> listaUfs) {
        return somenteStatusNaoEhNulo(codigoUF, sigla, nome, status) || listaUfs.size() > 1;
    }

    private boolean somenteStatusNaoEhNulo(Long codigoUF, String sigla, String nome, Integer status) {
        return codigoUF == null &&
                sigla == null &&
                nome == null &&
                status != null;
    }
}
