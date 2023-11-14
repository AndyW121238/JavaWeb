<%@ page import="Bean.Message" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: A
  Date: 2023/11/15
  Time: 1:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>消息展示框</title>
</head>
<body>
<div id="body">
    <textarea id="history" readonly>
      <%
          ArrayList<Message> filteredMessages = (ArrayList<Message>) request.getSession().getAttribute("filteredMessages");
          ArrayList<Message> m = (ArrayList<Message>) request.getServletContext().getAttribute("messages");
          System.out.println("所有消息集合:" + m);
          System.out.println("session消息集合:" + filteredMessages);
          System.out.println("session id:" + request.getSession().getId());
          for (Message message : filteredMessages) {
      %>
        <%=
        message.getWholeMessage()
        %>
        <%
            }
        %>
    </textarea>
</div>
</body>
</html>
