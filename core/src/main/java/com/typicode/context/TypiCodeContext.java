package com.typicode.context;

import com.typicode.client_wrapper.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Grigor_Meliksetyan
 */
@Component
@PropertySource("classpath:/environment/${env}/typicode.properties")
public class TypiCodeContext extends Context {

  @Bean
  public HttpClient typiCodeServiceHttpClient(@Value("${service.typicode.url}") String url) {
    return new HttpClient(url);
  }

  @Bean
  public HttpClient typiCodeServicePostsHttpClient(
      @Value("#{typiCodeServiceHttpClient.getHostUrl() + T(com.typicode.constants.Routes).postsEndpoint}") String url) {
    return new HttpClient(url);
  }

  @Bean
  public HttpClient typiCodeServicePostsIdHttpClient(
      @Value("#{typiCodeServiceHttpClient.getHostUrl() + T(com.typicode.constants.Routes).postsIdEndpoint}") String url) {
    return new HttpClient(url);
  }

  @Bean
  public HttpClient typiCodeServicePostsIdCommentsHttpClient(
      @Value("#{typiCodeServiceHttpClient.getHostUrl() + T(com.typicode.constants.Routes).postsIdCommentsEndpoint}") String url) {
    return new HttpClient(url);
  }

  @Bean
  public HttpClient typiCodeServiceCommentsHttpClient(
      @Value("#{typiCodeServiceHttpClient.getHostUrl() + T(com.typicode.constants.Routes).commentsEndpoint}") String url) {
    return new HttpClient(url);
  }
}
