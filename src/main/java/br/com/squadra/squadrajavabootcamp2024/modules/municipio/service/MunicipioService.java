package br.com.squadra.squadrajavabootcamp2024.modules.municipio.service;

import br.com.squadra.squadrajavabootcamp2024.exceptions.InvalidArgumentTypeException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioRequestDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.mapper.MunicipioMapper;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.model.MunicipioModel;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.repository.MunicipioRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
            throw new ResourceAlreadyExistException("Não foi possível incluir município no banco de dados. Já existe um município de nome " + request.getNome() + " cadastrado.");
        }
    }

    public Object buscarPorFiltro(Long codigoMunicipio, Long codigoUF, String nome, Integer status) {

            List<MunicipioModel> municipios = municipioRepository.findByFiltro(codigoMunicipio, codigoUF, nome, status);
            if (retornoNaoDeveriaSerUmaLista(codigoMunicipio, codigoUF, nome, status)){
                return municipios.stream()
                        .map(mapper::toReponseDTO)
                        .findFirst()
                        .orElseThrow(()-> new ResourceNotFoundException("Município não encontrado."));
            }

            return municipios.stream()
                    .map(mapper::toReponseDTO)
                    .collect(Collectors.toList());
    }

    private boolean retornoNaoDeveriaSerUmaLista(Long codigoMunicipio, Long codigoUF, String nome, Integer status){
        return codigoMunicipio != null &&
                codigoUF == null &&
                nome == null &&
                status == null;
    }

}
