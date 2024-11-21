package br.com.squadra.squadrajavabootcamp2024.repositories;

import br.com.squadra.squadrajavabootcamp2024.entities.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity, Long> {

    boolean existsByLogin(String login);

    boolean existsByLoginAndAndCodigoPessoaNot(String login, Long codigoPessoa);

    @Query(
            "SELECT pessoa FROM PessoaEntity pessoa WHERE (:codigoPessoa IS NULL OR pessoa.codigoPessoa = :codigoPessoa)" +
            "AND (:login IS NULL OR pessoa.login = :login)" +
            "AND (:status IS NULL OR pessoa.status = :status)" +
            "ORDER BY pessoa.codigoPessoa DESC"
    )
    List<PessoaEntity> findByFiltro(
            @Param("codigoPessoa") Long codigoPessoa,
            @Param("login") String login,
            @Param("status") Integer status);

    List<PessoaEntity> findAllByOrderByCodigoPessoaDesc();

}
