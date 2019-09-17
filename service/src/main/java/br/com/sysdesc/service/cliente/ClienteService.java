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

	private ClienteDAO clienteDAO;

	public ClienteService() {
		this(new ClienteDAO());
	}

	public ClienteService(ClienteDAO clienteDAO) {
		super(clienteDAO, Cliente::getIdCliente);

		this.clienteDAO = clienteDAO;
	}

	@Override
	public void validar(Cliente objetoPersistir) {

		if (StringUtil.isNullOrEmpty(objetoPersistir.getNome())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_NOME);
		}

		if (StringUtil.isNullOrEmpty(objetoPersistir.getFlagTipoCliente())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_TIPO_CLIENTE);
		}

		Boolean pessoaFisica = objetoPersistir.getFlagTipoCliente().equals(TipoClienteEnum.PESSOA_FISICA.getCodigo());

		if (pessoaFisica) {

			if (StringUtil.isNullOrEmpty(StringUtil.formatarNumero(objetoPersistir.getCgc()))) {
				throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_CPF);
			}

			if (!CPFUtil.isCPFValido(objetoPersistir.getCgc())) {
				throw new SysDescException(MensagemConstants.MENSAGEM_CPF_INVALIDO);
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

		if (StringUtil.isNullOrEmpty(objetoPersistir.getEndereco())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_ENDERECO);
		}

		if (StringUtil.isNullOrEmpty(objetoPersistir.getNumero())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_NUMERO);
		}

		if (StringUtil.isNullOrEmpty(objetoPersistir.getBairro())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_BAIRRO);
		}

		if (StringUtil.isNullOrEmpty(objetoPersistir.getCep())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_CEP);
		}

		if (objetoPersistir.getCidade() == null) {
			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_CIDADE);
		}

		if (pessoaFisica && StringUtil.isNullOrEmpty(objetoPersistir.getSexo())) {
			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_SEXO);
		}

		if (objetoPersistir.getSituacao() == null) {
			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_SITUACAO);
		}
	}

	public Cliente buscarClientePorCpf(String cgc, Long idCliente) {

		return clienteDAO.buscarClientePorCpf(cgc, idCliente);
	}

}
