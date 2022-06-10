package coursework;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class SimplestServerHttpHandler implements HttpHandler {
    static int requestCounter = 0;
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String[] requestParams = null;
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods","GET,POST");
        if ("GET".equals(httpExchange.getRequestMethod())) {
            requestParams = getRequestParams(httpExchange);
        }
        if ("POST".equals(httpExchange.getRequestMethod())) {
            requestParams = getRequestParams(httpExchange);
        }


        returnResponse(httpExchange, requestParams);
    }

    /**
     * gets params from browser address line
     *
     * @param httpExchange HttpExchange
     *
     * @return address line params
     */
    private String[] getRequestParams(HttpExchange httpExchange) {
        String parameters = httpExchange.getRequestURI().toString().split("\\?")[1];
        String[] params = parameters.split("&");
        return params;
    }

    /**
     * returns response to browser
     *
     * @param httpExchange       HttpExchange
     * @param requestParamValues
     *
     * @throws IOException
     */
    private void returnResponse(HttpExchange httpExchange, String[] requestParamValues) throws IOException {
        requestCounter++;
        System.out.println("Request received: " + requestCounter);
        OutputStream outputStream = httpExchange.getResponseBody();

        // build test response of all parameters
        StringBuilder response = new StringBuilder("");

        if(requestParamValues[0].equals("start=1")){
            response.append(Request.start());
        }
        if(requestParamValues[0].equals("add=1")){
            String result = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody())).lines().collect(Collectors.joining("\n"));
            response.append(Request.add(result));
        }
        if(requestParamValues[0].equals("delete=1")){
            String result = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody())).lines().collect(Collectors.joining("\n"));
            response.append(Request.delete(result));
        }
        if(requestParamValues[0].equals("edit=1")){
            String result = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody())).lines().collect(Collectors.joining("\n"));
            response.append(Request.edit(result));
        }

        httpExchange.sendResponseHeaders(0, response.length());
        outputStream.write(response.toString().getBytes());
        outputStream.flush();
        outputStream.close();
    }
}