package br.com.squadra.squadrajavabootcamp2024.mappers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.EnderecoCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.EnderecoResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.EnderecoUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.entities.EnderecoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {BairroMapper.class})
public interface EnderecoMapper {

    @Mapping(target = "codigoEndereco", ignore = true)
    @Mapping(target = "pessoa", ignore = true)
    @Mapping(source = "codigoBairro", target = "bairro.codigoBairro")
    EnderecoEntity toEntity(EnderecoCreateDTO requestDTO);

    @Mapping(source = "bairro.codigoBairro", target = "codigoBairro")
    @Mapping(source = "pessoa.codigoPessoa", target = "codigoPessoa")
    EnderecoResponseDTO toResponseDTO(EnderecoEntity entity);

    @Mapping(source = "codigoPessoa", target = "pessoa.codigoPessoa")
    @Mapping(source = "codigoBairro", target = "bairro.codigoBairro")
    EnderecoEntity mapUpdateToEntity(EnderecoUpdateDTO enderecoAtualizado);

    @Mapping(source = "codigoPessoa", target = "pessoa.codigoPessoa")
    @Mapping(source = "codigoBairro", target = "bairro.codigoBairro")
    void atualizar(EnderecoUpdateDTO enderecoAtualizado, @MappingTarget EnderecoEntity enderecoExistente);

}
