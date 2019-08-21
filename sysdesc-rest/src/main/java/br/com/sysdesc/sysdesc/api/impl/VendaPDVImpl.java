package br.com.sysdesc.sysdesc.api.impl;

import org.springframework.http.ResponseEntity;

import br.com.sysdesc.sysdesc.api.VendaPDV;

public class VendaPDVImpl implements VendaPDV {

    @Override
    public ResponseEntity<Boolean> adquirirVendaAberta() {

        return ResponseEntity.ok(true);
    }

}
