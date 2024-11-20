package br.com.squadra.squadrajavabootcamp2024.mappers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.BairroCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.BairroResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.BairroUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.entities.BairroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {MunicipioMapper.class})
public interface BairroMapper {

    @Mapping(target = "codigoBairro", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(source = "codigoMunicipio", target = "municipio.codigoMunicipio")
    BairroEntity toEntity(BairroCreateDTO requestDTO);

    @Mapping(source = "municipio.codigoMunicipio", target = "codigoMunicipio")
    BairroResponseDTO toResponseDTO(BairroEntity entity);

    @Mapping(source = "codigoMunicipio", target = "municipio.codigoMunicipio")
    @Mapping(target = "enderecos", ignore = true)
    void atualizarBairro(BairroUpdateDTO bairroAtualizado, @MappingTarget BairroEntity bairroExistente);
}
