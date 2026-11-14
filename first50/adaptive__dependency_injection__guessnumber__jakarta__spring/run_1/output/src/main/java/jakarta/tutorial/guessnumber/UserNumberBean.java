package jakarta.tutorial.guessnumber;
import java.io.Serializable;

public class UserNumberBean implements Serializable {
 private static final long serialVersionUID=1L; private final Generator generator; private int number,minimum,maximum,remainingGuesses; private Integer userNumber; private String message;
 public UserNumberBean(Generator generator){this.generator=generator;reset();}
 public int getNumber(){return number;} public Integer getUserNumber(){return userNumber;} public void setUserNumber(Integer v){userNumber=v;} public int getMinimum(){return minimum;} public void setMinimum(int v){minimum=v;} public int getMaximum(){return maximum;} public void setMaximum(int v){maximum=v;} public int getRemainingGuesses(){return remainingGuesses;} public String getMessage(){return message;}
 public String check(){if(userNumber==null||userNumber<minimum||userNumber>maximum){message="Invalid guess";return null;} if(userNumber>number){maximum=userNumber-1;message="Lower!";}else if(userNumber<number){minimum=userNumber+1;message="Higher!";}else message="Correct!";remainingGuesses--;return null;}
 public void reset(){minimum=0;userNumber=0;remainingGuesses=10;maximum=generator.getMaxNumber();number=generator.next();message=null;}
}
