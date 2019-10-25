package br.com.sysdesc.intercomm;

import java.util.List;

import br.com.sysdesc.util.vo.IPVO;
import feign.RequestLine;

public interface PDVClient {

    @RequestLine("POST /configuracaoModulo/atualizarConfiguracoes")
    public abstract Boolean atualizarGerenciadorPDV(List<IPVO> ipERP);
}
