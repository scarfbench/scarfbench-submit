package org.springframework.tutorial.encoder.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import org.springframework.tutorial.encoder.dto.CoderForm;
import org.springframework.tutorial.encoder.service.CoderImpl;

public class CoderControllerTest {
    private CoderController controller; private HttpServletRequest request; private HttpServletResponse response; private HttpSession session; private StringWriter body;
    @BeforeEach void setUp() throws Exception { controller = new CoderController(new CoderImpl()); request = mock(HttpServletRequest.class); response = mock(HttpServletResponse.class); session = mock(HttpSession.class); body = new StringWriter(); when(request.getContextPath()).thenReturn(""); when(request.getSession()).thenReturn(session); when(response.getWriter()).thenReturn(new PrintWriter(body)); }
    @Test void getMainPage() throws Exception { controller.doGet(request, response); assertTrue(body.toString().contains("String Encoder")); assertTrue(body.toString().contains("name=\"inputString\"")); }
    @Test void shouldRejectInvalidPayload() throws Exception { encodeRequest("aa", "124"); controller.doPost(request, response); assertTrue(body.toString().contains("between 0 and 26")); }
    @Test void encodeString() throws Exception { encodeRequest("aa", "2"); controller.doPost(request, response); var capture = org.mockito.ArgumentCaptor.forClass(CoderForm.class); verify(session).setAttribute(eq("coderForm"), capture.capture()); assertEquals("cc", capture.getValue().getCodedString()); verify(response).sendRedirect("/"); }
    @Test void resetShouldRedirect() throws Exception { when(request.getServletPath()).thenReturn("/reset"); controller.doPost(request, response); var capture = org.mockito.ArgumentCaptor.forClass(CoderForm.class); verify(session).setAttribute(eq("coderForm"), capture.capture()); assertNotNull(capture.getValue()); verify(response).sendRedirect("/"); }
    private void encodeRequest(String input, String shift) { when(request.getServletPath()).thenReturn("/encode"); when(request.getParameter("inputString")).thenReturn(input); when(request.getParameter("transVal")).thenReturn(shift); }
}
