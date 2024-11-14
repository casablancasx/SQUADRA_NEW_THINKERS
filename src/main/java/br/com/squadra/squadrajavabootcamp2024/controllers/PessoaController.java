package br.com.squadra.squadrajavabootcamp2024.controllers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.PessoaCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.PessoaUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.models.PessoaModel;
import br.com.squadra.squadrajavabootcamp2024.services.PessoaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
@AllArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<List<PessoaModel>> cadastrarPessoa(@Valid @RequestBody PessoaCreateDTO request) {
        return ResponseEntity.ok(pessoaService.cadastrarPessoa(request));
    }

    @GetMapping
    public ResponseEntity<Object> buscarPessoaPorFiltro(@RequestParam(required = false) Long codigoPessoa,
                                                        @RequestParam(required = false) String login,
                                                        @RequestParam(required = false) Integer status) {
        return ResponseEntity.ok(pessoaService.buscarPessoaPorFiltro(codigoPessoa, login, status));
    }

    @PutMapping
    public ResponseEntity<List<PessoaModel>> atualizarPessoa(@Valid @RequestBody PessoaUpdateDTO pessoaAtualizada) {
        return ResponseEntity.ok(pessoaService.atualizarPessoa(pessoaAtualizada));
    }

    @DeleteMapping("/{codigoPessoa}")
    public ResponseEntity<List<PessoaModel>> deletarPessoa(@PathVariable Long codigoPessoa) {
        return ResponseEntity.ok(pessoaService.deletarPessoa(codigoPessoa));
    }


}
