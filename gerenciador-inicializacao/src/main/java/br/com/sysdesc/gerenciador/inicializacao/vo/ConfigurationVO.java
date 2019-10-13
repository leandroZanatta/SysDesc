package br.com.sysdesc.gerenciador.inicializacao.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ConfigurationVO {

    private List<ServerVO> frontEnds = new ArrayList<>();

    private List<ServerVO> servers = new ArrayList<>();

}
