package br.com.squadra.squadrajavabootcamp2024.mappers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.UFCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.UFResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.UFUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.entities.UFEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UFMapper {

    @Mapping(target = "codigoUF", ignore = true)
    UFEntity toEntity(UFCreateDTO requestDTO);

    UFResponseDTO toResponse(UFEntity entity);

    void atualizarUF(UFUpdateDTO ufAtualizada, @MappingTarget UFEntity ufExistente);
}
