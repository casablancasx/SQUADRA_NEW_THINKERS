package br.com.squadra.squadrajavabootcamp2024.modules.municipio.mapper;

import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.model.MunicipioModel;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MunicipioMapper {

    @Mapping(target = "codigoMunicipio", ignore = true)
    @Mapping(source = "codigoUF", target = "uf.codigoUF")
    @Mapping(target = "bairros", ignore = true)
    MunicipioModel toEntity(MunicipioCreateDTO requestDTO);

    @Mapping(source = "uf.codigoUF", target = "codigoUF")
    MunicipioResponseDTO toReponseDTO(MunicipioModel model);

    @Mapping(target = "uf", ignore = true)
    @Mapping(target = "bairros", ignore = true)
    void atualizarMunicipio(MunicipioUpdateDTO municipioAtualizado, @MappingTarget MunicipioModel municipio);
}
