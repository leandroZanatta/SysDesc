package br.com.sysdesc.util.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModuleVO {

    private Process process;

    private Thread fechamentoExterno;
}
