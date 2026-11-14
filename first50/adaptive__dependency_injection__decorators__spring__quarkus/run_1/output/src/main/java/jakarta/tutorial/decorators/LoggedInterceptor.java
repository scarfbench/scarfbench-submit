package jakarta.tutorial.decorators;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Logged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggedInterceptor {
    @AroundInvoke
    public Object logMethodEntry(InvocationContext context) throws Exception {
        System.out.println("Entering method: " + context.getMethod().getName()
                + " in class " + context.getMethod().getDeclaringClass().getName());
        return context.proceed();
    }
}
