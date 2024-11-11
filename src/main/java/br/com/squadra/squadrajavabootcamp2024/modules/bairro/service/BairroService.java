package br.com.squadra.squadrajavabootcamp2024.modules.bairro.service;


import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto.BairroCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto.BairroResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto.BairroUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.mapper.BairroMapper;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.model.BairroModel;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.repository.BairroRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BairroService {

    private final BairroRepository bairroRepository;

    private final BairroMapper mapper;

    @Transactional
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

    public Object buscarBairroPorFiltro(Long codigoBairro, Long codigoMunicipio, String nome, Integer status) {

        List<BairroModel> listaBairros = bairroRepository.findByFiltro(codigoBairro, codigoMunicipio, nome, status);

        if (retornoDeveriaSerLista(codigoBairro, codigoMunicipio, nome, status)) {
            return listaBairros.stream()
                    .map(mapper::toResponseDTO)
                    .toList();
        }

        return listaBairros.isEmpty() ? List.of() : mapper.toResponseDTO(listaBairros.get(0));
    }


    public List<BairroResponseDTO> atualizarBairro(BairroUpdateDTO bairroAtualizado) {
        try {

            BairroModel bairroExistente = bairroRepository.findById(bairroAtualizado.getCodigoBairro())
                    .orElseThrow(() -> new IllegalArgumentException("Bairro não encontrado"));

            mapper.atualizar(bairroAtualizado, bairroExistente);
            bairroRepository.save(bairroExistente);

            return bairroRepository.findAllByOrderByCodigoBairroDesc().stream()
                    .map(mapper::toResponseDTO)
                    .toList();

        }catch (DataIntegrityViolationException e){
            throw new ResourceAlreadyExistException("Não foi possível atualizar bairro no banco de dados. Já existe um bairro de nome " + bairroAtualizado.getNome() + " cadastrado.");
        }
    }

    private boolean retornoDeveriaSerLista(Long codigoBairro, Long codigoMunicipio, String nome, Integer status) {
        return  codigoBairro == null &&
                codigoMunicipio != null ||
                nome != null ||
                status != null;
    }
}
