package br.com.sysdesc.util.vo;

import java.util.ArrayList;
import java.util.List;

import br.com.sysdesc.util.vo.ServerVO;
import lombok.Data;

@Data
public class ConfigurationVO {

	private List<ServerVO> frontEnds = new ArrayList<>();

	private List<ServerVO> servers = new ArrayList<>();

}
