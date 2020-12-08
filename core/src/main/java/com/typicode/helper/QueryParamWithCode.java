package com.typicode.helper;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpStatus;

/**
 * @author Grigor_Meliksetyan
 */

public class QueryParamWithCode {

  private String paramName;
  private Object value;
  private int status;

  public QueryParamWithCode(String paramName) {
    this(paramName, null, HttpStatus.SC_OK);
  }

  public QueryParamWithCode(String paramName, Object value, int status) {
    this.paramName = paramName;
    this.value = value;
    this.status = status;
  }

  public String getParamName() {
    return paramName;
  }

  public QueryParamWithCode setParamName(String paramName) {
    this.paramName = paramName;
    return this;
  }

  public Object getValue() {
    return value;
  }

  public QueryParamWithCode setValue(Object value) {
    this.value = value;
    return this;
  }

  public int getStatus() {
    return status;
  }

  public QueryParamWithCode setStatus(int status) {
    this.status = status;
    return this;
  }

  @Override
  public QueryParamWithCode clone() throws CloneNotSupportedException {
    return new QueryParamWithCode(getParamName(), getValue(), getStatus());
  }

  public Map<String, Object> getAsMap(Map<String, Object> otherFields) {
    Map<String, Object> res = new HashMap<>();
    otherFields.forEach((key, value) -> {
      if (!getParamName().equals(key)) {
        res.putIfAbsent(key, value);
      }
    });

    return res;
  }

  public Map<String, Object> getAsMap() {
    Map<String, Object> res = new HashMap<>();
    res.put(getParamName(), getValue());
    return res;
  }
}
