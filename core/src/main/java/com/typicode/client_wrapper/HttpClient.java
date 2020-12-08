package com.typicode.client_wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author Grigor_Meliksetyan
 */

public class HttpClient implements Cloneable {

  private final static String EMPTY_STRING = "";
  private final static String QUESTION_MARK = "?";
  private String hostUrl;
  @Autowired
  private RestTemplate template;

  /**
   * Constructor with request destination configuration.
   *
   * @param hostUrl - URL to send requests.
   */
  public HttpClient(String hostUrl) {
    this.hostUrl = hostUrl;
  }

  public static HttpEntity<String> createRequest() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
    return new HttpEntity<String>(headers);
  }

  private ObjectMapper getObjectMapper() {
    return (ObjectMapper) template.getMessageConverters()
        .parallelStream()
        .filter(MappingJackson2HttpMessageConverter.class::isInstance)
        .findFirst()
        .get();
  }

  public <H, P> ResponseEntity<P> send(String requestParamString,
      Map<String, List<String>> requestHeaders, HttpMethod httpMethod,
      Class<H> requestModel, ParameterizedTypeReference<P> responseModel,
      Object[] requestParamsVariables) {
    String url =
        requestParamString == null ? hostUrl : hostUrl + QUESTION_MARK + requestParamString;

    HttpHeaders headers = new HttpHeaders(requestHeaders != null ? new HttpHeaders() {{
      putAll(requestHeaders);
    }} : new HttpHeaders());

    H reqBody = requestModel != null ? (H) requestModel : (H) EMPTY_STRING;

    HttpEntity<H> requestEntity = new HttpEntity<H>(reqBody, headers);

    requestParamsVariables =
        requestParamsVariables == null ? new Object[]{} : requestParamsVariables;

    ResponseEntity<P> response = template
        .exchange(url, httpMethod, requestEntity, responseModel, requestParamsVariables);
    return response;
  }

  public String getHostUrl() {
    return hostUrl;
  }

  public void setHostUrl(String hostUrl) {
    this.hostUrl = hostUrl;
  }

  @Override
  public HttpClient clone() {
    HttpClient clone = new HttpClient(this.hostUrl);
    clone.template = this.template;
    return clone;
  }
}
