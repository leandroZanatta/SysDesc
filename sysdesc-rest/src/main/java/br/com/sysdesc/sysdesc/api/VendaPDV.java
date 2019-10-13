package br.com.sysdesc.sysdesc.api;

import static br.com.sysdesc.util.constants.EndPointsConstants.API_TAGVENDAPDV;
import static br.com.sysdesc.util.constants.EndPointsConstants.PATH_ADQUIRIR_VENDA_ABERTA;
import static br.com.sysdesc.util.constants.EndPointsConstants.PATH_VENDAPDV;
import static br.com.sysdesc.util.constants.EndPointsConstants.PROTOCOLO_HTTP;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.sysdesc.sysdesc.anotations.PDVResponses;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@Api(tags = { API_TAGVENDAPDV }, protocols = PROTOCOLO_HTTP)
@RequestMapping(value = PATH_VENDAPDV, consumes = { MediaType.APPLICATION_JSON_VALUE, }, produces = {
		MediaType.APPLICATION_JSON_VALUE })
public interface VendaPDV {

	@ApiOperation(value = PATH_ADQUIRIR_VENDA_ABERTA, response = Boolean.class)
	@PDVResponses
	@GetMapping(PATH_ADQUIRIR_VENDA_ABERTA)
	public abstract ResponseEntity<Boolean> adquirirVendaAberta();

}
