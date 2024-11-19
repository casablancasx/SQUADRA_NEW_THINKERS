package br.com.squadra.squadrajavabootcamp2024.services;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.MunicipioCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.MunicipioUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.mappers.MunicipioMapper;
import br.com.squadra.squadrajavabootcamp2024.models.MunicipioModel;
import br.com.squadra.squadrajavabootcamp2024.repositories.MunicipioRepository;
import br.com.squadra.squadrajavabootcamp2024.services.validators.MunicipioValidator;
import br.com.squadra.squadrajavabootcamp2024.services.validators.UfValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MunicipioService {

    private final MunicipioRepository municipioRepository;

    private final MunicipioMapper municipioMapper;

    private final UfValidator ufValidator;
    private final MunicipioValidator municipioValidator;

    @Transactional
    public List<MunicipioModel> cadastrarMunicipio(MunicipioCreateDTO request) {

        ufValidator.verificarSeUFexisteNoBanco(request.getCodigoUF());
        municipioValidator.verificarDuplicidadeDeNome(request.getNome());

        municipioRepository.save(municipioMapper.toEntity(request));
        return municipioRepository.findAllByOrderByCodigoMunicipioDesc();
    }


    public Object buscarPorFiltro(Long codigoMunicipio, Long codigoUF, String nome, Integer status) {

        List<MunicipioModel> municipios = municipioRepository.findByFiltro(codigoMunicipio, codigoUF, nome, status);

        if (codigoMunicipio != null) {
            return municipios.isEmpty() ? List.of() : municipios.get(0);
        }

        return municipios;
    }

    @Transactional
    public List<MunicipioModel> atualizarMunicipio(MunicipioUpdateDTO municipioAtualizado) {

        ufValidator.verificarSeUFexisteNoBanco(municipioAtualizado.getCodigoUF());
        municipioValidator.verificarNomeUnicoExcetoParaMunicipio(municipioAtualizado.getNome(), municipioAtualizado.getCodigoMunicipio());

        MunicipioModel municipioExistente = municipioRepository.findById(municipioAtualizado.getCodigoMunicipio())
                .orElseThrow(() -> new ResourceNotFoundException("Município não encontrado de id " + municipioAtualizado.getCodigoMunicipio() + " não foi encontrado") );

        municipioMapper.atualizarMunicipio(municipioAtualizado, municipioExistente);
        municipioRepository.save(municipioExistente);

        return municipioRepository.findAllByOrderByCodigoMunicipioDesc();
    }

    @Transactional
    public List<MunicipioModel> deletarMunicipio(Long codigoMunicipio) {
        MunicipioModel municipio = municipioRepository.findById(codigoMunicipio)
                .orElseThrow(() -> new ResourceNotFoundException("Município não encontrado."));
        municipioRepository.delete(municipio);
        return municipioRepository.findAllByOrderByCodigoMunicipioDesc();
    }

}
