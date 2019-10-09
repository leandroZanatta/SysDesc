package br.com.sysdesc.gerenciador.inicializacao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModuleVO {

    private Process process;

    private Thread fechamentoExterno;
}
