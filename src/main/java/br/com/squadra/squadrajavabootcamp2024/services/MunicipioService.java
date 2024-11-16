package br.com.squadra.squadrajavabootcamp2024.services;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.MunicipioCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.MunicipioUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.mappers.MunicipioMapper;
import br.com.squadra.squadrajavabootcamp2024.models.MunicipioModel;
import br.com.squadra.squadrajavabootcamp2024.repositories.MunicipioRepository;
import br.com.squadra.squadrajavabootcamp2024.repositories.UfRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class MunicipioService {

    private final MunicipioRepository municipioRepository;
    private final UfRepository ufRepository;
    private final MunicipioMapper mapper;


    public List<MunicipioModel> cadastrarMunicipio(MunicipioCreateDTO request) {

        validarSeUfEstaCadastradoNoBancoDeDados(request.getCodigoUF());

        if (municipioRepository.existsByNome(request.getNome())) {
            throw new ResourceAlreadyExistException("Não foi possível incluir município no banco de dados. Já existe um município de nome " + request.getNome() + " cadastrado.");
        }

        municipioRepository.save(mapper.toEntity(request));
        return municipioRepository.findAllByOrderByCodigoMunicipioDesc();

    }


    public Object buscarPorFiltro(Long codigoMunicipio, Long codigoUF, String nome, Integer status) {

        List<MunicipioModel> municipios = municipioRepository.findByFiltro(codigoMunicipio, codigoUF, nome, status);

        if (codigoMunicipio != null) {
            return municipios.isEmpty() ? List.of() : municipios.get(0);
        }

        return municipios;
    }

    public List<MunicipioModel> atualizarMunicipio(MunicipioUpdateDTO municipioAtualizado) {

        validarSeUfEstaCadastradoNoBancoDeDados(municipioAtualizado.getCodigoUF());

        if (municipioRepository.existsByNomeAndCodigoMunicipioNot(municipioAtualizado.getNome(), municipioAtualizado.getCodigoMunicipio())) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar município no banco de dados. Já existe um município de nome " + municipioAtualizado.getNome() + " cadastrado.");
        }

        MunicipioModel municipioExistente = municipioRepository.findById(municipioAtualizado.getCodigoMunicipio())
                .orElseThrow(() -> new ResourceNotFoundException("Município não encontrado."));

        mapper.atualizarMunicipio(municipioAtualizado, municipioExistente);
        municipioRepository.save(municipioExistente);

        return municipioRepository.findAllByOrderByCodigoMunicipioDesc();

    }

    public List<MunicipioModel> deletarMunicipio(Long codigoMunicipio) {
        MunicipioModel municipio = municipioRepository.findById(codigoMunicipio)
                .orElseThrow(() -> new ResourceNotFoundException("Município não encontrado."));
        municipioRepository.delete(municipio);
        return municipioRepository.findAllByOrderByCodigoMunicipioDesc();
    }

    private void validarSeUfEstaCadastradoNoBancoDeDados(Long codigoUF) {
        if (!ufRepository.existsByCodigoUF(codigoUF)) {
            throw new ResourceNotFoundException("Não foi encontrado UF com o código " + codigoUF + ".");
        }
    }


}
