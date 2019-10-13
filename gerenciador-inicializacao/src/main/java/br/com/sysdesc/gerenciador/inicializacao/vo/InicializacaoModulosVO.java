package br.com.sysdesc.gerenciador.inicializacao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InicializacaoModulosVO {

	private Long codigoModulo;

	private String serverName;

	private Boolean isFrontEnd;

	private Boolean isStarted;
}
