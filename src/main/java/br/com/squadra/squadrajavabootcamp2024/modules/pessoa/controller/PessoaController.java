package br.com.squadra.squadrajavabootcamp2024.modules.pessoa.controller;

import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.dto.PessoaCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.dto.PessoaResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.modules.pessoa.service.PessoaService;
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
    public ResponseEntity<List<PessoaResponseDTO>> cadastrarPessoa(@RequestBody PessoaCreateDTO request){
        return ResponseEntity.ok(pessoaService.cadastrarPessoa(request));
    }


}
