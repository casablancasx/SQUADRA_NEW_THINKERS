package br.com.squadra.squadrajavabootcamp2024.mappers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.UfCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.UfResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.UfUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.models.UfModel;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UfMapper {

    @Mapping(target = "codigoUF", ignore = true)
    UfModel toEntity(UfCreateDTO requestDTO);

    UfResponseDTO toResponse(UfModel uf);

    void atualizarUF(UfUpdateDTO ufAtualizada, @MappingTarget UfModel ufExistente);
}
