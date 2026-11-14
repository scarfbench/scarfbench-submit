package jakarta.tutorial.guessnumber;
import org.springframework.stereotype.Service;
@Service public class Generator {
 private final java.util.Random random=new java.util.Random(); private final int maxNumber=100;
 public synchronized int next(){return random.nextInt(maxNumber+1);} public int getMaxNumber(){return maxNumber;}
}
