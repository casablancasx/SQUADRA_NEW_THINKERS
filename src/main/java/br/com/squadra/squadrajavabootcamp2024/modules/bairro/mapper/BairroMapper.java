package br.com.squadra.squadrajavabootcamp2024.modules.bairro.mapper;

import br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto.BairroCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto.BairroResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto.BairroUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.model.BairroModel;
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
