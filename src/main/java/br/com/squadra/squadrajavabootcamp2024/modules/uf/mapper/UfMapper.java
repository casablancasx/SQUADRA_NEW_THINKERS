package br.com.squadra.squadrajavabootcamp2024.modules.uf.mapper;

import br.com.squadra.squadrajavabootcamp2024.modules.uf.dto.UfRequestDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.dto.UfResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.model.UfModel;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UfMapper {

    @Mapping(target = "codigoUF", ignore = true)
    @Mapping(target = "municipios", ignore = true)
    UfModel toEntity(UfRequestDTO requestDTO);

    UfResponseDTO toResponseDTO(UfModel model);

    void atualizarUF(UfModel updatedUf,@MappingTarget UfModel modelAtualizado);
}
