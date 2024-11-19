package br.com.squadra.squadrajavabootcamp2024.services.validators;

import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.repositories.UfRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UfValidator {

    private final UfRepository ufRepository;



    public void verificarSeUFexisteNoBanco(Long codigoUF) {
        if (!ufRepository.existsByCodigoUF(codigoUF)) {
            throw new ResourceAlreadyExistException("Não foi encontrado uma UF de cógigo " + codigoUF + " cadastrada.");
        }
    }

    public void verificarDuplicidadeDeNomeOuSigla(String nome, String sigla) {
        if (ufRepository.existsByNome(nome)) {
            throw new ResourceAlreadyExistException("Não foi possível incluir UF no banco de dados. Já existe uma UF de nome " + nome + " cadastrada.");
        }

        if (ufRepository.existsBySigla(sigla)) {
            throw new ResourceAlreadyExistException("Não foi possível incluir UF no banco de dados. Já existe uma UF de sigla " + sigla + " cadastrada.");
        }
    }

    public void verificarNomeESiglaUnicosExcetoParaUF(String nome, String sigla, Long id) {
        if (ufRepository.existsByNomeAndCodigoUFNot(nome, id)) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar UF no banco de dados. Já existe uma UF de nome " + nome + " cadastrada.");
        }

        if (ufRepository.existsBySiglaAndCodigoUFNot(sigla, id)) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar UF no banco de dados. Já existe uma UF de sigla " + sigla + " cadastrada.");
        }
    }


}
