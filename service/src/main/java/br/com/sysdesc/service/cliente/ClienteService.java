package br.com.sysdesc.service.cliente;

import br.com.sysdesc.repository.dao.ClienteDAO;
import br.com.sysdesc.repository.enumeradores.TipoClienteEnum;
import br.com.sysdesc.repository.model.Cliente;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.CPFUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class ClienteService extends AbstractGenericService<Cliente> {

	public ClienteService() {
		super(new ClienteDAO(), Cliente::getIdCliente);
	}

	@Override
	public void validar(Cliente objetoPersistir) {

		if (StringUtil.isNullOrEmpty(objetoPersistir.getNome())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_NOME);
		}
		if (StringUtil.isNullOrEmpty(objetoPersistir.getFlagTipoCliente())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_TIPO_CLIENTE);
		}

		if (objetoPersistir.getFlagTipoCliente().equals(TipoClienteEnum.PESSOA_FISICA.getCodigo())) {

			if (StringUtil.isNullOrEmpty(StringUtil.formatarNumero(objetoPersistir.getCgc()))) {
				throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_CPF);
			}

			if (!CPFUtil.isCPFValido(objetoPersistir.getCgc())) {
				throw new SysDescException(MensagemConstants.MENSAGEM_CPF_INVALIDO);
			}
			if (StringUtil.isNullOrEmpty(objetoPersistir.getSexo())) {
				throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_SEXO);
			}

		}

		if (objetoPersistir.getFlagTipoCliente().equals(TipoClienteEnum.PESSOA_JURIDICA.getCodigo())) {

			if (StringUtil.isNullOrEmpty(StringUtil.formatarNumero(objetoPersistir.getCgc()))) {
				throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_CNPJ);
			}

			if (CPFUtil.isCPFValido(objetoPersistir.getCgc())) {
				throw new SysDescException(MensagemConstants.MENSAGEM_CNPJ_INVALIDO);
			}

		}

		if (objetoPersistir.getCidade() == null) {
			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_CIDADE);
		}

		if (objetoPersistir.getSituacao() == null) {
			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_SITUACAO);
		}
	}

}
