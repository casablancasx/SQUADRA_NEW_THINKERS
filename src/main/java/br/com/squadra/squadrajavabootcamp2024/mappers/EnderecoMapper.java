package br.com.squadra.squadrajavabootcamp2024.mappers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.EnderecoCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.models.EnderecoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {BairroMapper.class})
public interface EnderecoMapper {

    @Mapping(target = "codigoEndereco", ignore = true)
    @Mapping(target = "pessoa", ignore = true)
    @Mapping(source = "codigoBairro", target = "bairro.codigoBairro")
    EnderecoModel toEntity(EnderecoCreateDTO requestDTO);

}
