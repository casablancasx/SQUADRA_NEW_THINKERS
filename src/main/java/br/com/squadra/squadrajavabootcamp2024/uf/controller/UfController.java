package br.com.squadra.squadrajavabootcamp2024.uf.controller;

import br.com.squadra.squadrajavabootcamp2024.uf.dto.UfRequestDTO;
import br.com.squadra.squadrajavabootcamp2024.uf.dto.UfResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.uf.service.UfService;
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
    public ResponseEntity<List<UfResponseDTO>> cadastrarUF(@Valid @RequestBody UfRequestDTO requestDTO){
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

        if (isBuscaSomentePorStatus(codigoUF, sigla, nome, status)){
            List<UfResponseDTO> listaUF = ufService.buscarSomentePorStatus(status);
            return ResponseEntity.ok(listaUF);
        }
        Object response = ufService.buscarPorCodigoUFOrSiglaOrNome(codigoUF, sigla, nome);
        return ResponseEntity.ok(response);
    }

    private boolean isBuscaSomentePorStatus(Long codigoUF, String sigla, String nome, Integer status){
        return codigoUF == null && sigla == null && nome == null && status != null;
    }
}
