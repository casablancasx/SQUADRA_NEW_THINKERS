package br.com.squadra.squadrajavabootcamp2024.repositories;

import br.com.squadra.squadrajavabootcamp2024.entities.MunicipioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipioRepository extends JpaRepository<MunicipioEntity, Long> {


    List<MunicipioEntity> findAllByOrderByCodigoMunicipioDesc();

    @Query(
            "SELECT municipio FROM MunicipioEntity municipio WHERE (:codigoMunicipio IS NULL OR municipio.codigoMunicipio = :codigoMunicipio)" +
            "AND (:codigoUF IS NULL OR municipio.uf.codigoUF = :codigoUF)" +
            "AND (:nome IS NULL OR municipio.nome = :nome)" +
            "AND (:status IS NULL OR municipio.status = :status)" +
            "ORDER BY municipio.codigoMunicipio DESC"
    )
    List<MunicipioEntity> findByFiltro(
            @Param("codigoMunicipio") Long codigoMunicipio,
            @Param("codigoUF") Long codigoUF,
            @Param("nome") String nome,
            @Param("status") Integer status);

    boolean existsByNome(String nome);

    boolean existsByNomeAndCodigoMunicipioNot(String nome, Long codigoMunicipio);

    boolean existsByCodigoMunicipio(Long codigoMunicipio);
}
