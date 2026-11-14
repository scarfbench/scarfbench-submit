package jakarta.tutorial.decorators;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Named
@RequestScoped
public class CoderBean {
    @Inject
    private Coder coder;
    private String inputString;
    private String codedString;
    @Min(0)
    @Max(26)
    private int transVal;

    public void encodeString() { codedString = coder.codeString(inputString, transVal); }
    public void reset() { inputString = null; codedString = null; transVal = 0; }
    public String getInputString() { return inputString; }
    public void setInputString(String inputString) { this.inputString = inputString; }
    public String getCodedString() { return codedString; }
    public void setCodedString(String codedString) { this.codedString = codedString; }
    public int getTransVal() { return transVal; }
    public void setTransVal(int transVal) { this.transVal = transVal; }
}
