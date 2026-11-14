package org.springframework.tutorial.decorators;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.decorator.Decorator;
import jakarta.enterprise.context.RequestScoped;
import jakarta.interceptor.Interceptor;
import jakarta.tutorial.decorators.CoderBean;
import jakarta.tutorial.decorators.CoderDecorator;
import jakarta.tutorial.decorators.LoggedInterceptor;
import org.junit.jupiter.api.Test;

public class ContextLoadsTest {

    @Test
    void contextLoads() {
        assertNotNull(CoderBean.class.getAnnotation(RequestScoped.class));
        assertNotNull(CoderDecorator.class.getAnnotation(Decorator.class));
        assertNotNull(LoggedInterceptor.class.getAnnotation(Interceptor.class));
    }

}
