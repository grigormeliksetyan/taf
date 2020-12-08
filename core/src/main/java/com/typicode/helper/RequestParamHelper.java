package com.typicode.helper;

import com.typicode.client_wrapper.HttpClient;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Grigor_Meliksetyan
 */

public class RequestParamHelper {

  public static HttpClient finalizeRequestUri(HttpClient httpClient, String pathParams) {
    return finalizeRequestUri(httpClient, List.of(pathParams));
  }

  public static HttpClient finalizeRequestUri(HttpClient httpClient, List<String> pathParams) {
    HttpClient client = httpClient.clone();
    client.setHostUrl(String.format(client.getHostUrl(), pathParams.toArray()));
    return client;
  }

  public static String buildParamStringFromMap(Map<String, Object> params) {
    return params.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
        .collect(Collectors.joining("&"));
  }

}
