package jakarta.tutorial.decorators;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Logged
@Interceptor
public class LoggedInterceptor {
    @AroundInvoke
    public Object logMethodEntry(InvocationContext context) throws Exception {
        System.out.println("Entering method: " + context.getMethod().getName()
                + " in class " + context.getMethod().getDeclaringClass().getName());
        return context.proceed();
    }
}
