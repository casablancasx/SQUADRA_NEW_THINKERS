package br.com.squadra.squadrajavabootcamp2024.services;


import br.com.squadra.squadrajavabootcamp2024.dtos.create.BairroCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.BairroUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.mappers.BairroMapper;
import br.com.squadra.squadrajavabootcamp2024.models.BairroModel;
import br.com.squadra.squadrajavabootcamp2024.repositories.BairroRepository;
import br.com.squadra.squadrajavabootcamp2024.services.validators.BairroValidator;
import br.com.squadra.squadrajavabootcamp2024.services.validators.MunicipioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BairroService {

    private final BairroRepository bairroRepository;

    private final BairroMapper bairroMapper;

    private final MunicipioValidator municipioValidator;
    private final BairroValidator bairroValidator;

    @Transactional
    public List<BairroModel> cadastrarBairro(BairroCreateDTO request) {

        municipioValidator.verificarSeMunicipioExisteNoBanco(request.getCodigoMunicipio());
        bairroValidator.verificarDuplicidadeDeNome(request.getNome());
        bairroRepository.save(bairroMapper.toEntity(request));
        return bairroRepository.findAllByOrderByCodigoBairroDesc();
    }

    public Object buscarBairroPorFiltro(Long codigoBairro, Long codigoMunicipio, String nome, Integer status) {
        List<BairroModel> listaBairros = bairroRepository.findByFiltro(codigoBairro, codigoMunicipio, nome, status);

        if (codigoBairro != null) {
            return listaBairros.isEmpty() ? List.of() : listaBairros.get(0);
        }

        return listaBairros;
    }

    @Transactional
    public List<BairroModel> atualizarBairro(BairroUpdateDTO bairroAtualizado) {

        municipioValidator.verificarSeMunicipioExisteNoBanco(bairroAtualizado.getCodigoMunicipio());

        bairroValidator.verificarNomeUnicoExcetoParaBairro(bairroAtualizado.getNome(), bairroAtualizado.getCodigoBairro());

        BairroModel bairroExistente = bairroRepository.findById(bairroAtualizado.getCodigoBairro())
                .orElseThrow(() -> new ResourceNotFoundException("Bairro não encontrado de id " + bairroAtualizado.getCodigoBairro() + " não foi encontrado"));

        bairroMapper.atualizarBairro(bairroAtualizado, bairroExistente);
        bairroRepository.save(bairroExistente);

        return bairroRepository.findAllByOrderByCodigoBairroDesc();
    }

    @Transactional
    public List<BairroModel> deletarBairro(Long codigoBairro) {
        BairroModel bairro = bairroRepository.findById(codigoBairro)
                .orElseThrow(() -> new ResourceNotFoundException("Bairro não encontrado."));
        bairroRepository.delete(bairro);
        return bairroRepository.findAllByOrderByCodigoBairroDesc();
    }

}
