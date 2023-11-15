import Bean.Message;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ChatRoomServlet", value = {"/ChatRoomServlet/enter", "/ChatRoomServlet/send", "/ChatRoomServlet/quit"})
public class ChatRoomServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();


        if ("/ChatRoomServlet/enter".equals(servletPath)) {
            doEnter(request, response);
        } else if ("/ChatRoomServlet/send".equals(servletPath)) {
            doSend(request, response);
        } else if ("/ChatRoomServlet/quit".equals(servletPath)) {
            doQuit(request, response);
        }
    }


    private void doQuit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取session对象
        HttpSession session = request.getSession(false);

        //  获取用户列表
        ArrayList<String> users = (ArrayList<String>) request.getServletContext().getAttribute("users");

        // 获取context对象
        ServletContext servletContext = request.getServletContext();

        if (session != null) {
            String name = (String) session.getAttribute("name");
            Message m = new Message("系统", "所有人", name + "退出聊天室");
            ArrayList<Message> messages = (ArrayList<Message>) servletContext.getAttribute("messages");
            // 把有人退出的消息添加到消息集合
            messages.add(m);
            // 手动销毁session对象
            session.invalidate();
            // 把当前用户从用户集合中删除
            users.remove(name);
        }
        // 重定向到登陆页面
        response.sendRedirect(request.getContextPath());
    }

    public void doEnter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doEnter");
        // 获取进入聊天室的人的昵称
        String name = request.getParameter("name");

        // 获取session
        HttpSession session = request.getSession(false);

        // 获取context
        ServletContext servletContext = request.getServletContext();

        // 如果各个域中没有集合的话就创建集合
        if (servletContext.getAttribute("messages") == null) {
            servletContext.setAttribute("messages", new ArrayList<Message>());
        }
        if (servletContext.getAttribute("users") == null) {
            servletContext.setAttribute("users", new ArrayList<Message>());
        }
        if (session.getAttribute("name") == null) {
            session.setAttribute("name", name);
        }
        if (session.getAttribute("filteredMessages") == null) {
            session.setAttribute("filteredMessages", new ArrayList<Message>());
        }

        // 获取集合
        ArrayList<Message> filteredMessages = (ArrayList<Message>) session.getAttribute("filteredMessages");
        ArrayList<Message> messages = (ArrayList<Message>) servletContext.getAttribute("messages");


        if (name != null) {
            ArrayList<String> users = (ArrayList<String>) servletContext.getAttribute("users");

            // 把进入聊天室的人添加到集合中
            users.add(name);

            String from = "系统";
            String to = "所有人";
            String message = name + "进入聊天室";

            // 创建消息对象
            Message m = new Message(from, to, message);

            // 把消息对象添加到消息集合中
            messages.add(m);
        }

        // 遍历消息集合,根据信息的发出者和接收者过滤信息,并添加到session中
        filteredMessages.clear();
        for (int i = 0; i < messages.size(); i++) {
            String f = messages.get(i).getFrom();
            String t = messages.get(i).getTo();
            System.out.print("t:");
            for (int i1 = 0; i1 < t.length(); i1++) {
                System.out.print(t.charAt(i1));
            }
            System.out.println();
            System.out.println("name:");
            for (int i1 = 0; i1 < t.length(); i1++) {
                System.out.print(t.charAt(i1));
            }
            System.out.println("t.equal(name):" + t.equals(name));
            if (f.equals(name) || t.equals(name) || t.equals("所有人")) {
                filteredMessages.add(messages.get(i));
            }
        }

        // 转发到聊天室页面
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ChatPage.jsp");
        requestDispatcher.forward(request, response);

    }

    public void doSend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doSend");
        // 获取用户发送的信息
        String message = request.getParameter("message");

        // 获取接受的人
        String to = request.getParameter("to");
        // 获取context
        ServletContext servletContext = request.getServletContext();

        // 获取session
        HttpSession session = request.getSession(false);

        // 获取消息集合
        ArrayList<Message> messages = (ArrayList<Message>) servletContext.getAttribute("messages");

        // 获取当前session的使用者
        String name = (String) session.getAttribute("name");

        if (to != null && message != null) {

            String from = name;
            String wholeMessage = name + "对" + to + "说:" + message;
            Message m = new Message(from, to, wholeMessage);
            messages.add(m);
            System.out.println("消息对象为:" + m + " from:" + m.getFrom() + "  to:" + m.getTo());
        }
        // 获取过滤后的消息集合
        ArrayList<Message> filteredMessages = (ArrayList<Message>) session.getAttribute("filteredMessages");

        // 清空消息集合以便重新赋值
        filteredMessages.clear();

        // 遍历消息集合,根据信息的发出者和接收者过滤信息,并添加到session中
        for (int i = 0; i < messages.size(); i++) {
            String f = messages.get(i).getFrom();
            String t = messages.get(i).getTo();
            System.out.println(t);
            if (f.equals(name) || t.equals(name) || t.equals("所有人")) {
                filteredMessages.add(messages.get(i));
            }
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ChatPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
