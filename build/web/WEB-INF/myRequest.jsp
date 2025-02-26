<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>My Leave Requests</title>
        <link rel="stylesheet" type="text/css" href="./CSS/style4.css">
    </head>
    <body>
        <c:set var="list" value="${requestScope.list}"/>
        
        <header>
            <h1>My Leave Requests</h1>
        </header>

        <div class="table-container">
            <table border="1">
                <tr>
                    <th>Employee ID</th>
                    <th>Manager ID</th>
                    <th>Department</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Reason</th>
                    <th>Status</th>
                    <th>Request Date</th>
                </tr>
                <c:forEach items="${list}" var="request">
                    <tr>
                        <td>${request.employeeid}</td>
                        <td>${request.managerid}</td>
                        <td>${requestScope.department}</td>
                        <td>${request.startdate}</td>
                        <td>${request.enddate}</td>
                        <td>${request.reason}</td>
                        <td>${request.status}</td>
                        <td>${request.requestdate}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <footer>
            <p>&copy; 2025 My Company. All Rights Reserved.</p>
        </footer>
    </body>
</html>
