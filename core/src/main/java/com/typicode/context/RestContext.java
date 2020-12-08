package com.typicode.context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import io.qameta.allure.httpclient.AllureHttpClientRequest;
import io.qameta.allure.httpclient.AllureHttpClientResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * @author Grigor_Meliksetyan
 */
@Component
public class RestContext extends Context {

  @Bean
  public RestTemplate restTemplate() {
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
        HttpClientBuilder.create().addInterceptorFirst(new AllureHttpClientRequest())
            .addInterceptorLast(new AllureHttpClientResponse())
            .build());
    RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
    CustomResponseErrorHandler errorHandler = new CustomResponseErrorHandler();
    restTemplate.setErrorHandler(errorHandler);
    final StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(
        StandardCharsets.UTF_8);
    final ByteArrayHttpMessageConverter byteArrayConverter = new ByteArrayHttpMessageConverter();
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    mappingJackson2HttpMessageConverter.getObjectMapper()
        .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mappingJackson2HttpMessageConverter.getObjectMapper()
        .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    messageConverter.setWriteAcceptCharset(false);
    List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>() {
    };
    messageConverterList.add(messageConverter);
    messageConverterList.add(byteArrayConverter);
    messageConverterList.add(mappingJackson2HttpMessageConverter);
    restTemplate.getMessageConverters().clear();
    restTemplate.setMessageConverters(messageConverterList);
    return restTemplate;
  }

  private static class CustomResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public boolean hasError(@Nonnull ClientHttpResponse response) throws IOException {
      return response.getStatusCode().is5xxServerError();
    }

  }
}
