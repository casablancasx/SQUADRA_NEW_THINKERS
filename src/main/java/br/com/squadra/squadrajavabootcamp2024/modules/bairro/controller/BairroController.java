package br.com.squadra.squadrajavabootcamp2024.modules.bairro.controller;


import br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto.BairroCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto.BairroResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto.BairroUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.service.BairroService;
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
    public ResponseEntity<List<BairroResponseDTO>> cadastrarBairro(@Valid @RequestBody BairroCreateDTO request) {
        List<BairroResponseDTO> listaBairros = bairroService.cadastrarBairro(request);
        return ResponseEntity.ok(listaBairros);
    }

    @GetMapping
    public ResponseEntity<Object> buscarBairroPorFiltro(
            @RequestParam(required = false) Long codigoBairro,
            @RequestParam(required = false) Long codigoMunicipio,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer status
    )
    {
        Object response = bairroService.buscarBairroPorFiltro(codigoBairro, codigoMunicipio, nome, status);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<List<BairroResponseDTO>> atualizarBairro(@Valid @RequestBody BairroUpdateDTO bairroAtualizado) {
        List<BairroResponseDTO> listaBairros = bairroService.atualizarBairro(bairroAtualizado);
        return ResponseEntity.ok(listaBairros);
    }



}
