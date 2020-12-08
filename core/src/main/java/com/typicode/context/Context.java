package com.typicode.context;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Grigor_Meliksetyan
 */
@Component
@PropertySource("classpath:/application.properties")
@PropertySource("classpath:/environment/${env}/application.properties")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Context {

}
