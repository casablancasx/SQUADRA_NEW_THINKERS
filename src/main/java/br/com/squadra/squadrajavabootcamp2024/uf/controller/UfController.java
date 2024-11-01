package br.com.squadra.squadrajavabootcamp2024.uf.controller;

import br.com.squadra.squadrajavabootcamp2024.uf.dto.UfRequestDTO;
import br.com.squadra.squadrajavabootcamp2024.uf.dto.UfResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.uf.service.UfService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/uf")
@AllArgsConstructor
public class UfController {

    private final UfService ufService;

    @PostMapping
    public ResponseEntity<List<UfResponseDTO>> cadastrarUF(@Valid @RequestBody UfRequestDTO requestDTO){
        List<UfResponseDTO> listaUF = ufService.cadastrarUF(requestDTO);
        return ResponseEntity.ok(listaUF);
    }
}
