package br.com.squadra.squadrajavabootcamp2024.mappers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.MunicipioCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.MunicipioResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.MunicipioUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.entities.MunicipioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UFMapper.class})
public interface MunicipioMapper {

    @Mapping(target = "codigoMunicipio", ignore = true)
    @Mapping(target = "bairros", ignore = true)
    @Mapping(source = "codigoUF", target = "uf.codigoUF")
    MunicipioEntity toEntity(MunicipioCreateDTO requestDTO);


    MunicipioResponseDTO toReponseDTO(MunicipioEntity entity);

    @Mapping(target = "uf", ignore = true)
    @Mapping(target = "bairros", ignore = true)
    void atualizarMunicipio(MunicipioUpdateDTO municipioAtualizado, @MappingTarget MunicipioEntity municipio);
}
