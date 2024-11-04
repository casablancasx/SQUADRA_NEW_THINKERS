package br.com.squadra.squadrajavabootcamp2024.modules.municipio.controller;

import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioRequestDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.service.MunicipioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/municipio")
@AllArgsConstructor
public class MunicipioController {

    private final MunicipioService municipioService;


    @PostMapping
    public ResponseEntity<List<MunicipioResponseDTO>> cadastrarMunicipio(@Valid @RequestBody MunicipioRequestDTO requestDTO){
        List<MunicipioResponseDTO> listaMunicipio = municipioService.cadastrarMunicipio(requestDTO);
        return ResponseEntity.ok(listaMunicipio);
    }
}
