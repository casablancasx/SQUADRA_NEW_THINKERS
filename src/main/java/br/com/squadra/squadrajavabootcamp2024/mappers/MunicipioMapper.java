package br.com.squadra.squadrajavabootcamp2024.mappers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.MunicipioCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.MunicipioResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.MunicipioUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.models.MunicipioModel;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UfMapper.class})
public interface MunicipioMapper {

    @Mapping(target = "codigoMunicipio", ignore = true)
    @Mapping(target = "bairros", ignore = true)
    @Mapping(source = "codigoUF", target = "uf.codigoUF")
    MunicipioModel toEntity(MunicipioCreateDTO requestDTO);


    MunicipioResponseDTO toReponseDTO(MunicipioModel model);

    @Mapping(target = "uf", ignore = true)
    @Mapping(target = "bairros", ignore = true)
    void atualizarMunicipio(MunicipioUpdateDTO municipioAtualizado, @MappingTarget MunicipioModel municipio);
}
