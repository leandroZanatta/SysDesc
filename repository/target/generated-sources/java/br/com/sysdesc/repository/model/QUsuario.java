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

    public static final QUsuario usuario = new QUsuario("usuario");

    public final NumberPath<java.math.BigDecimal> cdCliente = createNumber("cdCliente", java.math.BigDecimal.class);

    public final NumberPath<Long> idUsuario = createNumber("idUsuario", Long.class);

    public final ListPath<PermissaoPrograma, QPermissaoPrograma> tbPermissaoprogramas = this.<PermissaoPrograma, QPermissaoPrograma>createList("tbPermissaoprogramas", PermissaoPrograma.class, QPermissaoPrograma.class, PathInits.DIRECT2);

    public final StringPath txSenha = createString("txSenha");

    public final StringPath txUsuario = createString("txUsuario");

    public QUsuario(String variable) {
        super(Usuario.class, forVariable(variable));
    }

    public QUsuario(Path<? extends Usuario> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsuario(PathMetadata<?> metadata) {
        super(Usuario.class, metadata);
    }

}

