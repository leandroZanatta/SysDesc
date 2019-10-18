package br.com.sysdesc.gerenciador.inicializacao.http;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;

import br.com.sysdesc.gerenciador.inicializacao.http.enumerators.HttpMethod;
import br.com.sysdesc.gerenciador.inicializacao.vo.ApiMethodVO;
import br.com.sysdesc.gerenciador.inicializacao.vo.InternalErrorVO;
import br.com.sysdesc.gerenciador.inicializacao.vo.NotFoundVO;
import br.com.sysdesc.gerenciador.inicializacao.vo.OKOptionsVO;

public class Request {

    private HttpMethod method;
    private String fileRequested;
    private Map<String, String> headers;
    private String payload;
    private PrintWriter printWriter;
    private BufferedOutputStream bufferedOutputStream;

    public Request(HttpMethod method, String fileRequested, Map<String, String> headers, String payload, PrintWriter printWriter,
            BufferedOutputStream bufferedOutputStream) {
        this.method = method;
        this.fileRequested = fileRequested;
        this.headers = headers;
        this.payload = payload;
        this.printWriter = printWriter;
        this.bufferedOutputStream = bufferedOutputStream;
    }

    public void process() throws IOException {

        if (this.method.equals(HttpMethod.OPTIONS)) {

            OKOptionsVO okOptionsVO = new OKOptionsVO();
            okOptionsVO.setPath(this.fileRequested);

            retornarOk(okOptionsVO);

            return;
        }

        Optional<ApiMethodVO> metodo = this.findMethod();

        if (metodo.isPresent()) {

            try {

                ApiMethodVO apiMethodVO = metodo.get();

                Object[] args = {};

                retornarOk(apiMethodVO.getMethod().invoke(apiMethodVO.getInstance(), args));

            } catch (Exception e) {

                retornarInternalError(e);
            }

            return;
        }

        retornarNotFound();
    }

    private void retornarInternalError(Exception e) throws IOException {

        InternalErrorVO objError = new InternalErrorVO();
        objError.setPath(this.fileRequested);
        objError.setMessage(e.getMessage());

        retornoResponse(500, objError, "Internal Server Error");
    }

    private void retornarOk(Object object) throws IOException {

        retornoResponse(200, object, "Ok");
    }

    private void retornarNotFound() throws IOException {

        NotFoundVO notFoundVO = new NotFoundVO();
        notFoundVO.setPath(this.fileRequested);

        retornoResponse(404, notFoundVO, "Not Found");
    }

    private void retornoResponse(Integer code, Object response, String serverStatus) throws IOException {

        String objJson = new Gson().toJson(response);

        printWriter.println(String.format("HTTP/1.1 %d %s", code, serverStatus));
        printWriter.println("Server: Java HTTP Server");
        printWriter.println("Date: " + new Date());
        printWriter.println("Content-type: " + "application/json");
        printWriter.println("Content-length: " + objJson.length());
        printWriter.println("Access-Control-Allow-Origin: *");
        printWriter.println("Access-Control-Allow-Headers: *");
        printWriter.println();

        bufferedOutputStream.write(objJson.getBytes());

        printWriter.flush();
        bufferedOutputStream.flush();

    }

    private Optional<ApiMethodVO> findMethod() {

        if (ApisController.getInstance().getApis().containsKey(this.method)) {
            return Optional.ofNullable(ApisController.getInstance().getApis().get(this.method).get(this.fileRequested));
        }

        return Optional.empty();
    }

}
