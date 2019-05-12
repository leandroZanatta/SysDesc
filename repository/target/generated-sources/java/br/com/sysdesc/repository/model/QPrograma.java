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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPrograma programa1 = new QPrograma("programa1");

    public final StringPath descricao = createString("descricao");

    public final NumberPath<Long> idPrograma = createNumber("idPrograma", Long.class);

    public final ListPath<PermissaoPrograma, QPermissaoPrograma> permissaoProgramas = this.<PermissaoPrograma, QPermissaoPrograma>createList("permissaoProgramas", PermissaoPrograma.class, QPermissaoPrograma.class, PathInits.DIRECT2);

    public final QPrograma programa;

    public QPrograma(String variable) {
        this(Programa.class, forVariable(variable), INITS);
    }

    public QPrograma(Path<? extends Programa> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPrograma(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPrograma(PathMetadata<?> metadata, PathInits inits) {
        this(Programa.class, metadata, inits);
    }

    public QPrograma(Class<? extends Programa> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.programa = inits.isInitialized("programa") ? new QPrograma(forProperty("programa"), inits.get("programa")) : null;
    }

}

