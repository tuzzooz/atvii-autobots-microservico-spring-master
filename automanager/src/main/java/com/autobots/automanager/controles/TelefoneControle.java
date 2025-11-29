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

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.AdicionadorLinkTelefone;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
public class TelefoneControle {
    @Autowired
    private TelefoneRepositorio repositorio;
    @Autowired
    private AdicionadorLinkTelefone adicionadorLink;

    @GetMapping("/telefones/{id}")
    public ResponseEntity<Telefone> obterTelefone(@PathVariable Long id) {
        Optional<Telefone> opt = repositorio.findById(id);
        if (!opt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Telefone t = opt.get();
        adicionadorLink.adicionarLink(t);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @GetMapping("/telefones")
    public ResponseEntity<List<Telefone>> obterTelefones() {
        List<Telefone> lista = repositorio.findAll();
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        adicionadorLink.adicionarLink(lista);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/telefones")
    public ResponseEntity<?> cadastrarTelefone(@RequestBody Telefone telefone) {
        if (telefone.getId() != null) return new ResponseEntity<>(HttpStatus.CONFLICT);
        Telefone salvo = repositorio.save(telefone);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/telefones/{id}")
    public ResponseEntity<?> atualizarTelefone(@PathVariable Long id, @RequestBody Telefone atualizacao) {
        Optional<Telefone> opt = repositorio.findById(id);
        if (!opt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Telefone t = opt.get();
        t.setDdd(atualizacao.getDdd());
        t.setNumero(atualizacao.getNumero());
        repositorio.save(t);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/telefones/{id}")
    public ResponseEntity<?> excluirTelefone(@PathVariable Long id) {
        Optional<Telefone> opt = repositorio.findById(id);
        if (!opt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        repositorio.delete(opt.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
