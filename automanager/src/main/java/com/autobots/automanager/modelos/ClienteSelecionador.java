package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;

@Component
public class ClienteSelecionador {
	public Cliente selecionar(List<Cliente> clientes, Long id) {
		if (id == null) return null;
		Cliente selecionado = null;
		for (Cliente cliente : clientes) {
			if (cliente.getId() != null && cliente.getId().equals(id)) {
				selecionado = cliente;
				break;
			}
		}
		return selecionado;
	}
}