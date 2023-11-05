import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ChatRoomServlet", value = {"/ChatRoomServlet/enter","/ChatRoomServlet/send"})
public class ChatRoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();

        // 120秒无活动会退出
        request.getSession().setMaxInactiveInterval(120);

        if("/ChatRoomServlet/enter".equals(servletPath)){
            doEnter(request,response);
        } else if ("/ChatRoomServlet/send".equals(servletPath)){
            doSend(request,response);
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
            servletContext.setAttribute("messages", new ArrayList<String>());
        }
        if (servletContext.getAttribute("users") == null) {
            servletContext.setAttribute("users", new ArrayList<String>());
        }
        if(session.getAttribute("me") == null){
            session.setAttribute("me", name);
        }

        ArrayList<String> users = (ArrayList<String>)servletContext.getAttribute("users");
        ArrayList<String> messages = (ArrayList<String>)servletContext.getAttribute("messages");

        // 把进入聊天室的人添加到集合中
        users.add(name);
        messages.add(name + "进入聊天室");

        // 重定向到聊天室页面
       response.sendRedirect("/cr/ChatPage.jsp");
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

        System.out.println(session.getId());
        String me = (String) session.getAttribute("me");

        // 获取消息集合
        ArrayList<String> messages = (ArrayList<String>) servletContext.getAttribute("messages");

        messages.add(me + "对" + to + "说:" + message);
        session.setAttribute("messages",messages);
        System.out.println(messages);


       response.sendRedirect("/cr/ChatPage.jsp");
    }
}
