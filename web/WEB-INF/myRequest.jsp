<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>My Leave Requests</title>
        <link rel="stylesheet" type="text/css" href="./CSS/style4.css">
        <script>
            function confirmDelete() {
                return confirm("Are you sure you want to delete this leave request?");
            }
        </script>
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
                    <th>Update</th>
                </tr>
                <c:forEach items="${list}" var="request">
                    <tr>
                        <td>${request.employeeid}</td>
                        <td>${request.managerid}</td>
                        <td>${requestScope.department}</td>
                        <td><fmt:formatDate value="${request.startdate}" pattern="dd/MM/yyyy"/></td>
                        <td><fmt:formatDate value="${request.enddate}" pattern="dd/MM/yyyy"/></td>
                        <td>${request.reason}</td>
                        <td>${request.status}</td>
                        <td><fmt:formatDate value="${request.requestdate}" pattern="dd/MM/yyyy"/></td>
                        <td>
                            <div class="button-container">
                                <c:if test="${request.status == 'Pending'}">
                                    <form action="update">
                                        <input type="hidden" name="requestid" value="${request.requestid}">
                                        <button type="submit">Update</button>
                                    </form>
                                        <form action="delete" method="post" onsubmit="return confirmDelete();">
                                        <input type="hidden" name="requestid" value="${request.requestid}">
                                        <button type="submit">Delete</button>
                                    </form>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <footer>
            <p>© 2025 My Company. All Rights Reserved.</p>
        </footer>
    </body>
</html>