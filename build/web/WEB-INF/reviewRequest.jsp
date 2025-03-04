<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Review Leave Request</title>
        <link rel="stylesheet" type="text/css" href="./CSS/style5.css">
    </head>
    <body>

        <header>
            <h1>Review Leave Request</h1>
        </header>

        <c:set var="list" value="${requestScope.list}"/>

        <div class="container">
            <table>
                <thead>
                    <tr>
                        <th>Request ID</th>
                        <th>Employee ID</th>
                        <th>Manager ID</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Reason</th>
                        <th>Request Date</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list}" var="request">
                        <tr>
                            <td>${request.requestid}</td>
                            <td>${request.employeeid}</td>
                            <td>${request.managerid}</td>
                            <td><fmt:formatDate value="${request.startdate}" pattern="dd/MM/yyyy"/></td>
                            <td><fmt:formatDate value="${request.enddate}" pattern="dd/MM/yyyy"/></td>
                            <td>${request.reason}</td>
                            <td><fmt:formatDate value="${request.requestdate}" pattern="dd/MM/yyyy"/></td>
                            <td>
                                <div class="button-container">
                                    <form action="reviewrequest" method="post">
                                        <input type="hidden" name="requestid" value="${request.requestid}">
                                        <button type="submit" name="action" value="approve" class="btn btn-approve">Approve</button>
                                        <button type="submit" name="action" value="reject" class="btn btn-reject">Reject</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <footer>
            <p>© 2025 My Company. All Rights Reserved.</p>
        </footer>

    </body>
</html>