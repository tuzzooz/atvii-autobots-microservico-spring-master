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

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.AdicionadorLinkDocumento;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
public class DocumentoControle {
    @Autowired
    private DocumentoRepositorio repositorio;
    @Autowired
    private AdicionadorLinkDocumento adicionadorLink;

    @GetMapping("/documentos/{id}")
    public ResponseEntity<Documento> obterDocumento(@PathVariable Long id) {
        Optional<Documento> opt = repositorio.findById(id);
        if (!opt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Documento doc = opt.get();
        adicionadorLink.adicionarLink(doc);
        return new ResponseEntity<>(doc, HttpStatus.OK);
    }

    @GetMapping("/documentos")
    public ResponseEntity<List<Documento>> obterDocumentos() {
        List<Documento> lista = repositorio.findAll();
        if (lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        adicionadorLink.adicionarLink(lista);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/documentos")
    public ResponseEntity<?> cadastrarDocumento(@RequestBody Documento documento) {
        if (documento.getId() != null) return new ResponseEntity<>(HttpStatus.CONFLICT);
        Documento salvo = repositorio.save(documento);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/documentos/{id}")
    public ResponseEntity<?> atualizarDocumento(@PathVariable Long id, @RequestBody Documento atualizacao) {
        Optional<Documento> opt = repositorio.findById(id);
        if (!opt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Documento doc = opt.get();
        doc.setNumero(atualizacao.getNumero());
        doc.setTipo(atualizacao.getTipo());
        repositorio.save(doc);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/documentos/{id}")
    public ResponseEntity<?> excluirDocumento(@PathVariable Long id) {
        Optional<Documento> opt = repositorio.findById(id);
        if (!opt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        repositorio.delete(opt.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
