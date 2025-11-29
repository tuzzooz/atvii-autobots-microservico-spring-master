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
import javax.validation.Valid;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelos.AdicionadorLinkCliente;
import com.autobots.automanager.modelos.ClienteAtualizador;
import com.autobots.automanager.modelos.ClienteSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
public class ClienteControle {
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private ClienteSelecionador selecionador;
	@Autowired
	private AdicionadorLinkCliente adicionadorLink;

	@GetMapping("/clientes/{id}")
	public ResponseEntity<Cliente> obterCliente(@PathVariable Long id) {
		Optional<Cliente> clienteOpt = repositorio.findById(id);
		if (!clienteOpt.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Cliente cliente = clienteOpt.get();
		adicionadorLink.adicionarLink(cliente);
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> obterClientes() {
		List<Cliente> clientes = repositorio.findAll();
		if (clientes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		adicionadorLink.adicionarLink(clientes);
		return new ResponseEntity<>(clientes, HttpStatus.OK);
	}

	@PostMapping("/clientes")
	public ResponseEntity<?> cadastrarCliente(@Valid @RequestBody Cliente cliente) {
		if (cliente.getId() != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		Cliente salvo = repositorio.save(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(salvo.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> atualizarCliente(@PathVariable Long id, @Valid @RequestBody Cliente atualizacao) {
		Optional<Cliente> clienteOpt = repositorio.findById(id);
		if (!clienteOpt.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Cliente cliente = clienteOpt.get();
		ClienteAtualizador atualizador = new ClienteAtualizador();
		atualizador.atualizar(cliente, atualizacao);
		repositorio.save(cliente);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> excluirCliente(@PathVariable Long id) {
		Optional<Cliente> clienteOpt = repositorio.findById(id);
		if (!clienteOpt.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repositorio.delete(clienteOpt.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
