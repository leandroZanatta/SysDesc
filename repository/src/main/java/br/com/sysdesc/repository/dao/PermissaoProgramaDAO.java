package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QPerfilUsuario.perfilUsuario;
import static br.com.sysdesc.repository.model.QPermissaoPrograma.permissaoPrograma;

import java.util.List;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.support.Expressions;

import br.com.sysdesc.repository.model.PermissaoPrograma;

public class PermissaoProgramaDAO extends AbstractGenericDAO<PermissaoPrograma> {

	public PermissaoProgramaDAO() {
		super(permissaoPrograma, permissaoPrograma.idPermissaoprograma);
	}

	public List<PermissaoPrograma> buscarPermissoesPorUsuario(Long codigoUsuario) {

		BooleanBuilder booleanBuilderPerfil = new BooleanBuilder();

		booleanBuilderPerfil.and(permissaoPrograma.flagLeitura.eq(true).and(permissaoPrograma.codigoPerfil.isNotNull())
				.and(permissaoPrograma.codigoPerfil.in(subQuery().from(perfilUsuario)
						.where(perfilUsuario.codigoUsuario.eq(codigoUsuario)).list(perfilUsuario.codigoPerfil))));

		return sqlFrom()
				.where(permissaoPrograma.flagLeitura.eq(true).and(permissaoPrograma.codigoUsuario.eq(codigoUsuario))
						.or(Expressions.booleanTemplate("({0})", booleanBuilderPerfil)))
				.list(permissaoPrograma);
	}

	public List<PermissaoPrograma> buscarPermissoesPerfil(Long idPerfil) {

		return sqlFrom().where(permissaoPrograma.codigoPerfil.eq(idPerfil)).list(permissaoPrograma);
	}

	public List<PermissaoPrograma> buscarPermissoesUsuario(Long idUsuario) {

		return sqlFrom().where(permissaoPrograma.codigoUsuario.eq(idUsuario)).list(permissaoPrograma);
	}

}
