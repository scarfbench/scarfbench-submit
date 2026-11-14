package org.springframework.tutorial.decorators.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.servlet.annotation.WebServlet;
import jakarta.tutorial.decorators.Coder;
import jakarta.tutorial.decorators.CoderBean;
import jakarta.tutorial.decorators.CoderDecorator;
import jakarta.tutorial.decorators.CoderImpl;
import jakarta.tutorial.decorators.CoderServlet;
import jakarta.validation.Validation;
import java.lang.reflect.Field;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class CoderControllerTest {

    @Test
    void getMainPage() {
        WebServlet mapping = CoderServlet.class.getAnnotation(WebServlet.class);
        assertTrue(Arrays.asList(mapping.urlPatterns()).contains(""));
        assertTrue(Arrays.asList(mapping.urlPatterns()).contains("/index.xhtml"));
    }

    @Test
    void shouldRejectInvalidPayload() {
        CoderBean bean = new CoderBean();
        bean.setInputString("aa");
        bean.setTransVal(124);
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            assertTrue(factory.getValidator().validate(bean).stream()
                    .anyMatch(violation -> "transVal".equals(violation.getPropertyPath().toString())));
        }
    }

    @Test
    void encodeString() throws Exception {
        CoderBean bean = coderBean();
        bean.setInputString("aa");
        bean.setTransVal(2);
        bean.encodeString();
        assertEquals("\"aa\" becomes \"cc\", 2 characters in length", bean.getCodedString());
    }

    @Test
    void resetShouldRedirect() throws Exception {
        CoderBean bean = coderBean();
        bean.setInputString("aa");
        bean.setTransVal(2);
        bean.encodeString();
        bean.reset();
        assertNull(bean.getInputString());
        assertNull(bean.getCodedString());
        assertEquals(0, bean.getTransVal());
    }

    private static CoderBean coderBean() throws Exception {
        CoderDecorator decorator = new CoderDecorator() { };
        inject(decorator, "delegate", new CoderImpl());
        CoderBean bean = new CoderBean();
        inject(bean, "coder", decorator);
        return bean;
    }

    private static void inject(Object target, String fieldName, Coder value) throws Exception {
        Field field = target.getClass().getSuperclass() == CoderDecorator.class
                ? CoderDecorator.class.getDeclaredField(fieldName)
                : CoderBean.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
