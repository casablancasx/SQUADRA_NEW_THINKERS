package br.com.squadra.squadrajavabootcamp2024.services;

import br.com.squadra.squadrajavabootcamp2024.dtos.update.PessoaUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.dtos.create.PessoaCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.PessoaResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.mappers.EnderecoMapper;
import br.com.squadra.squadrajavabootcamp2024.mappers.PessoaMapper;
import br.com.squadra.squadrajavabootcamp2024.models.EnderecoModel;
import br.com.squadra.squadrajavabootcamp2024.models.PessoaModel;
import br.com.squadra.squadrajavabootcamp2024.repositories.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    private final EnderecoMapper enderecoMapper;


    public List<PessoaModel> cadastrarPessoa(PessoaCreateDTO request) {

        if (pessoaRepository.existsByLogin(request.getLogin())) {
            throw new ResourceAlreadyExistException("Não foi possível incluir pessoa no banco de dados. Já existe uma pessoa de login " + request.getLogin() + " cadastrado.");
        }

        PessoaModel pessoaModel = pessoaMapper.toEntity(request);
        pessoaModel.getEnderecos().forEach(endereco -> endereco.setPessoa(pessoaModel));
        pessoaRepository.save(pessoaModel);

        return pessoaRepository.findAllByOrderByCodigoPessoaDesc();
    }

    public Object buscarPessoaPorFiltro(Long codigoPessoa, String login, Integer status) {

        List<PessoaModel> pessoaModelList = pessoaRepository.findByFiltro(codigoPessoa, login, status);

        if (retornoNaoDeveSerUmaLista(codigoPessoa, login, status)) {
            return pessoaModelList.stream().findFirst().map(pessoaMapper::toResponseDTO).orElse(null);
        }

        return pessoaModelList;
    }

    public List<PessoaModel> atualizarPessoa(PessoaUpdateDTO pessoaAtualizada) {
        PessoaModel pessoaExistente = pessoaRepository.findById(pessoaAtualizada.getCodigoPessoa())
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada."));


        List<EnderecoModel> listaDeEnderecosAtualizada = pessoaAtualizada.getEnderecos().stream().map(enderecoMapper::mapUpdateToEntity).toList();
        System.out.println("antes: " + pessoaExistente.getEnderecos().toString());
        removeEnderecoCasoNaoExistaNaListaDeAtualizados(listaDeEnderecosAtualizada, pessoaExistente);


        pessoaMapper.atualizar(pessoaAtualizada, pessoaExistente);

        for (EnderecoModel endereco: pessoaExistente.getEnderecos()){
            System.out.println("endereco: " + endereco.getNomeRua());
        }


        if (pessoaRepository.existsByLoginAndAndCodigoPessoaNot(pessoaExistente.getLogin(), pessoaExistente.getCodigoPessoa())) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar pessoa no banco de dados. Já existe uma pessoa de login " + pessoaExistente.getLogin() + " cadastrado.");
        }

        pessoaRepository.save(pessoaExistente);
        return pessoaRepository.findAllByOrderByCodigoPessoaDesc();
    }

    private void removeEnderecoCasoNaoExistaNaListaDeAtualizados(List<EnderecoModel> listaDeEnderecosAtualizada, PessoaModel pessoaExistente) {
        pessoaExistente.getEnderecos().removeIf(endereco -> {
            Long codigoEndereco = endereco.getCodigoEndereco();
            return codigoEndereco != null && listaDeEnderecosAtualizada.stream()
                    .noneMatch(e -> codigoEndereco.equals(e.getCodigoEndereco()));
        });

    }

    private boolean retornoNaoDeveSerUmaLista(Long codigoPessoa, String login, Integer status) {
        return codigoPessoa != null && login == null && status == null;
    }

}
