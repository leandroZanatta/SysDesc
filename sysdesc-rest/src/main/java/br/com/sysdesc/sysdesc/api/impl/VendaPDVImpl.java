package br.com.sysdesc.sysdesc.api.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.sysdesc.sysdesc.api.VendaPDV;

@RestController
public class VendaPDVImpl implements VendaPDV {

	@Override
	public ResponseEntity<Boolean> adquirirVendaAberta() {

		return ResponseEntity.ok(true);
	}

}
