package br.com.squadra.squadrajavabootcamp2024.modules.bairro.repository;

import br.com.squadra.squadrajavabootcamp2024.modules.bairro.model.BairroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BairroRepository extends JpaRepository<BairroModel,Long> {

    List<BairroModel> findAllByOrderByCodigoBairroDesc();
}
