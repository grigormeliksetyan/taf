package com.typicode.model.http.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

/**
 * @author Grigor_Meliksetyan
 */

@ToString(callSuper = true)
public class PostsResponse extends BaseModel {

  @JsonProperty("userId")
  private int userId;

  @JsonProperty("title")
  private String title;

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
