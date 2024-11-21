package ar.edu.ubp.das.indecback.utils;

import com.google.gson.Gson;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Httpful {

    private final Client client;
    private WebTarget target;
    private String method;
    private Entity<?> entity;
    private String authHeader;
    private final Gson gson = new Gson();

    public Httpful(String baseUrl) {
        this.client = ClientBuilder.newClient();
        this.target = this.client.target(baseUrl);
    }

    public Httpful path(String path) {
        this.target = this.target.path(path);
        return this;
    }

    public Httpful method(String method) {
        this.method = method;
        return this;
    }

    public Httpful basicAuth(String username, String password) {
        String token = Base64.getEncoder().encodeToString((username + ":"
                + password).getBytes(StandardCharsets.UTF_8));
        this.authHeader = "Basic " + token;
        return this;
    }

    public Httpful addQueryParam(String key, String value) {
        this.target = this.target.queryParam(key, value);
        return this;
    }

    public Httpful post(Object body) {
        this.method = HttpMethod.POST;
        this.entity = Entity.entity(gson.toJson(body),
                MediaType.APPLICATION_JSON);
        return this;
    }

    public Httpful put(Object body) {
        this.method = HttpMethod.PUT;
        this.entity = Entity.entity(gson.toJson(body),
                MediaType.APPLICATION_JSON);
        return this;
    }

    public <T> T execute(Class<T> responseType) {
        return executeRequest(responseType);
    }

    public <T> T execute(Type responseType) {
        return executeRequest(responseType);
    }

    private <T> T executeRequest(Object responseType) {
        Response response = sendRequest();
        if (response.getStatus() == 200) {
            String jsonResponse = response.readEntity(String.class);
            if (responseType instanceof Class<?>) {
                return this.gson.fromJson(jsonResponse, (Class<T>)
                        responseType);
            }
            else {
                return this.gson.fromJson(jsonResponse, (Type)
                        responseType);
            }
        }
        else {
            String errorMessage = response.readEntity(String.class);
            throw new RuntimeException("Error en la solicitud: " +
                    response.getStatus() + ": " + errorMessage);
        }
    }

    private Response sendRequest() {
        Invocation.Builder requestBuilder =
                this.target.request(MediaType.APPLICATION_JSON);

        if (this.authHeader != null) {
            requestBuilder.header(HttpHeaders.AUTHORIZATION,
                    this.authHeader);
        }
        switch (this.method) {
            case HttpMethod.GET:
                return requestBuilder.get();
            case HttpMethod.POST:
                return requestBuilder.post(this.entity);
            case HttpMethod.PUT:
                return requestBuilder.put(this.entity);
            case HttpMethod.DELETE:
                return requestBuilder.delete();
            default:
                throw new RuntimeException("Método HTTP no soportado: " +
                        this.method);
        }
    }

}
