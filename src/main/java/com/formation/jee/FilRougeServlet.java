package com.formation.jee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@WebServlet(description = "Main servlet")
public class FilRougeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        OutputStream out = resp.getOutputStream();
        out.write("{\"message\": \"Hello world!\"}".getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
}
