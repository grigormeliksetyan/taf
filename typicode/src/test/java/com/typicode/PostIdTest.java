package com.typicode;

import com.typicode.model.http.response.BaseModel;
import com.typicode.model.http.response.PostsResponse;
import com.typicode.service.TypiCodeService;
import com.typicode.test.BaseTest;
import io.qameta.allure.Description;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Grigor_Meliksetyan
 */

public class PostIdTest extends BaseTest {

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
  @Description("Verify that get request of /posts endpoint response data structure is valid")
  public void getPostByIdHappyPathTest() {
    ResponseEntity<PostsResponse> response = typiCodeService.getPostsById(1);
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(1, response.getBody().getId());
    Assertions.assertEquals(1, response.getBody().getUserId());
    Assertions
        .assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            response.getBody().getTitle());
    Assertions.assertEquals(
        "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
        response.getBody().getBody());
  }

  @ParameterizedTest
  @MethodSource("provideQueryParams")
  @Description("Test get request of /posts endpoint post id invalid data format")
  public void getPostByIdInvalidIdFormatTest(Object id, int statusCode) {
    ResponseEntity<PostsResponse> response = typiCodeService.getPostsById(id);
    Assertions.assertEquals(statusCode, response.getStatusCodeValue());
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 24})
  @Description("Verify that put request of /posts endpoint response data structure is valid")
  public void putPostByIdHappyPathTest(int id) {
    ResponseEntity<BaseModel> response = typiCodeService.putPostsById(id);
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(id, response.getBody().getId());
  }

  @ParameterizedTest
  @MethodSource("provideQueryParams")
  @Description("Test put request of /posts endpoint post id invalid data format")
  public void putPostByIdInvalidIdFormatTest(Object id, int statusCode) {
    ResponseEntity<BaseModel> response = typiCodeService.putPostsById(id);
    Assertions.assertEquals(statusCode, response.getStatusCodeValue());
    // We need to make sure that there's no 500 status code.
    // Our custom error handler in rest template returns error in that case.
  }

  @Test
  @Description("Verify that put request of /posts endpoint response data structure is valid")
  public void patchPostByIdHappyPathTest() {
    ResponseEntity<PostsResponse> response = typiCodeService.patchPostsById(1);
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(1, response.getBody().getId());
    Assertions.assertEquals(1, response.getBody().getUserId());
    Assertions
        .assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            response.getBody().getTitle());
    Assertions.assertEquals(
        "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
        response.getBody().getBody());
  }

  @ParameterizedTest
  @MethodSource("provideQueryParams")
  @Description("Test put request of /posts endpoint post id invalid data format")
  public void patchPostByIdInvalidIdFormatTest(Object id, int statusCode) {
    ResponseEntity<PostsResponse> response = typiCodeService.patchPostsById(id);
    Assertions.assertEquals(statusCode, response.getStatusCodeValue());
    // We need to make sure that there's no 500 status code.
    // Our custom error handler in rest template returns error in that case.
  }

  @Test
  @Description("Verify that put request of /posts endpoint response data structure is valid")
  public void deletePostByIdHappyPathTest() {
    ResponseEntity<BaseModel> response = typiCodeService.deletePostsById(1);
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
  }

  @ParameterizedTest
  @MethodSource("provideQueryParams")
  @Description("Test put request of /posts endpoint post id invalid data format")
  public void deletePostByIdInvalidIdFormatTest(Object id, int statusCode) {
    ResponseEntity<PostsResponse> response = typiCodeService.patchPostsById(id);
    Assertions.assertEquals(statusCode, response.getStatusCodeValue());
    // We need to make sure that there's no 500 status code.
    // Our custom error handler in rest template returns error in that case.
  }
}
