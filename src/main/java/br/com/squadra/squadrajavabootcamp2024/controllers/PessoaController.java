package br.com.squadra.squadrajavabootcamp2024.controllers;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.PessoaCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.PessoaUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.models.PessoaModel;
import br.com.squadra.squadrajavabootcamp2024.services.PessoaService;
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
@RequestMapping("/pessoa")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PessoaModel> cadastrarPessoa(@Valid @RequestBody PessoaCreateDTO request) {
        return pessoaService.cadastrarPessoa(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Object buscarPessoaPorFiltro(@RequestParam(required = false) Long codigoPessoa,
                                        @RequestParam(required = false) String login,
                                        @RequestParam(required = false) Integer status) {
        return pessoaService.buscarPessoaPorFiltro(codigoPessoa, login, status);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PessoaModel> atualizarPessoa(@Valid @RequestBody PessoaUpdateDTO pessoaAtualizada) {
        return pessoaService.atualizarPessoa(pessoaAtualizada);
    }

    @DeleteMapping("/{codigoPessoa}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable Long codigoPessoa) {
        return ResponseEntity.noContent().build();
    }


}
