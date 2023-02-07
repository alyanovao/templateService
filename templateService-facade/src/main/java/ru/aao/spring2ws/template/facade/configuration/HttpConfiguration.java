package ru.aao.spring2ws.template.facade.configuration;

import org.apache.camel.component.http.HttpClientConfigurer;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

//Метод генерации httpClientConfigurer http и https взаимодействия, для использования раскоментировать и указать httpClientConfigurer в toD
//Пример в https://giturpo.bank.srv/dboflgroup/smartsearch/-/blob/master/smartSearch-facade/src/main/java/ru/kkb/smartsearch/facade/routes/SearchPayRoute.java
//@Service(value = "selfSignedHttpClientConfigurer")
public class HttpConfiguration implements HttpClientConfigurer {
/**
* @author Alyanov Artem 24.11.2022
*/

        @Override
        public void configureHttpClient(HttpClientBuilder clientBuilder) {
            try
            {
                final SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (x509CertChain, authType) -> true).build();

                clientBuilder
                        .disableRedirectHandling()
                        .setSSLContext(sslContext)
                        .setConnectionManager(new PoolingHttpClientConnectionManager(RegistryBuilder
                                .<ConnectionSocketFactory> create()
                                .register("http", PlainConnectionSocketFactory.INSTANCE)
                                .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
                                .build()));
            } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
                e.printStackTrace();
            }
        }
}
