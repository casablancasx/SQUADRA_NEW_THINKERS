package br.com.squadra.squadrajavabootcamp2024.controllers;


import br.com.squadra.squadrajavabootcamp2024.dtos.create.BairroCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.BairroUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.models.BairroModel;
import br.com.squadra.squadrajavabootcamp2024.services.BairroService;
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
@RequestMapping("/bairro")
@RequiredArgsConstructor
public class BairroController {


    private final BairroService bairroService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BairroModel> cadastrarBairro(@Valid @RequestBody BairroCreateDTO request) {
        return bairroService.cadastrarBairro(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Object buscarBairroPorFiltro(
            @RequestParam(required = false) Long codigoBairro,
            @RequestParam(required = false) Long codigoMunicipio,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer status
    ) {
        return bairroService.buscarBairroPorFiltro(codigoBairro, codigoMunicipio, nome, status);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BairroModel> atualizarBairro(@Valid @RequestBody BairroUpdateDTO bairroAtualizado) {
        return bairroService.atualizarBairro(bairroAtualizado);
    }

    @DeleteMapping("/{codigoBairro}")
    public ResponseEntity<Void> deletarBairro(@PathVariable Long codigoBairro) {
        bairroService.deletarBairro(codigoBairro);
        return ResponseEntity.noContent().build();
    }


}
