package br.com.squadra.squadrajavabootcamp2024.modules.municipio.controller;

import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.dto.MunicipioUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.municipio.service.MunicipioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/municipio")
@AllArgsConstructor
public class MunicipioController {

    private final MunicipioService municipioService;


    @PostMapping
    public ResponseEntity<List<MunicipioResponseDTO>> cadastrarMunicipio(@Valid @RequestBody MunicipioCreateDTO requestDTO){
        List<MunicipioResponseDTO> listaMunicipio = municipioService.cadastrarMunicipio(requestDTO);
        return ResponseEntity.ok(listaMunicipio);
    }

    @GetMapping
    public ResponseEntity<Object> buscarPorFiltro(
            @RequestParam(name = "codigoMunicipio", required = false) Long codigoMunicipio,
            @RequestParam(name = "codigoUF", required = false) Long codigoUF,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "status", required = false) Integer status
    ){
        Object response = municipioService.buscarPorFiltro(codigoMunicipio, codigoUF, nome, status);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<List<MunicipioResponseDTO>> atualizarMunicipio(@Valid @RequestBody MunicipioUpdateDTO municipioAtualizado){
        List<MunicipioResponseDTO> listaMunicipio = municipioService.atualizarMunicipio(municipioAtualizado);
        return ResponseEntity.ok(listaMunicipio);
    }

    @DeleteMapping("/{codigoMunicipio}")
    public ResponseEntity<List<MunicipioResponseDTO>> deletarMunicipio(@PathVariable Long codigoMunicipio){
        List<MunicipioResponseDTO> listaMunicipio = municipioService.deletarMunicipio(codigoMunicipio);
        return ResponseEntity.ok(listaMunicipio);
    }
}
