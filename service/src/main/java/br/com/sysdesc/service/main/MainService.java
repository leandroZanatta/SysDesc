package br.com.sysdesc.service.main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.sysdesc.repository.dao.PermissaoProgramaDAO;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Programa;
import br.com.sysdesc.util.classes.ListUtil;
import br.com.sysdesc.util.classes.LongUtil;

public class MainService {

	private PermissaoProgramaDAO permissaoProgramaDAO = new PermissaoProgramaDAO();

	public List<Programa> buscarPermissaoUsuario(Long codigoUsuario) {

		List<PermissaoPrograma> permissaoUsuarioPerfil = permissaoProgramaDAO.buscarPermissoesPorUsuario(codigoUsuario);

		List<PermissaoPrograma> rootMenus = permissaoUsuarioPerfil.stream()
				.filter(x -> x.getPrograma().getCodigoModulo() == null).collect(Collectors.toList());

		Map<Long, List<PermissaoPrograma>> rootMenusMap = rootMenus.stream()
				.collect(Collectors.groupingBy(PermissaoPrograma::getCodigoPrograma));

		List<Programa> permissoes = new ArrayList<>();

		for (Entry<Long, List<PermissaoPrograma>> entry : rootMenusMap.entrySet()) {

			permissoes.add(createMenu(permissaoUsuarioPerfil, entry.getValue()));

		}

		return permissoes;
	}

	private List<Programa> createSubMenus(Long idPrograma, List<PermissaoPrograma> permissaoUsuarioPerfil) {

		List<Programa> permissoes = new ArrayList<>();

		List<PermissaoPrograma> permissaoModulo = permissaoUsuarioPerfil.stream()
				.filter(x -> idPrograma.equals(x.getPrograma().getCodigoModulo())).collect(Collectors.toList());

		if (!ListUtil.isNullOrEmpty(permissaoModulo)) {

			Map<Long, List<PermissaoPrograma>> rootMenusMap = permissaoModulo.stream()
					.collect(Collectors.groupingBy(PermissaoPrograma::getCodigoPrograma));

			for (Entry<Long, List<PermissaoPrograma>> entry : rootMenusMap.entrySet()) {

				permissoes.add(createMenu(permissaoUsuarioPerfil, entry.getValue()));
			}

		}

		return permissoes;
	}

	private Programa createMenu(List<PermissaoPrograma> permissaoUsuarioPerfil, List<PermissaoPrograma> entry) {

		PermissaoPrograma permissaoPrograma = buscarPermissaoPorUsuario(entry);

		Programa menu = permissaoPrograma.getPrograma();

		menu.setPermissaoProgramas(ListUtil.toList(permissaoPrograma));

		menu.setProgramas(this.createSubMenus(menu.getIdPrograma(), permissaoUsuarioPerfil));

		return menu;
	}

	private PermissaoPrograma buscarPermissaoPorUsuario(List<PermissaoPrograma> entry) {

		Optional<PermissaoPrograma> optionalPermissaoUsuario = entry.stream()
				.filter(programa -> !LongUtil.isNullOrZero(programa.getCodigoUsuario())).findFirst();

		if (optionalPermissaoUsuario.isPresent()) {

			return optionalPermissaoUsuario.get();

		}

		return entry.stream()
				.sorted(Comparator.comparing(PermissaoPrograma::getFlagLeitura)
						.thenComparing(PermissaoPrograma::getFlagCadastro)
						.thenComparing(PermissaoPrograma::getFlagExclusao))
				.findFirst().get();

	}

}
