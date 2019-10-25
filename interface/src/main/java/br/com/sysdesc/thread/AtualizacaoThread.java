package br.com.sysdesc.thread;

import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_ATUALIZAR_VERSAO;
import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_THREAD_VERSAO_INTEROMPIDA;
import static br.com.sysdesc.util.resources.Configuracoes.FOLDER_VERSOES;
import static br.com.sysdesc.util.resources.Configuracoes.VERSAO;
import static br.com.sysdesc.util.resources.Resources.OPTION_VALIDACAO;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import br.com.sysdesc.repository.dao.VersaoDAO;
import br.com.sysdesc.repository.model.Versao;
import br.com.sysdesc.util.classes.ExtratorZip;
import br.com.sysdesc.util.vo.VersaoERPVO;
import liquibase.util.file.FilenameUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtualizacaoThread extends Thread {

    private JPanel contentVersao;

    private JLabel lbVersao;

    private String versaoBase;

    private static final String URLVERSAO = "https://raw.githubusercontent.com/leandroZanatta/SysDesc/develop/versoes/versao.json";

    private VersaoDAO versaoDAO = new VersaoDAO();

    public AtualizacaoThread(JPanel contentVersao) {
        this.contentVersao = contentVersao;

        lbVersao = new JLabel();
    }

    @Override
    public void run() {

        this.verificarVersaoBase();

        VersaoERPVO versaoVO = this.recuperarVersaoInternet();

        this.verificarVersaoRemota(versaoVO);

    }

    private VersaoERPVO recuperarVersaoInternet() {

        URL arquivoUrl;

        try {

            arquivoUrl = new URL(URLVERSAO);

            URLConnection urlConnection = arquivoUrl.openConnection();
            urlConnection.setUseCaches(Boolean.FALSE);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {

                String inputLine;

                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {

                    stringBuilder.append(inputLine);
                }

                return new Gson().fromJson(stringBuilder.toString(), VersaoERPVO.class);

            }

        } catch (IOException e) {

            try {
                Thread.sleep(18000);

                return recuperarVersaoInternet();

            } catch (InterruptedException e1) {

                log.error(MENSAGEM_THREAD_VERSAO_INTEROMPIDA, e1);

                return null;
            }
        }

    }

    private void verificarVersaoRemota(VersaoERPVO versaoVO) {

        try {

            VersaoERPVO versaoLocal = getArquivoVersaoLocal();

            if (!versaoBase.equals(versaoVO.getVersaoERP())) {

                String mensagem = String.format(translate(MENSAGEM_ATUALIZAR_VERSAO), versaoVO.getVersaoERP());

                Integer retornoOpcao = JOptionPane.showConfirmDialog(null, mensagem, OPTION_VALIDACAO, JOptionPane.YES_NO_OPTION);

                if (retornoOpcao == JOptionPane.YES_OPTION) {

                    efetuarDownloadVersao(versaoVO.getArquivoERP());
                    efetuarDownloadVersao(versaoVO.getArquivoSysdesc());

                    new ExtratorZip().extrairVersao(criarArquivoVersao(versaoVO.getArquivoSysdesc()));

                    versaoLocal.setVersaoERP(versaoVO.getVersaoERP());
                    versaoLocal.setArquivoERP(versaoVO.getArquivoERP());
                }
            }

            FileUtils.writeStringToFile(new File(VERSAO), new Gson().toJson(versaoLocal), Charset.defaultCharset());

        } catch (Exception e) {

            try {
                Thread.sleep(18000);

                verificarVersaoRemota(versaoVO);

            } catch (InterruptedException e1) {

                log.error(MENSAGEM_THREAD_VERSAO_INTEROMPIDA);

                Thread.currentThread().interrupt();
            }
        }

    }

    private VersaoERPVO getArquivoVersaoLocal() {
        try {

            return new Gson().fromJson(FileUtils.readFileToString(new File(VERSAO), Charset.defaultCharset()), VersaoERPVO.class);
        } catch (Exception e) {

            return new VersaoERPVO();
        }

    }

    private void efetuarDownloadVersao(String urlVersao) throws IOException {

        JProgressBar progres = new JProgressBar();

        progres.setStringPainted(true);

        contentVersao.removeAll();

        contentVersao.add(progres);

        URL arquivoUrl = new URL(urlVersao);

        URLConnection urlConnection = arquivoUrl.openConnection();

        urlConnection.setUseCaches(Boolean.FALSE);

        Long tamanhoArquivo = urlConnection.getContentLengthLong();

        File arquivoVersao = criarArquivoVersao(urlVersao);

        try (BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                FileOutputStream fileOutputStream = new FileOutputStream(arquivoVersao)) {

            byte[] dataBuffer = new byte[1024];

            int bytesRead;

            Long bufferTotal = 0L;

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {

                bufferTotal += bytesRead;

                fileOutputStream.write(dataBuffer, 0, bytesRead);

                progres.setValue(Double.valueOf(bufferTotal.doubleValue() / tamanhoArquivo.doubleValue() * 100).intValue());

            }
        }

        contentVersao.removeAll();

        contentVersao.add(lbVersao);
    }

    private File criarArquivoVersao(String arquivo) throws IOException {
        log.info("criando arquivo Versão");

        File folderVersao = new File(FOLDER_VERSOES);

        if (!folderVersao.exists()) {
            log.info("gerando arquivo:" + folderVersao.getName());
            folderVersao.mkdir();
        }

        File arquivoVersao = new File(folderVersao, FilenameUtils.getName(new URL(arquivo).getPath()));

        if (!arquivoVersao.exists()) {
            log.info("gerando arquivo: " + arquivoVersao.getName());

            if (arquivoVersao.createNewFile()) {
                log.error("Arquivo de versão não pode ser gerado: " + arquivoVersao.getName());
            }
        }

        return arquivoVersao;
    }

    private void verificarVersaoBase() {

        Versao versao = versaoDAO.last();

        contentVersao.add(lbVersao);

        if (versao != null) {

            this.versaoBase = versao.getVersao();

            lbVersao.setText(this.versaoBase);

            return;
        }

        this.versaoBase = "0.0.0";
    }

}
