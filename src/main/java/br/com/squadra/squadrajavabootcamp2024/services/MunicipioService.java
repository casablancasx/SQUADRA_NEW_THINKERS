package br.com.squadra.squadrajavabootcamp2024.services;

import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.dtos.create.MunicipioCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.MunicipioResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.MunicipioUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.mappers.MunicipioMapper;
import br.com.squadra.squadrajavabootcamp2024.models.MunicipioModel;
import br.com.squadra.squadrajavabootcamp2024.repositories.MunicipioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MunicipioService {

    private final MunicipioRepository municipioRepository;

    private final MunicipioMapper mapper;

    @Transactional
    public List<MunicipioResponseDTO> cadastrarMunicipio(MunicipioCreateDTO request) {

        if (isNomeDuplicado(request.getNome())) {
            throw new ResourceAlreadyExistException("Não foi possível incluir município no banco de dados. Já existe um município de nome " + request.getNome() + " cadastrado.");
        }

        municipioRepository.save(mapper.toEntity(request));
        return municipioRepository.findAllByOrderByCodigoMunicipioDesc().stream()
                .map(mapper::toReponseDTO)
                .toList();

    }


    //TODO REFATORAR
    public Object buscarPorFiltro(Long codigoMunicipio, Long codigoUF, String nome, Integer status) {

        List<MunicipioModel> municipios = municipioRepository.findByFiltro(codigoMunicipio, codigoUF, nome, status);
        if (retornoNaoDeveriaSerUmaLista(codigoMunicipio, codigoUF, nome, status)) {
            return municipios.stream()
                    .map(mapper::toReponseDTO)
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Município não encontrado."));
        }

        return municipios.stream()
                .map(mapper::toReponseDTO).toList();
    }

    private boolean retornoNaoDeveriaSerUmaLista(Long codigoMunicipio, Long codigoUF, String nome, Integer status) {
        return codigoMunicipio != null &&
                codigoUF == null &&
                nome == null &&
                status == null;
    }

    public List<MunicipioResponseDTO> atualizarMunicipio(MunicipioUpdateDTO municipioAtualizado) {

        if (municipioRepository.existsByNomeAndCodigoMunicipioNot(municipioAtualizado.getNome(), municipioAtualizado.getCodigoMunicipio())) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar município no banco de dados. Já existe um município de nome " + municipioAtualizado.getNome() + " cadastrado.");
        }

        MunicipioModel municipioExistente = municipioRepository.findById(municipioAtualizado.getCodigoMunicipio())
                .orElseThrow(() -> new ResourceNotFoundException("Município não encontrado."));

        mapper.atualizarMunicipio(municipioAtualizado, municipioExistente);
        municipioRepository.save(municipioExistente);

        return municipioRepository.findAllByOrderByCodigoMunicipioDesc().stream()
                .map(mapper::toReponseDTO).toList();

    }

    public List<MunicipioResponseDTO> deletarMunicipio(Long codigoMunicipio) {
        MunicipioModel municipio = municipioRepository.findById(codigoMunicipio)
                .orElseThrow(() -> new ResourceNotFoundException("Município não encontrado."));
        municipioRepository.delete(municipio);
        return municipioRepository.findAllByOrderByCodigoMunicipioDesc().stream()
                .map(mapper::toReponseDTO)
                .collect(Collectors.toList());
    }

    private boolean isNomeDuplicado(String nome) {
        return municipioRepository.existsByNome(nome);
    }
}
