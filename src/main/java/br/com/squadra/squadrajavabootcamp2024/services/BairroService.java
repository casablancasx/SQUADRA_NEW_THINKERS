package br.com.squadra.squadrajavabootcamp2024.services;


import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.dtos.create.BairroCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.BairroResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.BairroUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.mappers.BairroMapper;
import br.com.squadra.squadrajavabootcamp2024.models.BairroModel;
import br.com.squadra.squadrajavabootcamp2024.repositories.BairroRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BairroService {

    private final BairroRepository bairroRepository;

    private final BairroMapper mapper;

    @Transactional
    public List<BairroResponseDTO> cadastrarBairro(BairroCreateDTO request) {

        if (isNomeDuplicado(request.getNome())) {
            throw new ResourceAlreadyExistException("Não foi possível incluir bairro no banco de dados. Já existe um bairro de nome " + request.getNome() + " cadastrado.");
        }

        bairroRepository.save(mapper.toEntity(request));
        return bairroRepository.findAllByOrderByCodigoBairroDesc().stream()
                .map(mapper::toResponseDTO)
                .toList();

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


        BairroModel bairroExistente = bairroRepository.findById(bairroAtualizado.getCodigoBairro())
                .orElseThrow(() -> new IllegalArgumentException("Bairro não encontrado"));

        if (bairroRepository.existsByNomeAndCodigoBairroNot(bairroAtualizado.getNome(), bairroAtualizado.getCodigoBairro())) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar bairro no banco de dados. Já existe um bairro de nome " + bairroAtualizado.getNome() + " cadastrado.");
        }

        mapper.atualizar(bairroAtualizado, bairroExistente);
        bairroRepository.save(bairroExistente);

        return bairroRepository.findAllByOrderByCodigoBairroDesc().stream()
                .map(mapper::toResponseDTO)
                .toList();


    }

    private boolean retornoDeveriaSerLista(Long codigoBairro, Long codigoMunicipio, String nome, Integer status) {
        return codigoBairro == null &&
                codigoMunicipio != null ||
                nome != null ||
                status != null;
    }

    private boolean isNomeDuplicado(String nome) {
        return bairroRepository.existsByNome(nome);
    }
}
