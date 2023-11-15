<%@ page import="java.util.ArrayList" %>
<%@ page import="Bean.Message" %>
<%--
  Created by IntelliJ IDEA.
  User: AndyW
  Date: 2023/11/6
  Time: 1:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta http-equiv="refresh" content="5">
    <meta charset="UTF-8">
    <title>聊天室</title>
</head>

<body>
<div id="header">
    <img src="<%=request.getContextPath()%>\logo\logo.png" alt="logo">
    <form action="<%=request.getContextPath()%>/ChatRoomServlet/quit" method="get" id="quit">
        <input type="submit" value="退出聊天室">
    </form>
</div>
<div id="body">
    <textarea id="history" readonly>
      <%
          ArrayList<Message> filteredMessages = (ArrayList<Message>) request.getSession().getAttribute("filteredMessages");
          ArrayList<Message> m = (ArrayList<Message>) request.getServletContext().getAttribute("messages");
          String name = (String) request.getSession().getAttribute("name");
          System.out.println("所有消息集合:" + m);
          System.out.println(name + "的session消息集合:" + filteredMessages);
          System.out.println(name + "的session id:" + request.getSession().getId());
          for (Message message : filteredMessages) {
      %>
        <%=
        message.getWholeMessage()
        %>
        <%
            }
        %>
    </textarea>

    <textarea id="users" readonly>
    <%
        ArrayList<String> users = (ArrayList<String>) request.getServletContext().getAttribute("users");
        System.out.println(users);
        for (String user : users) {
    %>
      <%=
      user
      %>
    <%
        }
    %>
    </textarea>
</div>
<div id="footer">
    <form action="<%=request.getContextPath()%>/ChatRoomServlet/send" method="post">
        <div id="div1">
            <input type="text" name="message" id="message" required>
        </div>
        <div id="div2">
            <div>
                发送对象:
                <select name="to">
                    <option value="所有人">所有人</option>

                    <%
                        for (String user : users) {
                    %>
                    <option value="<%=user%>"><%=user%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
            <input type="submit" id="send" value="发送">
        </div>
    </form>
</div>
</body>
<style>
    body {
        background-color: #0e0e0e;
        margin: 0;
        display: flex;
        flex-direction: column;
        height: 100vh;
    }

    #header {
        background-color: #000;
        display: flex;
        align-items: center;
        margin: 0;
        flex-direction: row;
    }

    #body {
        background-color: #fff;
        height: 85%;
        display: flex;
        flex-direction: row;
    }

    #footer {
        background-color: #e5e8ec;
        width: 100%;
        height: 15%;
    }

    #history {
        border: 0;
        resize: none;
        height: 100%;
        width: 75%;
        padding: 0;
        background-color: #0e0e0e;
        outline: none;
        color: white;
        text-align: left;
    }

    #users {
        border: 0;
        resize: none;
        height: 100%;
        width: 25%;
        padding: 0;
        background-color: #282828;
        outline: none;
        color: white;
        text-align: left;
    }

    #message {
        background-color: #cbd5ee;
        height: 50px;
        width: 100%;
        line-height: 30px;
        font-size: 30px;
        padding-left: 30px;
        border-radius: 50px;
        border: none;
        align-self: center;
    }


    input[type="submit"] {
        background-color: #ff9900;
        color: #000;
        height: 45px;
        border: 0;
        font-size: 25px;
        box-sizing: content-box;
        border-radius: 5px;
        margin: 5px;
    }

    #div1 {
        width: 85%;
        display: flex;
    }

    #div2 {
        width: 15%;
    }

    form {
        display: flex;
        flex-direction: row;
        width: 100%;
    }

    #quit {
        flex-direction: row-reverse;
    }

</style>
</html>
