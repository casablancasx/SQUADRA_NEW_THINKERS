package br.com.squadra.squadrajavabootcamp2024.repositories;

import br.com.squadra.squadrajavabootcamp2024.entities.BairroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BairroRepository extends JpaRepository<BairroEntity, Long> {

    List<BairroEntity> findAllByOrderByCodigoBairroDesc();

    @Query(
            "SELECT b FROM BairroEntity b WHERE (:codigoBairro IS NULL OR b.codigoBairro = :codigoBairro)" +
                    "AND (:codigoMunicipio IS NULL OR b.municipio.codigoMunicipio = :codigoMunicipio)" +
                    "AND (:nome IS NULL OR b.nome = :nome)" +
                    "AND (:status IS NULL OR b.status = :status)" +
                    "ORDER BY b.codigoBairro DESC"
    )
    List<BairroEntity> findByFiltro(
            @Param("codigoBairro") Long codigoBairro,
            @Param("codigoMunicipio") Long codigoMunicipio,
            @Param("nome") String nome,
            @Param("status") Integer status);

    boolean existsByNome(String nome);

    boolean existsByNomeAndCodigoBairroNot(String nome, Long codigoBairro);

    boolean existsByCodigoBairro(Long codigoBairro);
}
