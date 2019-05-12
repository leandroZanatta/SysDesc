package br.com.sysdesc.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QEstado is a Querydsl query type for Estado
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QEstado extends EntityPathBase<Estado> {

    private static final long serialVersionUID = 165811700L;

    public static final QEstado estado = new QEstado("estado");

    public final ListPath<Cidade, QCidade> cidades = this.<Cidade, QCidade>createList("cidades", Cidade.class, QCidade.class, PathInits.DIRECT2);

    public final StringPath descricao = createString("descricao");

    public final NumberPath<Long> idEstado = createNumber("idEstado", Long.class);

    public final StringPath uf = createString("uf");

    public QEstado(String variable) {
        super(Estado.class, forVariable(variable));
    }

    public QEstado(Path<? extends Estado> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEstado(PathMetadata<?> metadata) {
        super(Estado.class, metadata);
    }

}

