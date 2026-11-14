package jakarta.tutorial.decorators;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggedInterceptor {
    @Around("@annotation(jakarta.tutorial.decorators.Logged) || @within(jakarta.tutorial.decorators.Logged)")
    public Object logMethodEntry(ProceedingJoinPoint invocationContext) throws Throwable {
        System.out.println("Entering method: " + invocationContext.getSignature().getName()
                + " in class " + invocationContext.getSignature().getDeclaringTypeName());
        return invocationContext.proceed();
    }
}
