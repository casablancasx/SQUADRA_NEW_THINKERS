package br.com.squadra.squadrajavabootcamp2024.controllers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.MunicipioCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.MunicipioUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.models.MunicipioModel;
import br.com.squadra.squadrajavabootcamp2024.services.MunicipioService;
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
    public ResponseEntity<List<MunicipioModel>> cadastrarMunicipio(@Valid @RequestBody MunicipioCreateDTO requestDTO) {
        List<MunicipioModel> listaMunicipio = municipioService.cadastrarMunicipio(requestDTO);
        return ResponseEntity.ok(listaMunicipio);
    }

    @GetMapping
    public ResponseEntity<Object> buscarPorFiltro(
            @RequestParam(name = "codigoMunicipio", required = false) Long codigoMunicipio,
            @RequestParam(name = "codigoUF", required = false) Long codigoUF,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "status", required = false) Integer status
    ) {
        Object response = municipioService.buscarPorFiltro(codigoMunicipio, codigoUF, nome, status);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<List<MunicipioModel>> atualizarMunicipio(@Valid @RequestBody MunicipioUpdateDTO municipioAtualizado) {
        List<MunicipioModel> listaMunicipio = municipioService.atualizarMunicipio(municipioAtualizado);
        return ResponseEntity.ok(listaMunicipio);
    }

    @DeleteMapping("/{codigoMunicipio}")
    public ResponseEntity<List<MunicipioModel>> deletarMunicipio(@PathVariable Long codigoMunicipio) {
        List<MunicipioModel> listaMunicipio = municipioService.deletarMunicipio(codigoMunicipio);
        return ResponseEntity.ok(listaMunicipio);
    }
}
