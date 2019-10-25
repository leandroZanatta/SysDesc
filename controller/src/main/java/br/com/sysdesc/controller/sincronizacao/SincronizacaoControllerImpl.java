package br.com.sysdesc.controller.sincronizacao;

import java.util.ArrayList;
import java.util.List;

import br.com.sysdesc.dto.sincronizacao.SincronizacaoItemDTO;
import br.com.sysdesc.http.server.anotation.RequestMethod;
import br.com.sysdesc.http.server.anotation.RestController;
import br.com.sysdesc.http.server.enumeradores.HttpMethod;

@RestController(path = "/sincronizacaoTabelas")
public class SincronizacaoControllerImpl {

    @RequestMethod(method = HttpMethod.GET, path = "/buscarVersoesSincronizacao")
    public List<SincronizacaoItemDTO> buscarVersoesSincronizacao() {

        return new ArrayList<SincronizacaoItemDTO>();
    }
}
