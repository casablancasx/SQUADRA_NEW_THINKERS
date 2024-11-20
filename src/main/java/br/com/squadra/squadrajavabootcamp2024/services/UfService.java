package br.com.squadra.squadrajavabootcamp2024.services;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.UfCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.UfUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.mappers.UfMapper;
import br.com.squadra.squadrajavabootcamp2024.models.UfModel;
import br.com.squadra.squadrajavabootcamp2024.repositories.UfRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UfService {

    private final UfRepository ufrepository;

    private final UfMapper mapper;


    public List<UfModel> cadastrarUF(UfCreateDTO requestDTO){

        verificarDuplicidadeDeNomeOuSigla(requestDTO.getNome(), requestDTO.getSigla());
        UfModel model = mapper.toEntity(requestDTO);
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


    public List<UfModel> atualizarUF(UfUpdateDTO ufAtualizada) {

       verificarDuplicidadeDeNomeOuSiglaExcetoParaUF(ufAtualizada.getNome(), ufAtualizada.getSigla(), ufAtualizada.getCodigoUF());
        UfModel ufExistente = ufrepository.findByCodigoUF(ufAtualizada.getCodigoUF()).orElseThrow(() -> new ResourceNotFoundException("O códigoUF(" + ufAtualizada.getCodigoUF() + ") não foi encontrado."));
        mapper.atualizarUF(ufAtualizada, ufExistente);
        ufrepository.save(ufExistente);
        return ufrepository.findAllByOrderByCodigoUFDesc();

    }

    public List<UfModel> deletarUF(Long codigoUF) {
        UfModel entity = ufrepository.findById(codigoUF).orElseThrow(() -> new ResourceNotFoundException("O códigoUF(" + codigoUF + ") não foi encontrado."));
        ufrepository.delete(entity);
        return ufrepository.findAllByOrderByCodigoUFDesc();
    }


    private boolean retornoDeveriaSerUmUnicoObjeto(Long codigoUF, String sigla, String nome) {
        return codigoUF != null || sigla != null || nome != null;
    }


    private void verificarDuplicidadeDeNomeOuSiglaExcetoParaUF( String nome, String sigla,Long codigoUF) {
        if (ufrepository.existsByNomeAndCodigoUFNot(nome, codigoUF)) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar UF no banco de dados. Já existe uma UF de nome " + nome + " cadastrada.");
        }

        if (ufrepository.existsBySiglaAndCodigoUFNot(sigla, codigoUF)) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar UF no banco de dados. Já existe uma UF de sigla " + sigla + " cadastrada.");
        }
    }

    private void verificarDuplicidadeDeNomeOuSigla(String nome, String sigla) {
        if (ufrepository.existsByNome(nome)) {
            throw new ResourceAlreadyExistException("Não foi possível incluir UF no banco de dados. Já existe uma UF de nome " + nome + " cadastrada.");
        }

        if (ufrepository.existsBySigla(sigla)) {
            throw new ResourceAlreadyExistException("Não foi possível incluir UF no banco de dados. Já existe uma UF de sigla " + sigla+ " cadastrada.");
        }
    }
}
