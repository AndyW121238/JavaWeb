import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "MessageServlet", value = "/MessageServlet")
public class MessageServlet extends HttpServlet {
    private List<Message> messages = new ArrayList<>();
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String messageText = request.getParameter("message");
        Message message = new Message(name, messageText);
        messages.add(message);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>留言板</title></head><body>");
        out.println("<h2>留言板</h2>");
        out.println("<ul>");
        for (Message msg : messages) {
            out.println("<li><strong>" + msg.getName() + ":</strong> " + msg.getMessage() + "</li>");
        }
        out.println("</ul>");
        out.println("<a href=\"/demo/messageBoard.html\">返回</a>");
        out.println("</body></html>");
    }

    private static class Message {
        private String name;
        private String message;

        public Message(String name, String message) {
            this.name = name;
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public String getMessage() {
            return message;
        }
    }
}
