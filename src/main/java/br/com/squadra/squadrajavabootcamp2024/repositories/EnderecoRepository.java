package br.com.squadra.squadrajavabootcamp2024.repositories;

import br.com.squadra.squadrajavabootcamp2024.models.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel,Long> {

    boolean existsByCodigoEndereco(Long codigoEndereco);
}
