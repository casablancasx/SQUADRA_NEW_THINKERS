package br.com.squadra.squadrajavabootcamp2024.mappers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.BairroCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.BairroResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.BairroUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.models.BairroModel;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BairroMapper {

    @Mapping(target = "codigoBairro", ignore = true)
    @Mapping(source = "codigoMunicipio", target = "municipio.codigoMunicipio")
    @Mapping(target = "enderecos", ignore = true)
    BairroModel toEntity(BairroCreateDTO requestDTO);

    @Mapping(source = "municipio.codigoMunicipio", target = "codigoMunicipio")
    BairroResponseDTO toResponseDTO(BairroModel model);

    @Mapping(source = "codigoMunicipio", target = "municipio.codigoMunicipio")
    @Mapping(target = "enderecos", ignore = true)
    void atualizar(BairroUpdateDTO bairroAtualizado, @MappingTarget BairroModel bairroExistente);
}
