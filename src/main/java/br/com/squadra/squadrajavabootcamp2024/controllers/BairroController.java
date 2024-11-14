package br.com.squadra.squadrajavabootcamp2024.controllers;


import br.com.squadra.squadrajavabootcamp2024.dtos.create.BairroCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.BairroUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.models.BairroModel;
import br.com.squadra.squadrajavabootcamp2024.services.BairroService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bairro")
@AllArgsConstructor
public class BairroController {


    private final BairroService bairroService;

    @PostMapping
    public ResponseEntity<List<BairroModel>> cadastrarBairro(@Valid @RequestBody BairroCreateDTO request) {
        List<BairroModel> listaBairros = bairroService.cadastrarBairro(request);
        return ResponseEntity.ok(listaBairros);
    }

    @GetMapping
    public ResponseEntity<Object> buscarBairroPorFiltro(
            @RequestParam(required = false) Long codigoBairro,
            @RequestParam(required = false) Long codigoMunicipio,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer status
    ) {
        Object response = bairroService.buscarBairroPorFiltro(codigoBairro, codigoMunicipio, nome, status);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<List<BairroModel>> atualizarBairro(@Valid @RequestBody BairroUpdateDTO bairroAtualizado) {
        List<BairroModel> listaBairros = bairroService.atualizarBairro(bairroAtualizado);
        return ResponseEntity.ok(listaBairros);
    }

    @DeleteMapping("/{codigoBairro}")
    public ResponseEntity<List<BairroModel>> deletarBairro(@PathVariable Long codigoBairro) {
        List<BairroModel> listaBairros = bairroService.deletarBairro(codigoBairro);
        return ResponseEntity.ok(listaBairros);
    }


}
