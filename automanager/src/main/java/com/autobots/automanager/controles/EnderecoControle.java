package com.autobots.automanager.controles;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.AdicionadorLinkEndereco;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
public class EnderecoControle {
    @Autowired
    private EnderecoRepositorio repositorio;
    @Autowired
    private AdicionadorLinkEndereco adicionadorLink;

    @GetMapping("/enderecos/{id}")
    public ResponseEntity<Endereco> obterEndereco(@PathVariable Long id) {
        Optional<Endereco> opt = repositorio.findById(id);
        if (!opt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Endereco e = opt.get();
        adicionadorLink.adicionarLink(e);
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @GetMapping("/enderecos")
    public ResponseEntity<List<Endereco>> obterEnderecos() {
        List<Endereco> lista = repositorio.findAll();
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        adicionadorLink.adicionarLink(lista);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/enderecos")
    public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco) {
        if (endereco.getId() != null) return new ResponseEntity<>(HttpStatus.CONFLICT);
        Endereco salvo = repositorio.save(endereco);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/enderecos/{id}")
    public ResponseEntity<?> atualizarEndereco(@PathVariable Long id, @RequestBody Endereco atualizacao) {
        Optional<Endereco> opt = repositorio.findById(id);
        if (!opt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Endereco e = opt.get();
        e.setCidade(atualizacao.getCidade());
        e.setEstado(atualizacao.getEstado());
        e.setRua(atualizacao.getRua());
        e.setNumero(atualizacao.getNumero());
        e.setBairro(atualizacao.getBairro());
        repositorio.save(e);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/enderecos/{id}")
    public ResponseEntity<?> excluirEndereco(@PathVariable Long id) {
        Optional<Endereco> opt = repositorio.findById(id);
        if (!opt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        repositorio.delete(opt.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
