package com.typicode.service;

import static com.typicode.helper.RequestParamHelper.buildParamStringFromMap;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import com.typicode.client_wrapper.HttpClient;
import com.typicode.constants.LegacyServiceRequestConstants;
import com.typicode.model.http.response.BaseModel;
import com.typicode.model.http.response.CommentsResponse;
import com.typicode.model.http.response.PostsResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Grigor_Meliksetyan
 */

@Component
public class TypiCodeService {

  @Autowired
  private HttpClient typiCodeServicePostsHttpClient;

  @Autowired
  private HttpClient typiCodeServicePostsIdHttpClient;

  @Autowired
  private HttpClient typiCodeServicePostsIdCommentsHttpClient;

  @Autowired
  private HttpClient typiCodeServiceCommentsHttpClient;

  public ResponseEntity<List<PostsResponse>> getPosts() {
    ResponseEntity<List<PostsResponse>> response = typiCodeServicePostsHttpClient.send(null,
        null,
        GET,
        null,
        new ParameterizedTypeReference<List<PostsResponse>>() {
        },
        null
    );
    return response;
  }

  public ResponseEntity<PostsResponse> getPostsById(Object id) {
    HttpClient httpClient = typiCodeServicePostsIdHttpClient.clone();
    httpClient.setHostUrl(String.format(httpClient.getHostUrl(), id));
    ResponseEntity<PostsResponse> response = httpClient
        .send(null, null, GET, null, new ParameterizedTypeReference<PostsResponse>() {
        }, null);
    return response;
  }

  public ResponseEntity<List<CommentsResponse>> getPostsCommentsById(Object id) {
    HttpClient httpClient = typiCodeServicePostsIdCommentsHttpClient.clone();
    httpClient.setHostUrl(String.format(httpClient.getHostUrl(), id));
    ResponseEntity<List<CommentsResponse>> response = httpClient.send(null,
        null,
        GET,
        null,
        new ParameterizedTypeReference<List<CommentsResponse>>() {
        },
        null
    );
    return response;
  }

  public ResponseEntity<List<CommentsResponse>> getCommentsByPostParam(Map<String, Object> params) {
    Map<String, Object> fullParams = fullFillHeadersOrParams(params);
    ResponseEntity<List<CommentsResponse>> response = typiCodeServiceCommentsHttpClient
        .send(buildParamStringFromMap(fullParams),
            null,
            GET,
            null,
            new ParameterizedTypeReference<List<CommentsResponse>>() {
            },
            null
        );
    return response;
  }

  public ResponseEntity<BaseModel> postPosts() {
    ResponseEntity<BaseModel> response = typiCodeServicePostsHttpClient.send(null,
        null,
        POST,
        null,
        new ParameterizedTypeReference<BaseModel>() {
        },
        null
    );
    return response;
  }

  public ResponseEntity<BaseModel> putPostsById(Object id) {
    HttpClient httpClient = typiCodeServicePostsIdHttpClient.clone();
    httpClient.setHostUrl(String.format(httpClient.getHostUrl(), id));
    ResponseEntity<BaseModel> response = httpClient
        .send(null, null, PUT, null, new ParameterizedTypeReference<BaseModel>() {
        }, null);
    return response;
  }

  public ResponseEntity<PostsResponse> patchPostsById(Object id) {
    HttpClient httpClient = typiCodeServicePostsIdHttpClient.clone();
    httpClient.setHostUrl(String.format(httpClient.getHostUrl(), id));
    ResponseEntity<PostsResponse> response = httpClient
        .send(null, null, PATCH, null, new ParameterizedTypeReference<PostsResponse>() {
        }, null);
    return response;
  }

  public ResponseEntity<BaseModel> deletePostsById(Object id) {
    HttpClient httpClient = typiCodeServicePostsIdHttpClient.clone();
    httpClient.setHostUrl(String.format(httpClient.getHostUrl(), id));
    ResponseEntity<BaseModel> response = httpClient
        .send(null, null, DELETE, null, new ParameterizedTypeReference<BaseModel>() {
        }, null);
    return response;
  }

  Map<String, Object> fullFillHeadersOrParams(Map<String, Object> headersOrParams,
      LegacyServiceRequestConstants.HeadersParamsInterface... defaultHeadersOrParams) {
    Map<String, Object> fullFilledHeadersOrParams = new HashMap<>();
    Arrays.stream(defaultHeadersOrParams)
        .forEach(param -> fullFilledHeadersOrParams.put(param.key, param.defaultValue));
    if (headersOrParams != null && !headersOrParams.isEmpty()) {
      fullFilledHeadersOrParams.putAll(headersOrParams);
    }
    return fullFilledHeadersOrParams;
  }

}
