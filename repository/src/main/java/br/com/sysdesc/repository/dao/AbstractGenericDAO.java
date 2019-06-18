package br.com.sysdesc.repository.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.sql.JPASQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.NumberPath;

import br.com.sysdesc.repository.conexao.Conexao;
import br.com.sysdesc.repository.interfaces.GenericDAO;
import br.com.sysdesc.repository.model.Pesquisa;
import br.com.sysdesc.repository.util.EntityPathUtil;
import br.com.sysdesc.util.classes.LongUtil;
import br.com.sysdesc.util.classes.StringUtil;

public abstract class AbstractGenericDAO<T> implements GenericDAO<T> {

	private final EntityManager entityManager = Conexao.getEntityManager();
	private final SQLTemplates sqlTemplates = Conexao.getSqlTemplates();
	private EntityPath<T> entityPath;
	private NumberPath<Long> campoId;

	public AbstractGenericDAO(EntityPath<T> entityPath, NumberPath<Long> idLogin) {
		this.entityPath = entityPath;
		this.campoId = idLogin;
	}

	public JPASQLQuery sqlQuery() {

		return new JPASQLQuery(entityManager, sqlTemplates);
	}

	public JPASQLQuery sqlFrom() {
		return new JPASQLQuery(entityManager, sqlTemplates).from(entityPath);
	}

	public JPASubQuery subQuery() {
		return new JPASubQuery();
	}

	public JPAQuery query() {
		return new JPAQuery(entityManager);
	}

	public JPAQuery from() {
		return new JPAQuery(entityManager).from(entityPath);
	}

	public List<T> listar() {
		return query().from(entityPath).list(entityPath);
	}

	public List<T> pesquisar(BooleanBuilder clausulas, NumberPath<Long> aliasOrdenacao, Integer numeroRegistros,
			Integer tamanhoPagina) {
		JPAQuery query = from();

		if (clausulas.hasValue()) {
			query.where(clausulas);
		}

		return query.offset(numeroRegistros).limit(tamanhoPagina).orderBy(aliasOrdenacao.asc()).list(entityPath);
	}

	public EntityPath<T> getEntityPath() {
		return entityPath;
	}

	public void setEntityPath(EntityPath<T> entityPath) {
		this.entityPath = entityPath;
	}

	public void salvar(T perist) {
		entityManager.getTransaction().begin();
		entityManager.persist(perist);
		entityManager.flush();
		entityManager.getTransaction().commit();

	}

	public List<T> salvar(List<T> perist) {
		entityManager.getTransaction().begin();
		for (T t : perist) {
			entityManager.persist(t);
		}
		entityManager.flush();
		entityManager.getTransaction().commit();
		return perist;
	}

	public void remove(T remove) {
		entityManager.getTransaction().begin();
		entityManager.remove(remove);
		entityManager.flush();
		entityManager.getTransaction().commit();
	}

	public T obterPorId(Long id) {
		return from().where(campoId.eq(id)).singleResult(entityPath);
	}

	public T obterPorId(Long id, BooleanBuilder filter) {

		filter.and(campoId.eq(id));

		return from().where(filter).singleResult(entityPath);
	}

	public T next(Long id) {

		if (LongUtil.isNullOrZero(id)) {
			return last();
		}

		T objeto = from().where(campoId.gt(id)).orderBy(campoId.asc()).limit(1L).singleResult(entityPath);

		if (objeto == null) {
			return first();
		}

		return objeto;

	}

	public T previows(Long id) {

		if (LongUtil.isNullOrZero(id)) {
			return last();
		}

		T objeto = from().where(campoId.lt(id)).orderBy(campoId.desc()).limit(1L).singleResult(entityPath);

		if (objeto == null) {
			return last();
		}

		return objeto;

	}

	public T last() {
		return from().orderBy(campoId.desc()).singleResult(entityPath);
	}

	public T first() {
		return from().orderBy(campoId.asc()).singleResult(entityPath);
	}

	@Override
	public List<T> pesquisar(boolean selected, String pesquisa, Pesquisa pesquisaExibir, Integer rows) {

		JPAQuery query = from();

		BooleanBuilder booleanBuilder = getClausule(selected, pesquisa, pesquisaExibir);

		if (booleanBuilder.hasValue()) {
			query.where(booleanBuilder);
		}

		return query.orderBy(campoId.asc()).offset(rows.intValue()).limit(pesquisaExibir.getPaginacao())
				.list(entityPath);
	}

	@Override
	public Long count(boolean selected, String pesquisa, Pesquisa pesquisaExibir) {

		JPAQuery query = from();

		BooleanBuilder booleanBuilder = getClausule(selected, pesquisa, pesquisaExibir);

		if (booleanBuilder.hasValue()) {
			query.where(booleanBuilder);
		}

		return query.orderBy(campoId.asc()).count();
	}

	private BooleanBuilder getClausule(boolean selected, String pesquisa, Pesquisa pesquisaExibir) {

		BooleanBuilder booleanBuilder = new BooleanBuilder();

		if (!StringUtil.isNullOrEmpty(pesquisa)) {

			pesquisaExibir.getPesquisaCampos().forEach(campo -> {

				BooleanExpression clausula = EntityPathUtil.getExpressionLike(this.entityPath, selected, pesquisa,
						campo);

				if (clausula != null) {

					booleanBuilder.or(clausula);
				}
			});

		}

		return booleanBuilder;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}