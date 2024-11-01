package br.com.squadra.squadrajavabootcamp2024.uf.repository;

import br.com.squadra.squadrajavabootcamp2024.uf.model.UfModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UfRepository  extends JpaRepository<UfModel, Long> {

    List<UfModel> findAllByOrderByCodigoUFDesc();

    Optional<UfModel> findByCodigoUFOrSiglaOrNome(Long codigoUF, String sigla, String nome);

    List<UfModel> findByStatus(Integer status);
}
