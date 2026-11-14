package org.springframework.tutorial.encoder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.tutorial.encoder.dto.CoderForm;
import org.springframework.tutorial.encoder.service.Coder;

@WebServlet(urlPatterns = {"/", "/encode", "/reset"})
public class CoderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Inject private Coder coderService;
    public CoderController() { }
    public CoderController(Coder coderService) { this.coderService = coderService; }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        CoderForm form = (CoderForm) session.getAttribute("coderForm");
        session.removeAttribute("coderForm");
        render(response, request.getContextPath(), form == null ? new CoderForm() : form, null);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if ("/reset".equals(request.getServletPath())) {
            request.getSession().setAttribute("coderForm", new CoderForm());
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        CoderForm form = new CoderForm();
        form.setInputString(request.getParameter("inputString"));
        int shift;
        try { shift = Integer.parseInt(request.getParameter("transVal")); }
        catch (RuntimeException e) { render(response, request.getContextPath(), form, "The value must be between 0 and 26."); return; }
        form.setTransVal(shift);
        if (shift < 0 || shift > 26) { render(response, request.getContextPath(), form, "The value must be between 0 and 26."); return; }
        form.setCodedString(coderService.codeString(form.getInputString() == null ? "" : form.getInputString(), shift));
        request.getSession().setAttribute("coderForm", form);
        response.sendRedirect(request.getContextPath() + "/");
    }

    private void render(HttpServletResponse response, String context, CoderForm form, String error) throws IOException {
        response.setContentType("text/html;charset=UTF-8"); PrintWriter out = response.getWriter();
        out.print("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>String Encoder</title><link rel=\"stylesheet\" href=\"" + context + "/css/default.css\"></head><body><h2>String Encoder</h2><p>Enter a string and an integer, then click Encode.</p><p>Depending on which alternative is enabled, the coder bean will either display the argument values or return a string that shifts the letters in the original string by the value you specify. The value must be between 0 and 26.</p><form action=\"" + context + "/encode\" method=\"post\"><p><label for=\"inputString\">Enter a string: </label><input type=\"text\" id=\"inputString\" name=\"inputString\" value=\"" + escape(form.getInputString()) + "\"><label for=\"transVal\">Enter the number of letters to shift by: </label><input type=\"text\" id=\"transVal\" name=\"transVal\" value=\"" + form.getTransVal() + "\"></p><p><button type=\"submit\">Encode</button></p><div><p><strong>Result:</strong> <span style=\"color:blue\">" + escape(form.getCodedString()) + "</span></p></div></form><form action=\"" + context + "/reset\" method=\"post\"><button type=\"submit\">Reset</button></form>");
        if (error != null) out.print("<ul><li style=\"color:red\">" + escape(error) + "</li></ul>"); out.print("</body></html>");
    }
    private static String escape(String value) { return value == null ? "" : value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;"); }
}
