import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import m.Message;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ChatRoomServlet", value = {"/ChatRoomServlet/enter", "/ChatRoomServlet/send"})
public class ChatRoomServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();

        // 120秒无活动会退出
        request.getSession().setMaxInactiveInterval(12000);

        if ("/ChatRoomServlet/enter".equals(servletPath)) {
            doEnter(request, response);
        } else if ("/ChatRoomServlet/send".equals(servletPath)) {
            doSend(request, response);
        }
    }

    public void doEnter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取进入聊天室的人的昵称
        String name = request.getParameter("name");

        // 获取session
        HttpSession session = request.getSession(false);
        // 获取context
        ServletContext servletContext = request.getServletContext();

        if (servletContext.getAttribute("messages") == null) {
            servletContext.setAttribute("messages", new ArrayList<Message>());
        }
        if (servletContext.getAttribute("users") == null) {
            servletContext.setAttribute("users", new ArrayList<Message>());
        }
        if (session.getAttribute("me") == null) {
            session.setAttribute("me", name);
        }
        if (session.getAttribute("filteredMessages") == null) {
            session.setAttribute("filteredMessages", new ArrayList<Message>());
        }

        ArrayList<String> users = (ArrayList<String>) servletContext.getAttribute("users");
        ArrayList<Message> messages = (ArrayList<Message>) servletContext.getAttribute("messages");
        ArrayList<Message> filteredMessages = (ArrayList<Message>) session.getAttribute("filteredMessages");

        // 把进入聊天室的人添加到集合中
        users.add(name);

        String from = "系统";
        String to = "所有人";
        String message = name + "进入聊天室";

        Message m = new Message(from, to, message);

        messages.add(m);
        for (int i = 0; i < messages.size(); i++) {
            String f = messages.get(i).getFrom();
            String t = messages.get(i).getTo();
            if (f.equals(name) || t.equals(name) || t.equals("所有人")) {
                filteredMessages.add(messages.get(i));
            }
        }

        // 转发到聊天室页面
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ChatPage.jsp");
        requestDispatcher.forward(request, response);

    }

    public void doSend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户发送的信息
        String message = request.getParameter("message");
        // 获取接受的人
        String to = request.getParameter("to");

        // 获取session
        HttpSession session = request.getSession(false);

        // 获取context
        ServletContext servletContext = request.getServletContext();

        String me = (String) session.getAttribute("me");

        // 获取消息集合
        ArrayList<Message> messages = (ArrayList<Message>) servletContext.getAttribute("messages");

        String from = me;
        String wholeMessage = me + "对" + to + "说:" + message;
        Message m = new Message(from, to, wholeMessage);
        messages.add(m);
        System.out.println("消息对象为:" + m + " from:" + m.getFrom() + "  to:" + m.getTo());

        ArrayList<Message> filteredMessages = (ArrayList<Message>) session.getAttribute("filteredMessages");
        filteredMessages.add(m);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ChatPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
