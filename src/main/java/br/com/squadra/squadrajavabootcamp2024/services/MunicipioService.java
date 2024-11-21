package br.com.squadra.squadrajavabootcamp2024.services;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.MunicipioCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.MunicipioUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.mappers.MunicipioMapper;
import br.com.squadra.squadrajavabootcamp2024.entities.MunicipioEntity;
import br.com.squadra.squadrajavabootcamp2024.repositories.MunicipioRepository;
import br.com.squadra.squadrajavabootcamp2024.repositories.UfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MunicipioService {

    private final MunicipioRepository municipioRepository;
    private final UfRepository ufRepository;

    private final MunicipioMapper municipioMapper;

    @Transactional
    public List<MunicipioEntity> cadastrarMunicipio(MunicipioCreateDTO request) {

        verificarSeUfExiste(request.getCodigoUF());

        verificarDuplicidadeDeNome(request.getNome());

        municipioRepository.save(municipioMapper.toEntity(request));

        return municipioRepository.findAllByOrderByCodigoMunicipioDesc();

    }


    public Object buscarPorFiltro(Long codigoMunicipio, Long codigoUF, String nome, Integer status) {

        List<MunicipioEntity> municipios = municipioRepository.findByFiltro(codigoMunicipio, codigoUF, nome, status);

        if (codigoMunicipio != null) {
            return municipios.isEmpty() ? List.of() : municipios.get(0);
        }

        return municipios;
    }

    @Transactional
    public List<MunicipioEntity> atualizarMunicipio(MunicipioUpdateDTO municipioAtualizado) {

        verificarSeUfExiste(municipioAtualizado.getCodigoUF());

        verificarDuplicidadeDeNomeExcetoParaMunicipio(municipioAtualizado.getNome(), municipioAtualizado.getCodigoMunicipio());

        MunicipioEntity municipioExistente = municipioRepository.findById(municipioAtualizado.getCodigoMunicipio())
                .orElseThrow(() -> new ResourceNotFoundException("Município não encontrado."));

        municipioMapper.atualizarMunicipio(municipioAtualizado, municipioExistente);

        municipioRepository.save(municipioExistente);

        return municipioRepository.findAllByOrderByCodigoMunicipioDesc();

    }

    @Transactional
    public void deletarMunicipio(Long codigoMunicipio) {

        MunicipioEntity entity = municipioRepository.findById(codigoMunicipio)
                .orElseThrow(() -> new ResourceNotFoundException("Município não encontrado."));

        municipioRepository.delete(entity);
    }

    private void  verificarDuplicidadeDeNomeExcetoParaMunicipio(String nome, Long codigoMunicipio) {
        if (municipioRepository.existsByNomeAndCodigoMunicipioNot(nome, codigoMunicipio)) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar município no banco de dados. Já existe um município de nome " + nome + " cadastrado.");
        }
    }

    private void verificarDuplicidadeDeNome(String nome) {
        if (municipioRepository.existsByNome(nome)) {
            throw new ResourceAlreadyExistException("Não foi possível incluir município no banco de dados. Já existe um município de nome " + nome + " cadastrado.");
        }
    }

    private void verificarSeUfExiste(Long codigoUF) {
        if (!ufRepository.existsByCodigoUF(codigoUF)) {
            throw new ResourceNotFoundException("Não foi encontrado UF com o código " + codigoUF + ".");
        }
    }


}
