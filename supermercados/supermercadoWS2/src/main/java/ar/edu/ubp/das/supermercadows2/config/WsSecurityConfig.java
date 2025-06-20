
package ar.edu.ubp.das.supermercadows2.config;

// Clase que se añade para manejar el interceptor de seguridad
// Si queres regenerar comentar toda la clase

import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;

import java.util.Collections;
import java.util.List;

@EnableWs
@Configuration
public class WsSecurityConfig extends WsConfigurerAdapter {

    @Value("${ws.security.username}")
    private String username;

    @Value("${ws.security.password}")
    private String password;

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        securityInterceptor.setValidationActions(WSHandlerConstants.USERNAME_TOKEN);

        securityInterceptor.setValidationCallbackHandler(passwordValidationCallbackHandler());
        return securityInterceptor;
    }

    @Bean
    public SimplePasswordValidationCallbackHandler passwordValidationCallbackHandler() {
        SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
        callbackHandler.setUsersMap(Collections.singletonMap(username, password));
        return callbackHandler;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(securityInterceptor());
    }

}
