<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="model.Employee" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Leave Request</title>
        <link rel="stylesheet" href="./CSS/style3.css">
        <script>
            function validateForm() {
                var fromDate = document.getElementById("fromDate").value;
                var toDate = document.getElementById("toDate").value;

                if (fromDate === "" || toDate === "") {
                    alert("Please fill in both Start Date and End Date fields");
                    return false;
                }

                // Phần tùy chọn: kiểm tra xem toDate có nhỏ hơn fromDate không
                if (new Date(toDate) < new Date(fromDate)) {
                    alert("End Date must be after Start Date");
                    return false;
                }

                return true;
            }
        </script>
    </head>
    <body>

        <c:if test="${empty sessionScope.user}">
            <c:redirect url="login.jsp"/>
        </c:if>

        <c:if test="${empty requestScope.employee}">
            <c:redirect url="login.jsp"/>
        </c:if>

        <header>
            <h1>Leave Request</h1>
        </header>

        <div class="container">

            <c:set var="user" value="${sessionScope.user}" />
            <c:set var="employee" value="${requestScope.employee}" />

            <form action="create" method="post" onsubmit="return validateForm()">
                <label>Employee ID:</label>
                <input type="text" name="employeeId" id="employeeId" readonly value="${employee.employeeid}">

                <label>Name:</label>
                <input type="text" name="name" id="name" readonly value="${employee.fullname}">

                <label>Email:</label>
                <input type="email" name="email" id="email" readonly value="${user.email}">

                <label>Department:</label>
                <input type="text" name="department" id="department" readonly value="${employee.department}">

                <label>From (Start Date):</label>
                <input type="date" name="fromDate" id="fromDate">

                <label>To (End Date):</label>
                <input type="date" name="toDate" id="toDate">

                <label>Reason:</label>
                <textarea name="reason" id="reason"></textarea>

                <button type="submit">Send</button>
            </form>
        </div>
        <footer>
            <p>&copy; 2025 My Company. All Rights Reserved.</p>
        </footer>

    </body>
</html>