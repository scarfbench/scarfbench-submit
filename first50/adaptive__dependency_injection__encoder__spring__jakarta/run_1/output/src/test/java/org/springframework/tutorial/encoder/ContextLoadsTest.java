package org.springframework.tutorial.encoder;

import org.junit.jupiter.api.Test;
import org.springframework.tutorial.encoder.service.CoderImpl;

public class ContextLoadsTest {

    @Test
    void contextLoads() { new CoderImpl(); }

}
