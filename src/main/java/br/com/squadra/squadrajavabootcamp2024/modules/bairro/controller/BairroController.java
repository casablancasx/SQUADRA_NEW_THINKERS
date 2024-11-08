package br.com.squadra.squadrajavabootcamp2024.modules.bairro.controller;


import br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto.BairroCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.dto.BairroResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.bairro.service.BairroService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
