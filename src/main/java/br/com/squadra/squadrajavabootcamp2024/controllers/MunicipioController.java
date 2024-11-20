package br.com.squadra.squadrajavabootcamp2024.controllers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.MunicipioCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.MunicipioUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.entities.MunicipioEntity;
import br.com.squadra.squadrajavabootcamp2024.services.MunicipioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/municipio")
@RequiredArgsConstructor
public class MunicipioController {

    private final MunicipioService municipioService;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MunicipioEntity> cadastrarMunicipio(@Valid @RequestBody MunicipioCreateDTO requestDTO) {
        return municipioService.cadastrarMunicipio(requestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Object buscarPorFiltro(
            @RequestParam(name = "codigoMunicipio", required = false) Long codigoMunicipio,
            @RequestParam(name = "codigoUF", required = false) Long codigoUF,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "status", required = false) Integer status
    ) {
        return municipioService.buscarPorFiltro(codigoMunicipio, codigoUF, nome, status);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MunicipioEntity> atualizarMunicipio(@Valid @RequestBody MunicipioUpdateDTO municipioAtualizado) {
        return municipioService.atualizarMunicipio(municipioAtualizado);
    }

    @DeleteMapping("/{codigoMunicipio}")
    public ResponseEntity<Void> deletarMunicipio(@PathVariable Long codigoMunicipio) {
        municipioService.deletarMunicipio(codigoMunicipio);
        return ResponseEntity.noContent().build();
    }
}
