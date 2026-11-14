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
package jakarta.tutorial.encoder;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Managed bean that calls a Coder implementation to perform a transformation
 * on an input string
 */
@Named
@RequestScoped
public class CoderBean {

    private String inputString;
    private String codedString;
    @Max(26)
    @Min(0)
    @NotNull
    private int transVal;
    @Inject
    Coder coder;

    public void encodeString() {
        setCodedString(coder.codeString(inputString, transVal));
    }

    /**
     * Ensures a submitted encode command is handled when running on the
     * Quarkus MyFaces integration. The normal command action runs first; this
     * hook is a no-op in that case and supplies the result only when the
     * action was not delivered by the JSF implementation.
     */
    public void encodeSubmitted() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (codedString == null
                && context.isPostback()
                && context.getExternalContext().getRequestParameterMap()
                        .containsKey("encodeit:encode")) {
            encodeString();
        }
    }

    public void reset() {
        setInputString("");
        setTransVal(0);
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inString) {
        inputString = inString;
    }

    public String getCodedString() {
        return codedString;
    }

    public void setCodedString(String str) {
        codedString = str;
    }

    public int getTransVal() {
        return transVal;
    }

    public void setTransVal(int tval) {
        transVal = tval;
    }
}
