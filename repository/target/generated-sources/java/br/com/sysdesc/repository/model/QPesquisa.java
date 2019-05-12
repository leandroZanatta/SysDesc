package br.com.sysdesc.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPesquisa is a Querydsl query type for Pesquisa
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPesquisa extends EntityPathBase<Pesquisa> {

    private static final long serialVersionUID = -1426167165L;

    public static final QPesquisa pesquisa = new QPesquisa("pesquisa");

    public final NumberPath<Long> codigoTabela = createNumber("codigoTabela", Long.class);

    public final NumberPath<Long> idPesquisa = createNumber("idPesquisa", Long.class);

    public final ListPath<Perfil, QPerfil> perfils = this.<Perfil, QPerfil>createList("perfils", Perfil.class, QPerfil.class, PathInits.DIRECT2);

    public QPesquisa(String variable) {
        super(Pesquisa.class, forVariable(variable));
    }

    public QPesquisa(Path<? extends Pesquisa> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPesquisa(PathMetadata<?> metadata) {
        super(Pesquisa.class, metadata);
    }

}

