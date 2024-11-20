package br.com.squadra.squadrajavabootcamp2024.services;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.UFCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.UFUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.mappers.UFMapper;
import br.com.squadra.squadrajavabootcamp2024.entities.UFEntity;
import br.com.squadra.squadrajavabootcamp2024.repositories.UfRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UFService {

    private final UfRepository ufrepository;

    private final UFMapper mapper;

    @Transactional
    public List<UFEntity> cadastrarUF(UFCreateDTO requestDTO){

        verificarDuplicidadeDeNomeOuSigla(requestDTO.getNome(), requestDTO.getSigla());
        UFEntity entity = mapper.toEntity(requestDTO);
        ufrepository.save(entity);
        return ufrepository.findAllByOrderByCodigoUFDesc();
    }



    public Object buscarPorFiltro(Long codigoUF, String sigla, String nome, Integer status) {
        List<UFEntity> listaUfs = ufrepository.findByFiltro(codigoUF, sigla, nome, status);

        if (retornoDeveriaSerUmUnicoObjeto(codigoUF, sigla, nome)) {
            return listaUfs.isEmpty() ? List.of() : listaUfs.get(0);
        }

        return listaUfs;
    }

    @Transactional
    public List<UFEntity> atualizarUF(UFUpdateDTO ufAtualizada) {

       verificarDuplicidadeDeNomeOuSiglaExcetoParaUF(ufAtualizada.getNome(), ufAtualizada.getSigla(), ufAtualizada.getCodigoUF());
        UFEntity ufExistente = ufrepository.findByCodigoUF(ufAtualizada.getCodigoUF()).orElseThrow(() -> new ResourceNotFoundException("O códigoUF(" + ufAtualizada.getCodigoUF() + ") não foi encontrado."));
        mapper.atualizarUF(ufAtualizada, ufExistente);
        ufrepository.save(ufExistente);
        return ufrepository.findAllByOrderByCodigoUFDesc();

    }

    @Transactional
    public void deletarUF(Long codigoUF) {
        UFEntity entity = ufrepository.findById(codigoUF).orElseThrow(() -> new ResourceNotFoundException("O códigoUF(" + codigoUF + ") não foi encontrado."));
        ufrepository.delete(entity);
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
