package br.com.squadra.squadrajavabootcamp2024.modules.pessoa.repository;

import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.model.PessoaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Long> {

    boolean existsByLogin(String login);

    List<PessoaModel> findAllByOrderByCodigoPessoaDesc();

}
