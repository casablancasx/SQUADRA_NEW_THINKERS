package br.com.squadra.squadrajavabootcamp2024.controllers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.PessoaCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.PessoaResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.models.PessoaModel;
import br.com.squadra.squadrajavabootcamp2024.services.PessoaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
@AllArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<List<PessoaModel>> cadastrarPessoa(@Valid @RequestBody PessoaCreateDTO request){
        return ResponseEntity.ok(pessoaService.cadastrarPessoa(request));
    }


}
