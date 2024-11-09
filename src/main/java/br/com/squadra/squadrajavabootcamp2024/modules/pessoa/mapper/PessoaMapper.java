package br.com.squadra.squadrajavabootcamp2024.modules.pessoa.mapper;


import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.dto.PessoaCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.dto.PesssoaResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.model.PessoaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PessoaMapper {

    @Mapping(target = "codigoPessoa", ignore = true)
    PessoaModel toEntity(PessoaCreateDTO requestDTO);

    @Mapping(target = "enderecos", ignore = true)
    PesssoaResponseDTO toResponseDTO(PessoaModel model);
}
