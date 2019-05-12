package br.com.sysdesc.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPerfil is a Querydsl query type for Perfil
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPerfil extends EntityPathBase<Perfil> {

    private static final long serialVersionUID = 467748442L;

    public static final QPerfil perfil = new QPerfil("perfil");

    public final StringPath descricao = createString("descricao");

    public final NumberPath<Long> idPerfil = createNumber("idPerfil", Long.class);

    public final ListPath<Pesquisa, QPesquisa> pesquisas = this.<Pesquisa, QPesquisa>createList("pesquisas", Pesquisa.class, QPesquisa.class, PathInits.DIRECT2);

    public final ListPath<Usuario, QUsuario> usuarios = this.<Usuario, QUsuario>createList("usuarios", Usuario.class, QUsuario.class, PathInits.DIRECT2);

    public QPerfil(String variable) {
        super(Perfil.class, forVariable(variable));
    }

    public QPerfil(Path<? extends Perfil> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPerfil(PathMetadata<?> metadata) {
        super(Perfil.class, metadata);
    }

}

