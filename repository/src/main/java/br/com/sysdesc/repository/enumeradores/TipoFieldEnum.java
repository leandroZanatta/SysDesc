package br.com.sysdesc.repository.enumeradores;

import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_TIPO_DADO_INVALIDO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.DatePath;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import br.com.sysdesc.util.exception.SysDescException;
import br.com.sysdesc.util.vo.FieldPesquisaVO;

public enum TipoFieldEnum {

	LONG(1L, NumberPath.class, Long.class),

	BIGDECIMAL(1L, NumberPath.class, BigDecimal.class),

	DATE(1L, DatePath.class, Date.class),

	DATETIME(1L, DateTimePath.class, Date.class),

	BOOLEAN(1L, BooleanPath.class, Boolean.class),

	STRING(1L, StringPath.class, String.class);

	private static Map<Long, TipoFieldEnum> mapa = new HashMap<>();

	private final Class<? extends Path> path;

	private final Class<?> type;

	private final Long codigo;

	static {
		for (TipoFieldEnum tipoFieldEnum : TipoFieldEnum.values()) {
			mapa.put(tipoFieldEnum.getCodigo(), tipoFieldEnum);
		}
	}

	@SuppressWarnings("rawtypes")
	private TipoFieldEnum(Long codigo, final Class<? extends Path> path, Class<?> type) {
		this.codigo = codigo;
		this.path = path;
		this.type = type;
	}

	@SuppressWarnings("rawtypes")
	public Class<? extends Path> getPath() {
		return path;
	}

	public Class<?> getType() {
		return type;
	}

	public Long getCodigo() {
		return codigo;
	}

	public static TipoFieldEnum getFromPath(FieldPesquisaVO fieldPesquisaVO) {

		Class<?> path = fieldPesquisaVO.getField().getType();

		if (path.equals(NumberPath.class)) {

			ParameterizedType type = (ParameterizedType) fieldPesquisaVO.getField().getAnnotatedType().getType();

			Type classType = type.getActualTypeArguments()[0];

			if (classType.equals(BigDecimal.class)) {
				return BIGDECIMAL;
			}

			if (classType.equals(Long.class)) {
				return LONG;
			}
		}

		if (path.equals(StringPath.class)) {

			return STRING;
		}

		if (path.equals(BooleanPath.class)) {

			return BOOLEAN;
		}

		if (path.equals(DatePath.class)) {

			return DATE;
		}

		if (path.equals(DateTimePath.class)) {

			return DATETIME;
		}

		throw new SysDescException(MENSAGEM_TIPO_DADO_INVALIDO);
	}

	public static TipoFieldEnum forCodigo(Long codigo) {

		return mapa.get(codigo);
	}

}
