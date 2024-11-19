package br.com.squadra.squadrajavabootcamp2024.services.validators;

import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.repositories.MunicipioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MunicipioValidator {


    private final MunicipioRepository municipioRepository;


    public void verificarDuplicidadeDeNome(String nome) {
        if (municipioRepository.existsByNome(nome)) {
            throw new ResourceAlreadyExistException("Não foi possível incluir município no banco de dados. Já existe um município de nome " + nome + " cadastrado.");
        }
    }

    public void verificarNomeUnicoExcetoParaMunicipio(String nome, Long id) {
        if (municipioRepository.existsByNomeAndCodigoMunicipioNot(nome, id)) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar município no banco de dados. Já existe um município de nome " + nome + " cadastrado.");
        }
    }

    public void verificarSeMunicipioExisteNoBanco(Long codigoMunicipio) {
        if (!municipioRepository.existsByCodigoMunicipio(codigoMunicipio)) {
            throw new ResourceAlreadyExistException("Não foi possível incluir bairro no banco de dados. Não existe um município de código " + codigoMunicipio + " cadastrado.");
        }
    }


}
