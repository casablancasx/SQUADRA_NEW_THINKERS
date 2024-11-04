package br.com.squadra.squadrajavabootcamp2024.uf.mapper;

import br.com.squadra.squadrajavabootcamp2024.uf.dto.UfRequestDTO;
import br.com.squadra.squadrajavabootcamp2024.uf.dto.UfResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.uf.model.UfModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UfMapper {

    @Mapping(target = "codigoUF", ignore = true)
    UfModel toEntity(UfRequestDTO requestDTO);

    UfResponseDTO toResponseDTO(UfModel model);

    void atualizarUF(UfModel updatedUf, UfModel modelAtualizado);
}
