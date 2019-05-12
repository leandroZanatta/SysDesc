package br.com.sysdesc.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QCliente is a Querydsl query type for Cliente
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QCliente extends EntityPathBase<Cliente> {

    private static final long serialVersionUID = -1140245716L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCliente cliente = new QCliente("cliente");

    public final QCidade cidade;

    public final NumberPath<Long> idCliente = createNumber("idCliente", Long.class);

    public final StringPath nome = createString("nome");

    public final ListPath<Usuario, QUsuario> usuarios = this.<Usuario, QUsuario>createList("usuarios", Usuario.class, QUsuario.class, PathInits.DIRECT2);

    public QCliente(String variable) {
        this(Cliente.class, forVariable(variable), INITS);
    }

    public QCliente(Path<? extends Cliente> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QCliente(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QCliente(PathMetadata<?> metadata, PathInits inits) {
        this(Cliente.class, metadata, inits);
    }

    public QCliente(Class<? extends Cliente> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cidade = inits.isInitialized("cidade") ? new QCidade(forProperty("cidade"), inits.get("cidade")) : null;
    }

}

