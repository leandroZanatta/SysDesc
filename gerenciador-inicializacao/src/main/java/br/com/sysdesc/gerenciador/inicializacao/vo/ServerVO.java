package br.com.sysdesc.gerenciador.inicializacao.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ServerVO {

    private Long id;

    private String serverName;

    private Long maximumExecutionTime;

    private String comand;

    private String msgValidacaoStart;

    private Boolean async;
    
    private String directory;

    private List<Long> dependsOn = new ArrayList<>();

}
