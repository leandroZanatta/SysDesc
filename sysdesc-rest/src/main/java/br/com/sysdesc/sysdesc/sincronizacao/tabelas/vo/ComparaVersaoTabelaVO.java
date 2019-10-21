package br.com.sysdesc.sysdesc.sincronizacao.tabelas.vo;

import java.util.List;

import br.com.sysdesc.sysdesc.dto.SincronizacaoItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparaVersaoTabelaVO {

	private List<SincronizacaoItemDTO> versaoLocal;
	private List<SincronizacaoItemDTO> versaoRemota;

}
