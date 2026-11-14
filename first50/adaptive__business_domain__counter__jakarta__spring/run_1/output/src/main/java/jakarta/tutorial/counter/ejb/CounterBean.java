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
package jakarta.tutorial.counter.ejb;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

/**
 *
 * @author ian
 * CounterBean is a simple singleton session bean that records the number
 * of hits to a web page.
 */
@Service
public class CounterBean {
    private final AtomicInteger hits = new AtomicInteger(1);

    // Increment and return the number of hits
    public int getHits() {
        return hits.getAndIncrement();
    }
}
