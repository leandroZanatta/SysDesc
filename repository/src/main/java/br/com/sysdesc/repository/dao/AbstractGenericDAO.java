package br.com.sysdesc.repository.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.path.NumberPath;

import br.com.sysdesc.repository.conexao.Conexao;
import br.com.sysdesc.util.classes.interfaces.GenericDAO;

public abstract class AbstractGenericDAO<T> implements GenericDAO<T> {

	private final EntityManager entityManager = Conexao.getEntityManager();
	private EntityPath<T> entityPath;
	private NumberPath<Long> campoId;
	private Field fieldId;

	@SuppressWarnings("unchecked")
	public AbstractGenericDAO(EntityPath<T> entityPath, NumberPath<Long> idLogin) {
		this.entityPath = entityPath;
		this.campoId = idLogin;
		Class<T> persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		try {
			this.fieldId = persistentClass.getDeclaredField(idLogin.getMetadata().getName());
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public JPAQuery sqlQuery() {
		return new JPAQuery(entityManager);
	}

	public JPASubQuery subQuery() {
		return new JPASubQuery();
	}

	public JPAQuery sqlFrom() {
		return new JPAQuery(entityManager).from(entityPath);
	}

	public List<T> listar() {
		return sqlQuery().from(entityPath).list(entityPath);
	}

	public List<T> pesquisar(BooleanBuilder clausulas, NumberPath<Long> aliasOrdenacao, Integer numeroRegistros,
			Integer tamanhoPagina) {
		JPAQuery query = sqlQuery().from(entityPath);

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
		return sqlFrom().where(campoId.eq(id)).singleResult(entityPath);
	}

	public T obterPorId(Long id, BooleanBuilder filter) {

		filter.and(campoId.eq(id));

		return sqlFrom().where(filter).singleResult(entityPath);
	}

	public T next(T classeConsulta) {
		try {
			fieldId.setAccessible(Boolean.TRUE);

			if (classeConsulta == null || fieldId.get(classeConsulta) == null) {
				return last();
			}

			T objeto = sqlFrom().where(campoId.gt(Long.valueOf(fieldId.get(classeConsulta).toString())))
					.orderBy(campoId.asc()).limit(1L).singleResult(entityPath);

			if (objeto == null) {
				return first();
			}

			return objeto;
		} catch (IllegalArgumentException | IllegalAccessException e) {

			return null;
		}

	}

	public T previows(T classeConsulta) {

		try {

			fieldId.setAccessible(Boolean.TRUE);

			if (classeConsulta == null || fieldId.get(classeConsulta) == null) {
				return last();
			}

			T objeto = sqlFrom().where(campoId.lt(Long.valueOf(fieldId.get(classeConsulta).toString())))
					.orderBy(campoId.desc()).limit(1L).singleResult(entityPath);

			if (objeto == null) {
				return last();
			}

			return objeto;
		} catch (IllegalArgumentException | IllegalAccessException e) {

			return null;
		}

	}

	public T last() {
		return sqlFrom().orderBy(campoId.desc()).singleResult(entityPath);
	}

	public T first() {
		return sqlFrom().orderBy(campoId.asc()).singleResult(entityPath);
	}

	public Field getFieldId() {
		return fieldId;
	}

	public void setFieldId(Field fieldId) {
		this.fieldId = fieldId;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}