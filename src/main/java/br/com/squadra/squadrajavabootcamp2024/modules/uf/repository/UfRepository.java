package br.com.squadra.squadrajavabootcamp2024.modules.uf.repository;

import br.com.squadra.squadrajavabootcamp2024.modules.uf.model.UfModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UfRepository  extends JpaRepository<UfModel, Long> {

    boolean existsBySiglaOrNome(String sigla, String nome);

    boolean existsBySiglaOrNomeAndCodigoUFNot(String sigla, String nome, Long codigoUF);

    List<UfModel> findAllByOrderByCodigoUFDesc();

    @Query(
            "SELECT uf FROM UfModel uf WHERE (:codigoUF IS NULL OR uf.codigoUF = :codigoUF)" +
            "AND (:sigla IS NULL OR uf.sigla = :sigla)" +
            "AND (:nome IS NULL OR uf.nome = :nome)" +
            "AND (:status IS NULL OR uf.status = :status)" +
            "ORDER BY uf.codigoUF DESC"
    )
    List<UfModel> findByFiltro(
            @Param("codigoUF") Long codigoUF,
            @Param("sigla") String sigla,
            @Param("nome") String nome,
            @Param("status") Integer status
    );

    Optional<UfModel> findByCodigoUF(Long codigoUF);
}
