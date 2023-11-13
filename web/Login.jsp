<%--
  Created by IntelliJ IDEA.
  User: AndyW
  Date: 2023/11/6
  Time: 1:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
</head>
<body>
<div id="header">
    <img src="logo\logo.png" alt="logo">
</div>
<div id="body">
    <div id="div1">
        <h2>请输入昵称以进入聊天室</h2>
    </div>
    <div id="div2">
        <form action="<%=request.getContextPath()%>/ChatRoomServlet/enter" method="post">
            <input type="text" name="name" id="name" required>
            <br>
            <input type="submit" id="enter" value="进入聊天室">
        </form>
    </div>

</div>
</body>
<style>
    body {
        background-color: #0e0e0e;
        margin: 0;
        display: flex;
        flex-direction: column;
    }
    #header {
        background-color: #000;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    #body {
        display: flex;
        flex-direction: column;
    }
    input[type="submit"] {
        background-color: #ff9900;
        color: #000;
        height: 45px;
        border: 0;
        font-size: 25px;
        box-sizing: content-box;
        border-radius: 5px;
    }
    h2 {
        color: white;
    }
    #div1{
        display: flex;
        justify-content: center;
        align-items: center;
    }
    #div2{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
    form{
        display: grid;
        justify-content: center;
        align-items: center;
    }
    #name{
        width: 500px;
        height: 50px;
        line-height: 30px;
        font-size: 30px;
        padding-left: 30px;
        border-radius: 50px;
        background-color: gainsboro;
        border: none;
    }
    #name:focus{
        outline: none;
        background-color: lightblue;
    }

</style>
</html>
