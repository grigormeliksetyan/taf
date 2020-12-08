package com.typicode;

import static com.typicode.constants.RequestConstants.TypiCode.BODY;
import static com.typicode.constants.RequestConstants.TypiCode.EMAIL;
import static com.typicode.constants.RequestConstants.TypiCode.ID;
import static com.typicode.constants.RequestConstants.TypiCode.NAME;
import static com.typicode.constants.RequestConstants.TypiCode.POST_ID;

import com.typicode.helper.QueryParamWithCode;
import com.typicode.model.http.response.CommentsResponse;
import com.typicode.service.TypiCodeService;
import com.typicode.test.BaseTest;
import io.qameta.allure.Description;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Grigor_Meliksetyan
 */

public class CommentsTest extends BaseTest {

  @Autowired
  private TypiCodeService typiCodeService;

  private static Stream<QueryParamWithCode> provideQueryParams() throws CloneNotSupportedException {
    return Stream.of(
        providePostIdParams(),
        provideIdParams(),
        provideNameParams(),
        provideEmailParams(),
        provideBodyParams()
    ).flatMap(Function.identity());
  }

  @Test
  @Description("Verify that get request of /posts endpoint response data structure is valid")
  public void getPostByIdHappyPathTest() {
    ResponseEntity<List<CommentsResponse>> response = typiCodeService.getCommentsByPostParam(null);
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(500, response.getBody().size());
    response.getBody().parallelStream().forEach(post -> {
      Assertions.assertTrue(post.getId() > 0);
      Assertions.assertTrue(post.getId() > 0);
      Assertions.assertNotNull(post.getEmail());
      Assertions.assertNotNull(post.getBody());
    });
  }

  @ParameterizedTest
  @MethodSource("provideQueryParams")
  @Description("Verify that get request of /posts endpoint response data structure is valid")
  public void getPostByIdParamsTest(QueryParamWithCode param) {
    ResponseEntity<List<CommentsResponse>> response = typiCodeService
        .getCommentsByPostParam(param.getAsMap());
    Assertions.assertEquals(param.getStatus(), response.getStatusCodeValue());
    Assertions.assertNotNull(response.getBody());
    validateParamFilter(param, response);
  }

  private void validateParamFilter(QueryParamWithCode param,
      ResponseEntity<List<CommentsResponse>> response) {
    response.getBody().parallelStream().forEach(post -> {
      switch (param.getParamName()) {
        case POST_ID:
          Assertions.assertEquals(param.getValue(), post.getPostId());
          break;
        case ID:
          Assertions.assertEquals(param.getValue(), post.getId());
          break;
        case NAME:
          Assertions.assertEquals(param.getValue(), post.getName());
          break;
        case EMAIL:
          Assertions.assertEquals(param.getValue(), post.getEmail());
          break;
        case BODY:
          Assertions.assertEquals(param.getValue(), post.getBody());
          break;
      }
    });
  }

}
