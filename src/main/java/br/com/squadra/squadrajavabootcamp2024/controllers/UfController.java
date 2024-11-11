package br.com.squadra.squadrajavabootcamp2024.controllers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.UfCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.UfResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.UfUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.services.UfService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/uf")
@AllArgsConstructor
public class UfController {

    private final UfService ufService;

    @PostMapping
    public ResponseEntity<List<UfResponseDTO>> cadastrarUF(@Valid @RequestBody UfCreateDTO requestDTO){
        List<UfResponseDTO> listaUF = ufService.cadastrarUF(requestDTO);
        return ResponseEntity.ok(listaUF);
    }

    @GetMapping
    public ResponseEntity<Object> buscarPorFiltro(
            @RequestParam(name = "codigoUF", required = false) Long codigoUF,
            @RequestParam(name = "sigla", required = false) String sigla,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "status", required = false) Integer status
    ){
        Object response = ufService.buscarPorFiltro(codigoUF, sigla, nome, status);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<List<UfResponseDTO>> atualizarUF(@Valid @RequestBody UfUpdateDTO ufAtualizada){
        List<UfResponseDTO> listaUF = ufService.atualizarUF(ufAtualizada);
        return ResponseEntity.ok(listaUF);
    }

    @DeleteMapping("/{codigoUF}")
    public ResponseEntity<List<UfResponseDTO>> deletarUF(@PathVariable Long codigoUF){
        List<UfResponseDTO> listaUF = ufService.deletarUF(codigoUF);
        return ResponseEntity.ok(listaUF);
    }

}
