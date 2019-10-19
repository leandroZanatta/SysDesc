package br.com.sysdesc.util.vo;

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
