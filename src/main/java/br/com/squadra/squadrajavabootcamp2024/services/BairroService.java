package br.com.squadra.squadrajavabootcamp2024.services;


import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.dtos.create.BairroCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.BairroResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.BairroUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.mappers.BairroMapper;
import br.com.squadra.squadrajavabootcamp2024.models.BairroModel;
import br.com.squadra.squadrajavabootcamp2024.repositories.BairroRepository;
import br.com.squadra.squadrajavabootcamp2024.repositories.MunicipioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BairroService {

    private final BairroRepository bairroRepository;

    private final MunicipioRepository municipioRepository;

    private final BairroMapper mapper;

    public List<BairroModel> cadastrarBairro(BairroCreateDTO request) {

        if (bairroRepository.existsByNome(request.getNome())) {
            throw new ResourceAlreadyExistException("Não foi possível incluir bairro no banco de dados. Já existe um bairro de nome " + request.getNome() + " cadastrado.");
        }

        bairroRepository.save(mapper.toEntity(request));
        return bairroRepository.findAllByOrderByCodigoBairroDesc();
    }

    public Object buscarBairroPorFiltro(Long codigoBairro, Long codigoMunicipio, String nome, Integer status) {
        List<BairroModel> listaBairros = bairroRepository.findByFiltro(codigoBairro, codigoMunicipio, nome, status);

        if (codigoBairro != null) {
            return listaBairros.isEmpty() ? List.of() : listaBairros.get(0);
        }

        return listaBairros;
    }


    public List<BairroModel> atualizarBairro(BairroUpdateDTO bairroAtualizado) {

        if (!municipioRepository.existsByCodigoMunicipio(bairroAtualizado.getCodigoMunicipio())) {
            throw new ResourceNotFoundException("Não foi encontrado município com o código " + bairroAtualizado.getCodigoMunicipio() + ".");
        }

        if (bairroRepository.existsByNomeAndCodigoBairroNot(bairroAtualizado.getNome(), bairroAtualizado.getCodigoBairro())) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar bairro no banco de dados. Já existe um bairro de nome " + bairroAtualizado.getNome() + " cadastrado.");
        }

        BairroModel bairroExistente = bairroRepository.findById(bairroAtualizado.getCodigoBairro())
                .orElseThrow(() -> new IllegalArgumentException("Bairro não encontrado"));

        mapper.atualizarBairro(bairroAtualizado, bairroExistente);
        bairroRepository.save(bairroExistente);

        return bairroRepository.findAllByOrderByCodigoBairroDesc();
    }
}
