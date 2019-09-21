package br.com.sysdesc.pesquisa.components;

import java.util.Comparator;
import java.util.List;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import br.com.sysdesc.pesquisa.enumeradores.TipoTamanhoEnum;
import br.com.sysdesc.repository.enumeradores.TipoFieldEnum;
import br.com.sysdesc.repository.model.Pesquisa;
import br.com.sysdesc.repository.model.PesquisaCampo;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportBuilder<T> {

	private FastReportBuilder fastReportBuilder = new FastReportBuilder();
	private Pesquisa pesquisa;
	private List<T> data;
	private Long size;

	public ReportBuilder(Pesquisa pesquisa, List<T> data) {
		this(pesquisa, data, 950L);
	}

	public ReportBuilder(Pesquisa pesquisa, List<T> data, Long size) {

		this.pesquisa = pesquisa;
		this.data = data;
		this.size = size;
	}

	public JasperPrint build() throws ColumnBuilderException, ClassNotFoundException, JRException {

		fastReportBuilder.setTitle(pesquisa.getDescricao());
		fastReportBuilder.setDetailHeight(15);

		Long tamanhoVariavel = pesquisa.getPesquisaCampos().stream()
				.filter(x -> x.getFlagTipoTamanho().equals(TipoTamanhoEnum.FLEX.getCodigo()))
				.mapToLong(x -> x.getNumeroTamanho()).sum();

		pesquisa.getPesquisaCampos().stream().sorted(Comparator.comparing(PesquisaCampo::getFlagOrdem));

		for (PesquisaCampo pesquisa : pesquisa.getPesquisaCampos()) {

			Integer tamanho = getTamanhoCampo(pesquisa, tamanhoVariavel);

			fastReportBuilder.addColumn(pesquisa.getDescricao(), pesquisa.getCampo(),
					TipoFieldEnum.forCodigo(pesquisa.getFlagTipoDado()).getType().getName(), tamanho);
		}

		JRDataSource ds = new JRBeanCollectionDataSource(data);

		return DynamicJasperHelper.generateJasperPrint(fastReportBuilder.build(), new ClassicLayoutManager(), ds);
	}

	private int getTamanhoCampo(PesquisaCampo pesquisa, Long tamanhoVariavel) {

		if (pesquisa.getFlagTipoTamanho().equals(TipoTamanhoEnum.PIXELS.getCodigo())) {
			return pesquisa.getNumeroTamanho().intValue() / 2;
		}

		Long tamanho = size / tamanhoVariavel * pesquisa.getNumeroTamanho() / 2;

		return tamanho.intValue();
	}

}
