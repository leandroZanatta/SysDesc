package br.com.sysdesc.sysdesc.sincronizacao.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.com.sysdesc.sysdesc.dto.SincronizacaoItemDTO;
import br.com.sysdesc.sysdesc.dto.SincronizacaoVersaoDTO;
import br.com.sysdesc.sysdesc.sincronizacao.service.SincronizacaoItemService;
import br.com.sysdesc.sysdesc.sincronizacao.tabelas.vo.ComparaVersaoTabelaVO;

@Service
public abstract class SincronizaItemServiceImpl<T extends SincronizacaoVersaoDTO> implements SincronizacaoItemService {

	private ComparaVersaoTabelaVO comparaVersaoTabelaVO;

	@Autowired
	@Lazy
	private ApplicationContext applicationContext;

	@Override
	public void setSincronizacaoVersao(ComparaVersaoTabelaVO comparaVersaoTabelaVO) {
		this.comparaVersaoTabelaVO = comparaVersaoTabelaVO;
	}

	@Override
	public boolean isTabelaAtualizada() {

		for (SincronizacaoItemDTO versao : comparaVersaoTabelaVO.getVersaoRemota()) {

			Optional<SincronizacaoItemDTO> optional = comparaVersaoTabelaVO.getVersaoLocal().stream().findFirst();

			if (!optional.isPresent()
					|| optional.get().getSincronizacaoVersao().equals(versao.getSincronizacaoVersao())) {
				return Boolean.FALSE;
			}
		}

		return Boolean.TRUE;

	}

}
