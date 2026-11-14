package org.springframework.tutorial.encoder.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CoderForm {
    private String inputString;
    private String codedString;

    @NotNull
    @Min(0)
    @Max(26)
    private Integer transVal = 0;

    public String getInputString() { return inputString; }
    public void setInputString(String inputString) { this.inputString = inputString; }
    public String getCodedString() { return codedString; }
    public void setCodedString(String codedString) { this.codedString = codedString; }
    public Integer getTransVal() { return transVal; }
    public void setTransVal(Integer transVal) { this.transVal = transVal; }
}
