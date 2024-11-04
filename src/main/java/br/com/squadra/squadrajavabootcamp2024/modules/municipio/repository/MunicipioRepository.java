package br.com.squadra.squadrajavabootcamp2024.modules.municipio.repository;

import br.com.squadra.squadrajavabootcamp2024.modules.municipio.model.MunicipioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipioRepository extends JpaRepository<MunicipioModel, Long> {


    List<MunicipioModel> findAllByOrderByCodigoMunicipioDesc();
}
