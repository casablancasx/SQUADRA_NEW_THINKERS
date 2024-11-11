package br.com.squadra.squadrajavabootcamp2024.repositories;

import br.com.squadra.squadrajavabootcamp2024.models.MunicipioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipioRepository extends JpaRepository<MunicipioModel, Long> {


    List<MunicipioModel> findAllByOrderByCodigoMunicipioDesc();

    @Query(
            "SELECT municipio FROM MunicipioModel municipio WHERE (:codigoMunicipio IS NULL OR municipio.codigoMunicipio = :codigoMunicipio)" +
            "AND (:codigoUF IS NULL OR municipio.uf.codigoUF = :codigoUF)" +
            "AND (:nome IS NULL OR municipio.nome = :nome)" +
            "AND (:status IS NULL OR municipio.status = :status)" +
            "ORDER BY municipio.codigoMunicipio DESC"
    )
    List<MunicipioModel> findByFiltro(
            @Param("codigoMunicipio") Long codigoMunicipio,
            @Param("codigoUF") Long codigoUF,
            @Param("nome") String nome,
            @Param("status") Integer status);

    boolean existsByNome(String nome);

    boolean existsByNomeAndCodigoMunicipioNot(String nome, Long codigoMunicipio);
}
