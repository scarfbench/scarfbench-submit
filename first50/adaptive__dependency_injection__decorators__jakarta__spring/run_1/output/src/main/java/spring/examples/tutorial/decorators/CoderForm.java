package org.springframework.tutorial.decorators.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/** Form backing object for the encoder page. */
public class CoderForm {

    private String inputString = "";

    @Min(value = 0, message = "The value must be between 0 and 26.")
    @Max(value = 26, message = "The value must be between 0 and 26.")
    private int transVal;

    private String codedString = "";

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public int getTransVal() {
        return transVal;
    }

    public void setTransVal(int transVal) {
        this.transVal = transVal;
    }

    public String getCodedString() {
        return codedString;
    }

    public void setCodedString(String codedString) {
        this.codedString = codedString;
    }
}
