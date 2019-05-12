package br.com.sysdesc.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPermissaoPrograma is a Querydsl query type for PermissaoPrograma
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPermissaoPrograma extends EntityPathBase<PermissaoPrograma> {

    private static final long serialVersionUID = -1937602634L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPermissaoPrograma permissaoPrograma = new QPermissaoPrograma("permissaoPrograma");

    public final NumberPath<Long> codigoPerfil = createNumber("codigoPerfil", Long.class);

    public final NumberPath<Long> codigoPrograma = createNumber("codigoPrograma", Long.class);

    public final NumberPath<Long> codigoUsuario = createNumber("codigoUsuario", Long.class);

    public final BooleanPath flagCadastro = createBoolean("flagCadastro");

    public final BooleanPath flagExclusao = createBoolean("flagExclusao");

    public final BooleanPath flagLeitura = createBoolean("flagLeitura");

    public final NumberPath<Long> idPermissaoprograma = createNumber("idPermissaoprograma", Long.class);

    public final QPerfil perfil;

    public final QPrograma programa;

    public final QUsuario usuario;

    public QPermissaoPrograma(String variable) {
        this(PermissaoPrograma.class, forVariable(variable), INITS);
    }

    public QPermissaoPrograma(Path<? extends PermissaoPrograma> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPermissaoPrograma(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPermissaoPrograma(PathMetadata<?> metadata, PathInits inits) {
        this(PermissaoPrograma.class, metadata, inits);
    }

    public QPermissaoPrograma(Class<? extends PermissaoPrograma> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.perfil = inits.isInitialized("perfil") ? new QPerfil(forProperty("perfil")) : null;
        this.programa = inits.isInitialized("programa") ? new QPrograma(forProperty("programa"), inits.get("programa")) : null;
        this.usuario = inits.isInitialized("usuario") ? new QUsuario(forProperty("usuario"), inits.get("usuario")) : null;
    }

}

