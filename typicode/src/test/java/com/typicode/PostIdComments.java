package com.typicode;

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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Grigor_Meliksetyan
 */

public class PostIdComments extends BaseTest {

  @Autowired
  private TypiCodeService typiCodeService;

  private static Stream<Arguments> provideQueryParams() throws CloneNotSupportedException {
    return Stream.of(
        provideStrings(),
        provideIntegers(),
        provideDoubles()
    ).flatMap(Function.identity());
  }

  @Test
  @Description("Verify that get request of /posts/{id}/comments endpoint response data structure is valid and filtered by given id")
  public void getPostByIdHappyPathTest() {
    ResponseEntity<List<CommentsResponse>> response = typiCodeService.getPostsCommentsById(1);
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(5, response.getBody().size());
    validateDataStructureAndId(response);
  }

  @ParameterizedTest
  @MethodSource("provideQueryParams")
  @Description("Test get request of /posts/{id}/comments endpoint post id invalid data format")
  public void getPostByIdInvalidIdFormatTest(Object id, int statusCode) {
    ResponseEntity<List<CommentsResponse>> response = typiCodeService.getPostsCommentsById(id);
    Assertions.assertEquals(statusCode, response.getStatusCodeValue());
  }

  private void validateDataStructureAndId(ResponseEntity<List<CommentsResponse>> response) {
    response.getBody().parallelStream().forEach(post -> {
      Assertions.assertTrue(post.getId() > 0);
      Assertions.assertEquals(1, post.getPostId());
      Assertions.assertNotNull(post.getEmail());
      Assertions.assertNotNull(post.getBody());
    });
  }
}
