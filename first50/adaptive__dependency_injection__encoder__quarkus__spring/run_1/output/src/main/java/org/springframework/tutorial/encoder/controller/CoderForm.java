package org.springframework.tutorial.encoder.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CoderForm {
    private String inputString = "";
    @NotNull @Min(0) @Max(26)
    private Integer transVal = 0;
    private String codedString;

    public String getInputString() { return inputString; }
    public void setInputString(String inputString) { this.inputString = inputString; }
    public Integer getTransVal() { return transVal; }
    public void setTransVal(Integer transVal) { this.transVal = transVal; }
    public String getCodedString() { return codedString; }
    public void setCodedString(String codedString) { this.codedString = codedString; }
}
