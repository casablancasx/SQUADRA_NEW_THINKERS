package br.com.squadra.squadrajavabootcamp2024.mappers;


import br.com.squadra.squadrajavabootcamp2024.dtos.create.PessoaCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.PessoaResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.models.PessoaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;




@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {EnderecoMapper.class})
public interface PessoaMapper {

    @Mapping(target = "codigoPessoa", ignore = true)
    PessoaModel toEntity(PessoaCreateDTO requestDTO);


    @Mapping(source = "enderecos", target = "enderecos")
    PessoaResponseDTO toResponseDTO(PessoaModel model);
}