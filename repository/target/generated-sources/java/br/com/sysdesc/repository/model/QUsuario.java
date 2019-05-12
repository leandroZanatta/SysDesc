package br.com.sysdesc.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QUsuario is a Querydsl query type for Usuario
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUsuario extends EntityPathBase<Usuario> {

    private static final long serialVersionUID = -2133677984L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsuario usuario1 = new QUsuario("usuario1");

    public final QCliente cliente;

    public final NumberPath<Long> idUsuario = createNumber("idUsuario", Long.class);

    public final ListPath<PerfilUsuario, QPerfilUsuario> perfilUsuarios = this.<PerfilUsuario, QPerfilUsuario>createList("perfilUsuarios", PerfilUsuario.class, QPerfilUsuario.class, PathInits.DIRECT2);

    public final ListPath<PermissaoPrograma, QPermissaoPrograma> permissaoProgramas = this.<PermissaoPrograma, QPermissaoPrograma>createList("permissaoProgramas", PermissaoPrograma.class, QPermissaoPrograma.class, PathInits.DIRECT2);

    public final StringPath senha = createString("senha");

    public final StringPath usuario = createString("usuario");

    public QUsuario(String variable) {
        this(Usuario.class, forVariable(variable), INITS);
    }

    public QUsuario(Path<? extends Usuario> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUsuario(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUsuario(PathMetadata<?> metadata, PathInits inits) {
        this(Usuario.class, metadata, inits);
    }

    public QUsuario(Class<? extends Usuario> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cliente = inits.isInitialized("cliente") ? new QCliente(forProperty("cliente"), inits.get("cliente")) : null;
    }

}

