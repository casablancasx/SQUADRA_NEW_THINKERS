package br.com.squadra.squadrajavabootcamp2024.modules.municipio.mapper;

import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioRequestDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.model.MunicipioModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MunicipioMapper {

    @Mapping(target = "codigoMunicipio", ignore = true)
    @Mapping(source = "codigoUF", target = "uf.codigoUF")
    MunicipioModel toEntity(MunicipioRequestDTO requestDTO);

    @Mapping(source = "uf.codigoUF", target = "codigoUF")
    MunicipioResponseDTO toReponseDTO(MunicipioModel model);
}
