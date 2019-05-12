package br.com.sysdesc.repository.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPesquisaCampo is a Querydsl query type for PesquisaCampo
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPesquisaCampo extends EntityPathBase<PesquisaCampo> {

    private static final long serialVersionUID = 1222415691L;

    public static final QPesquisaCampo pesquisaCampo = new QPesquisaCampo("pesquisaCampo");

    public final StringPath alias = createString("alias");

    public final StringPath campo = createString("campo");

    public final NumberPath<Long> codigoPesquisa = createNumber("codigoPesquisa", Long.class);

    public final StringPath descricao = createString("descricao");

    public final NumberPath<Long> flagFormatacao = createNumber("flagFormatacao", Long.class);

    public final NumberPath<Long> flagOrdem = createNumber("flagOrdem", Long.class);

    public final NumberPath<Long> flagTipoDado = createNumber("flagTipoDado", Long.class);

    public final NumberPath<Long> flagTipoTamanho = createNumber("flagTipoTamanho", Long.class);

    public final StringPath formato = createString("formato");

    public final NumberPath<Long> idPesquisacampo = createNumber("idPesquisacampo", Long.class);

    public final NumberPath<Long> numeroTamanho = createNumber("numeroTamanho", Long.class);

    public QPesquisaCampo(String variable) {
        super(PesquisaCampo.class, forVariable(variable));
    }

    public QPesquisaCampo(Path<? extends PesquisaCampo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPesquisaCampo(PathMetadata<?> metadata) {
        super(PesquisaCampo.class, metadata);
    }

}

