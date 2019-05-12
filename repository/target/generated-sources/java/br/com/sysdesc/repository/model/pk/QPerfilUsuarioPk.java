package br.com.sysdesc.repository.model.pk;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPerfilUsuarioPk is a Querydsl query type for PerfilUsuarioPk
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QPerfilUsuarioPk extends BeanPath<PerfilUsuarioPk> {

    private static final long serialVersionUID = -770117960L;

    public static final QPerfilUsuarioPk perfilUsuarioPk = new QPerfilUsuarioPk("perfilUsuarioPk");

    public final NumberPath<Long> codigoPerfil = createNumber("codigoPerfil", Long.class);

    public final NumberPath<Long> codigoUsuario = createNumber("codigoUsuario", Long.class);

    public QPerfilUsuarioPk(String variable) {
        super(PerfilUsuarioPk.class, forVariable(variable));
    }

    public QPerfilUsuarioPk(Path<? extends PerfilUsuarioPk> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPerfilUsuarioPk(PathMetadata<?> metadata) {
        super(PerfilUsuarioPk.class, metadata);
    }

}

