package br.com.squadra.squadrajavabootcamp2024.modules.bairro.service;


import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto.BairroCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto.BairroResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.mapper.BairroMapper;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.repository.BairroRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BairroService {

    private final BairroRepository bairroRepository;

    private final BairroMapper mapper;


    public List<BairroResponseDTO> cadastrarBairro(BairroCreateDTO request) {
        try {
            bairroRepository.save(mapper.toEntity(request));
            return bairroRepository.findAllByOrderByCodigoBairroDesc().stream()
                    .map(mapper::toResponseDTO)
                    .toList();
        }catch (DataIntegrityViolationException e){
            throw new ResourceAlreadyExistException("Não foi possível incluir bairro no banco de dados. Já existe um bairro de nome " + request.getNome() + " cadastrado.");
        }
    }
}
