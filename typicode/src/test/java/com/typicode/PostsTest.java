package com.typicode;

import com.typicode.model.http.response.BaseModel;
import com.typicode.model.http.response.PostsResponse;
import com.typicode.service.TypiCodeService;
import com.typicode.test.BaseTest;
import io.qameta.allure.Description;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Grigor_Meliksetyan
 */

public class PostsTest extends BaseTest {

  @Autowired
  private TypiCodeService typiCodeService;

  @Test
  @Description("Verify that get request of /posts endpoint response data structure is valid")
  public void getPostHappyPathTest() {
    ResponseEntity<List<PostsResponse>> postsGetResponse = typiCodeService.getPosts();
    Assertions.assertEquals(HttpStatus.OK.value(), postsGetResponse.getStatusCodeValue());
    Assertions.assertNotNull(postsGetResponse.getBody());
    Assertions.assertEquals(100, postsGetResponse.getBody().size());
    postsGetResponse.getBody().parallelStream().forEach(post -> {
      Assertions.assertTrue(post.getId() > 0);
      Assertions.assertTrue(post.getUserId() > 0);
      Assertions.assertNotNull(post.getTitle());
      Assertions.assertNotNull(post.getBody());
    });
  }

  @Test
  @Description("Verify that post request of /posts endpoint response user id is our expected")
  public void postPostHappyPathTest() {
    ResponseEntity<BaseModel> postsPostResponse = typiCodeService.postPosts();
    Assertions.assertEquals(HttpStatus.CREATED.value(), postsPostResponse.getStatusCodeValue());
    Assertions.assertNotNull(postsPostResponse.getBody());
    Assertions.assertEquals(101, postsPostResponse.getBody().getId());
  }
}
