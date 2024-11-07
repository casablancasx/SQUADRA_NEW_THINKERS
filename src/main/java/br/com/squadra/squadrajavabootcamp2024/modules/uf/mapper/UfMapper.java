package br.com.squadra.squadrajavabootcamp2024.modules.uf.mapper;

import br.com.squadra.squadrajavabootcamp2024.modules.uf.dto.UfCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.dto.UfResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.dto.UfUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.uf.model.UfModel;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UfMapper {

    @Mapping(target = "codigoUF", ignore = true)
    @Mapping(target = "municipios", ignore = true)
    UfModel toEntity(UfCreateDTO requestDTO);

    UfResponseDTO toResponseDTO(UfModel model);

    @Mapping(target = "municipios", ignore = true)
    void atualizarUF(UfUpdateDTO ufAtualizada, @MappingTarget UfModel ufExistente);
}
