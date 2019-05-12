package br.com.sysdesc.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPrograma is a Querydsl query type for Programa
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPrograma extends EntityPathBase<Programa> {

    private static final long serialVersionUID = 1397597035L;

    public static final QPrograma programa = new QPrograma("programa");

    public final NumberPath<Long> idPrograma = createNumber("idPrograma", Long.class);

    public final ListPath<PermissaoPrograma, QPermissaoPrograma> tbPermissaoprogramas = this.<PermissaoPrograma, QPermissaoPrograma>createList("tbPermissaoprogramas", PermissaoPrograma.class, QPermissaoPrograma.class, PathInits.DIRECT2);

    public final StringPath txDescricao = createString("txDescricao");

    public QPrograma(String variable) {
        super(Programa.class, forVariable(variable));
    }

    public QPrograma(Path<? extends Programa> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPrograma(PathMetadata<?> metadata) {
        super(Programa.class, metadata);
    }

}

