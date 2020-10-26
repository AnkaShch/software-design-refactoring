package ru.shchetsova.sd.refactoring;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Utils {
    public static void printHTML(HttpServletResponse response, String request, String result) throws IOException {
        response.getWriter().println("<html><body>");
        response.getWriter().println(request);
        response.getWriter().println(result);
        response.getWriter().println("</body></html>");
    }
}
