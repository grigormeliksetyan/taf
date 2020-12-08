package com.typicode.test;

import static com.typicode.constants.RequestConstants.TypiCode.BODY;
import static com.typicode.constants.RequestConstants.TypiCode.EMAIL;
import static com.typicode.constants.RequestConstants.TypiCode.ID;
import static com.typicode.constants.RequestConstants.TypiCode.NAME;
import static com.typicode.constants.RequestConstants.TypiCode.POST_ID;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import com.typicode.client_wrapper.HttpClient;
import com.typicode.helper.QueryParamWithCode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Grigor_Meliksetyan
 */

@PropertySource("classpath:/application.properties")
@PropertySource("classpath:/environment/${env}/application.properties")
@ContextConfiguration("classpath:coreBeans.xml")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
//@TestInstance(PER_CLASS)
public class BaseTest {

  public static Stream<Arguments> provideDoubles() {
    return Stream.of(
        Arguments.of(11.0, HttpStatus.SC_NOT_FOUND), Arguments.of(7.5, HttpStatus.SC_NOT_FOUND));
  }

  public static Stream<Arguments> provideIntegers() {
    return Stream.of(
        Arguments.of(1, HttpStatus.SC_OK), Arguments.of(10, HttpStatus.SC_OK));
  }

  public static Stream<Arguments> provideStrings() {
    return Stream.of(
        Arguments.of("@SPECIAL_SYMBOL", HttpStatus.SC_NOT_FOUND),
        Arguments.of("SOME_STRING", HttpStatus.SC_NOT_FOUND));
  }

  public static Stream<QueryParamWithCode> provideIdParams() throws CloneNotSupportedException {
    return getQueryNumericParams(ID);
  }

  public static Stream<QueryParamWithCode> providePostIdParams() throws CloneNotSupportedException {
    return getQueryNumericParams(POST_ID);
  }

  public static Stream<QueryParamWithCode> provideNameParams() throws CloneNotSupportedException {
    return getQueryStringParams(NAME);
  }

  public static Stream<QueryParamWithCode> provideEmailParams() throws CloneNotSupportedException {
    return getQueryStringParams(EMAIL);
  }

  public static Stream<QueryParamWithCode> provideBodyParams() throws CloneNotSupportedException {
    return getQueryStringParams(BODY);
  }

  private static Stream<QueryParamWithCode> getQueryNumericParams(String fieldName)
      throws CloneNotSupportedException {
    List<QueryParamWithCode> result = new ArrayList<>();
    QueryParamWithCode base = new QueryParamWithCode(ID);
    QueryParamWithCode case1 = base.clone();
    case1.setValue("SOME_STRING");
    case1.setStatus(HttpStatus.SC_BAD_REQUEST);
    QueryParamWithCode case2 = base.clone();
    case2.setValue(-2);
    case2.setStatus(HttpStatus.SC_BAD_REQUEST);
    QueryParamWithCode case3 = base.clone();
    case3.setValue(null);
    case3.setStatus(HttpStatus.SC_OK);
    QueryParamWithCode case4 = base.clone();
    case4.setValue("^%$#@");
    case4.setStatus(HttpStatus.SC_BAD_REQUEST);
    QueryParamWithCode case5 = base.clone();
    case5.setValue("");
    case5.setStatus(HttpStatus.SC_OK);
    QueryParamWithCode case6 = base.clone();
    case6.setValue("   ");
    case6.setStatus(HttpStatus.SC_OK);
    result.add(case1);
    result.add(case2);
    result.add(case3);
    result.add(case4);
    result.add(case5);
    result.add(case6);
    return result.stream();
  }

  private static Stream<QueryParamWithCode> getQueryStringParams(String fieldName)
      throws CloneNotSupportedException {
    List<QueryParamWithCode> result = new ArrayList<>();
    QueryParamWithCode base = new QueryParamWithCode(fieldName);
    QueryParamWithCode case1 = base.clone();
    case1.setValue("SOME_STRING");
    case1.setStatus(HttpStatus.SC_OK);
    QueryParamWithCode case2 = base.clone();
    case2.setValue(-2);
    case2.setStatus(HttpStatus.SC_OK);
    QueryParamWithCode case3 = base.clone();
    case3.setValue(null);
    case3.setStatus(HttpStatus.SC_OK);
    QueryParamWithCode case4 = base.clone();
    case4.setValue("^%$#@");
    case4.setStatus(HttpStatus.SC_OK);
    QueryParamWithCode case5 = base.clone();
    case5.setValue("");
    case5.setStatus(HttpStatus.SC_OK);
    QueryParamWithCode case6 = base.clone();
    case6.setValue("   ");
    case6.setStatus(HttpStatus.SC_OK);
    result.add(case1);
    result.add(case2);
    result.add(case3);
    result.add(case4);
    result.add(case5);
    result.add(case6);
    return result.stream();
  }

  HttpClient finalizeRequestUri(HttpClient httpClient, String pathParams) {
    return finalizeRequestUri(httpClient, List.of(pathParams));
  }

  HttpClient finalizeRequestUri(HttpClient httpClient, List<String> pathParams) {
    HttpClient client = httpClient.clone();
    client.setHostUrl(String.format(client.getHostUrl(), pathParams.toArray()));
    return client;
  }

}
