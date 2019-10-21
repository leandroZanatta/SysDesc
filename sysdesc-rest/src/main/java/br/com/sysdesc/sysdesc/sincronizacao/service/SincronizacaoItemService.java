package br.com.sysdesc.sysdesc.sincronizacao.service;

import br.com.sysdesc.sysdesc.sincronizacao.tabelas.vo.ComparaVersaoTabelaVO;
import br.com.sysdesc.sysdesc.sincronizacao.util.SincronizacaoTabelaEnum;

public interface SincronizacaoItemService {

	public abstract SincronizacaoTabelaEnum getTabela();

	public abstract boolean isTabelaAtualizada();

	public abstract void setSincronizacaoVersao(ComparaVersaoTabelaVO comparaVersaoTabelaVO);

}
