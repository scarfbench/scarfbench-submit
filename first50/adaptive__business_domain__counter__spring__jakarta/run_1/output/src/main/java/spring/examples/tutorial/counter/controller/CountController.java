package spring.examples.tutorial.counter.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import spring.examples.tutorial.counter.service.CounterService;

@WebServlet(urlPatterns = {"", "/index.html"})
public class CountController extends HttpServlet {

    @Inject
    private CounterService counterService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int hitCount = counterService.getHits();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
        out.println("<title>Counter - A singleton session bean example.</title>");
        out.println("<link rel=\"stylesheet\" href=\"" + request.getContextPath()
                + "/css/default.css\"></head><body>");
        out.println("<h1>This page has been accessed " + hitCount + " time(s).</h1>");
        out.println("<p>Hooray!</p></body></html>");
    }
}
