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
    Coder coder;
    private String inputString;
    @Min(0) @Max(26)
    private int transVal;
    private String codedString;

    public void encodeString() { codedString = coder.codeString(inputString, transVal); }
    public void encode() { encodeString(); }
    public void reset() { inputString = null; transVal = 0; codedString = null; }
    public String getInputString() { return inputString; }
    public void setInputString(String inputString) { this.inputString = inputString; }
    public int getTransVal() { return transVal; }
    public void setTransVal(int transVal) { this.transVal = transVal; }
    public String getCodedString() { return codedString; }
}
