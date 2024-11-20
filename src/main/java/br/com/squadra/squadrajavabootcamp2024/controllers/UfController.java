package br.com.squadra.squadrajavabootcamp2024.controllers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.UfCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.UfUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.models.UfModel;
import br.com.squadra.squadrajavabootcamp2024.services.UfService;
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
@RequestMapping("/uf")
@RequiredArgsConstructor
public class UfController {

    private final UfService ufService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UfModel> cadastrarUF(@Valid @RequestBody UfCreateDTO requestDTO) {
        return ufService.cadastrarUF(requestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Object buscarPorFiltro(
            @RequestParam(name = "codigoUF", required = false) Long codigoUF,
            @RequestParam(name = "sigla", required = false) String sigla,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "status", required = false) Integer status
    ) {
        return ufService.buscarPorFiltro(codigoUF, sigla, nome, status);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UfModel> atualizarUF(@Valid @RequestBody UfUpdateDTO ufAtualizada) {
        return ufService.atualizarUF(ufAtualizada);
    }

    @DeleteMapping("/{codigoUF}")
    public ResponseEntity<Void> deletarUF(@PathVariable Long codigoUF) {
        ufService.deletarUF(codigoUF);
        return ResponseEntity.noContent().build();
    }

}
