package br.com.sysdesc.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPerfilUsuario is a Querydsl query type for PerfilUsuario
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPerfilUsuario extends EntityPathBase<PerfilUsuario> {

    private static final long serialVersionUID = 1740327796L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPerfilUsuario perfilUsuario = new QPerfilUsuario("perfilUsuario");

    public final NumberPath<Long> codigoPerfil = createNumber("codigoPerfil", Long.class);

    public final NumberPath<Long> codigoUsuario = createNumber("codigoUsuario", Long.class);

    public final br.com.sysdesc.repository.model.pk.QPerfilUsuarioPk id;

    public final QPerfil perfil;

    public final QUsuario usuario;

    public QPerfilUsuario(String variable) {
        this(PerfilUsuario.class, forVariable(variable), INITS);
    }

    public QPerfilUsuario(Path<? extends PerfilUsuario> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPerfilUsuario(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPerfilUsuario(PathMetadata<?> metadata, PathInits inits) {
        this(PerfilUsuario.class, metadata, inits);
    }

    public QPerfilUsuario(Class<? extends PerfilUsuario> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new br.com.sysdesc.repository.model.pk.QPerfilUsuarioPk(forProperty("id")) : null;
        this.perfil = inits.isInitialized("perfil") ? new QPerfil(forProperty("perfil")) : null;
        this.usuario = inits.isInitialized("usuario") ? new QUsuario(forProperty("usuario"), inits.get("usuario")) : null;
    }

}

