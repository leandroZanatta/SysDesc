package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QPerfilUsuario.perfilUsuario;
import static br.com.sysdesc.repository.model.QPermissaoPrograma.permissaoPrograma;

import java.util.List;

import br.com.sysdesc.repository.model.PermissaoPrograma;

public class PermissaoProgramaDAO extends AbstractGenericDAO<PermissaoPrograma> {

	public PermissaoProgramaDAO() {
		super(permissaoPrograma, permissaoPrograma.idPermissaoprograma);
	}

	public List<PermissaoPrograma> buscarPermissoesPorUsuario(Long codigoUsuario) {

		return sqlFrom().where(permissaoPrograma.flagLeitura.eq(true)
				.and(permissaoPrograma.codigoUsuario.eq(codigoUsuario)
						.or(permissaoPrograma.codigoPerfil.isNotNull()
								.and(permissaoPrograma.codigoPerfil.in(subQuery().from(perfilUsuario)
										.where(perfilUsuario.codigoUsuario.eq(codigoUsuario))
										.list(perfilUsuario.codigoUsuario))))))
				.list(permissaoPrograma);
	}

}
