package br.com.squadra.squadrajavabootcamp2024.modules.municipio.service;

import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioRequestDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.mapper.MunicipioMapper;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.repository.MunicipioRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MunicipioService {

    private final MunicipioRepository municipioRepository;

    private final MunicipioMapper mapper;

    public List<MunicipioResponseDTO> cadastrarMunicipio(MunicipioRequestDTO request) {
        try {
            municipioRepository.save(mapper.toEntity(request));
            return municipioRepository.findAllByOrderByCodigoMunicipioDesc().stream()
                    .map(mapper::toReponseDTO)
                    .collect(Collectors.toList());
        }catch (DataIntegrityViolationException e){
            throw new ResourceAlreadyExistException("Não foi possível incluir município no banco de dados. Já existe um município com credenciais informadas.");
        }
    }
}
