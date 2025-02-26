<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Leave Management</title>
        <link rel="stylesheet" href="./CSS/style2.css">
    </head>
    <body>

        <c:if test="${empty sessionScope.user}">
            <c:redirect url="login.jsp"/>
        </c:if>

        <div class="sidebar">
            <h2>Menu</h2>
            <ul>
                <li><a href="#">Home</a></li>
                <li><a href="#">My Task</a></li>
                <li><a href="#">Report</a></li>
                <li><a href="#">About Us</a></li>
                <li><a href="login.jsp">Logout</a></li>
            </ul>
        </div>

        <!-- Header -->
        <header>
            <h1>Leave Management System</h1> 
        </header>

        <c:set var="user" value="${sessionScope.user}" />

        <div class="container">
            <h2>📑 Manage Leave Requests</h2>
            <div class="menu">

                <c:if test="${not empty user and user.roleid >1}">
                    <a href="myrequest" class="btn">📄 View My Request</a>
                </c:if>

                <c:if test="${not empty user and user.roleid > 1}">
                    <a href="create" class="btn">📝 Create Request</a>
                </c:if>

                <c:if test="${not empty user and user.roleid < 3}">
                    <a href="reviewrequest" class="btn">✅ Review Request</a>
                </c:if>

                <c:if test="${not empty user and user.roleid == 1}">
                    <a href="agenda.jsp" class="btn">📊 Agenda</a>
                </c:if>
            </div>
        </div>
        <footer>
            <p>&copy; 2025 My Company. All Rights Reserved.</p>
        </footer>
        <c:if test="${not empty sessionScope.message}">
            <script>
                alert("${sessionScope.message}");
            </script>
            <c:remove var="message" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.notification}">
            <script>
                alert("${sessionScope.notification}");
            </script>
            <c:remove var="notification" scope="session"/>
        </c:if>
    </body>
</html>
