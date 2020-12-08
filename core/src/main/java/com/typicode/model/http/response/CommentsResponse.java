package com.typicode.model.http.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

/**
 * @author Grigor_Meliksetyan
 */

@ToString(callSuper = true)
public class CommentsResponse extends BaseModel {

  @JsonProperty("postId")
  private int postId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("email")
  private String email;

  public int getPostId() {
    return postId;
  }

  public void setPostId(int postId) {
    this.postId = postId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
