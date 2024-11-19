package br.com.squadra.squadrajavabootcamp2024.services;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.UfCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.UfUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.mappers.UfMapper;
import br.com.squadra.squadrajavabootcamp2024.models.UfModel;
import br.com.squadra.squadrajavabootcamp2024.repositories.UfRepository;
import br.com.squadra.squadrajavabootcamp2024.services.validators.UfValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UfService {

    private final UfRepository ufrepository;

    private final UfMapper ufMapper;

    private final UfValidator ufValidator;

    @Transactional
    public List<UfModel> cadastrarUF(UfCreateDTO requestDTO) {

        ufValidator.verificarDuplicidadeDeNomeOuSigla(requestDTO.getNome(), requestDTO.getSigla());
        UfModel model = ufMapper.toEntity(requestDTO);
        ufrepository.save(model);

        return ufrepository.findAllByOrderByCodigoUFDesc();
    }

    public Object buscarPorFiltro(Long codigoUF, String sigla, String nome, Integer status) {

        List<UfModel> listaUfs = ufrepository.findByFiltro(codigoUF, sigla, nome, status);
        if (retornoDeveriaSerUmUnicoObjeto(codigoUF, sigla, nome)) {
            return listaUfs.isEmpty() ? List.of() : listaUfs.get(0);
        }
        return listaUfs;
    }

    @Transactional
    public List<UfModel> atualizarUF(UfUpdateDTO ufAtualizada) {

        ufValidator.verificarNomeESiglaUnicosExcetoParaUF(ufAtualizada.getNome(), ufAtualizada.getSigla(), ufAtualizada.getCodigoUF());

        UfModel ufExistente = ufrepository.findByCodigoUF(ufAtualizada.getCodigoUF()).orElseThrow(() -> new ResourceNotFoundException("O códigoUF(" + ufAtualizada.getCodigoUF() + ") não foi encontrado."));
        ufMapper.atualizarUF(ufAtualizada, ufExistente);
        ufrepository.save(ufExistente);
        return ufrepository.findAllByOrderByCodigoUFDesc();

    }

    @Transactional
    public List<UfModel> deletarUF(Long codigoUF) {
        UfModel model = ufrepository.findByCodigoUF(codigoUF).orElseThrow(() -> new ResourceNotFoundException("O códigoUF(" + codigoUF + ") não foi encontrado."));
        ufrepository.delete(model);
        return ufrepository.findAllByOrderByCodigoUFDesc();
    }


    private boolean retornoDeveriaSerUmUnicoObjeto(Long codigoUF, String sigla, String nome) {
        return codigoUF != null || sigla != null || nome != null;
    }
}
