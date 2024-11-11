package br.com.squadra.squadrajavabootcamp2024.services;

import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.dtos.create.UfCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.UfResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.UfUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.mappers.UfMapper;
import br.com.squadra.squadrajavabootcamp2024.models.UfModel;
import br.com.squadra.squadrajavabootcamp2024.repositories.UfRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UfService {

    private final UfRepository ufrepository;

    private final UfMapper mapper;


    public List<UfModel> cadastrarUF(UfCreateDTO requestDTO) throws ResourceAlreadyExistException {

        if (ufrepository.existsBySiglaOrNome(requestDTO.getSigla(), requestDTO.getNome())) {
            throw new ResourceAlreadyExistException("Não foi possível incluir UF no banco de dados. Já existe uma UF com a sigla ou nome informado.");
        }

        UfModel model = mapper.toEntity(requestDTO);
        ufrepository.save(model);

        return ufrepository.findAllByOrderByCodigoUFDesc();
    }

    public Object buscarPorFiltro(Long codigoUF, String sigla, String nome, Integer status) {
        List<UfModel> listaUfs = ufrepository.findByFiltro(codigoUF, sigla, nome, status);
        if (retornoDeveriaSerLista(codigoUF, sigla, nome, status, listaUfs)) {
            return listaUfs;
        }
        return listaUfs.isEmpty() ? Collections.emptyList() : listaUfs.get(0);
    }


    public List<UfModel> atualizarUF(UfUpdateDTO ufAtualizada) {


        if (ufrepository.existsBySiglaOrNomeAndCodigoUFNot(ufAtualizada.getSigla(), ufAtualizada.getNome(), ufAtualizada.getCodigoUF())) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar UF no banco de dados. Já existe uma UF com a sigla ou nome informado.");
        }

        UfModel ufExistente = ufrepository.findByCodigoUF(ufAtualizada.getCodigoUF()).orElseThrow(() -> new ResourceNotFoundException("O códigoUF(" + ufAtualizada.getCodigoUF() + ") não foi encontrado."));
        mapper.atualizarUF(ufAtualizada, ufExistente);
        ufrepository.save(ufExistente);
        return ufrepository.findAllByOrderByCodigoUFDesc();

    }

    public List<UfModel> deletarUF(Long codigoUF) {
        Optional<UfModel> optional = ufrepository.findByCodigoUF(codigoUF);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("O códigoUF(" + codigoUF + ") não foi encontrado.");
        }
        ufrepository.delete(optional.get());
        return ufrepository.findAllByOrderByCodigoUFDesc();
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
