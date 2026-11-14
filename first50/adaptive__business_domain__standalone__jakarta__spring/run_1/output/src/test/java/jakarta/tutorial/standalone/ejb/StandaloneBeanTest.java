/*
 * Copyright (c), Eclipse Foundation, Inc. and its licensors.
 *
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0, which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package jakarta.tutorial.standalone.ejb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import spring.examples.tutorial.standalone.StandaloneApplication;
import spring.examples.tutorial.standalone.service.StandaloneService;

@SpringBootTest(classes = StandaloneApplication.class)
class StandaloneBeanTest {
    private static final Logger logger = Logger.getLogger("standalone.ejb");

    @Autowired
    private StandaloneService standaloneService;

    /** Test of returnMessage method, of class StandaloneBean. */
    @Test
    void testReturnMessage() {
        logger.info("Testing StandaloneService.returnMessage()");
        String expResult = "Greetings!";
        String result = standaloneService.returnMessage();
        assertEquals(expResult, result);
    }
}
