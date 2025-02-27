<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*, java.sql.*, java.text.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Approved Leave Requests</title>
        <link rel="stylesheet" href="./CSS/style6.css">
        <style>
            .leave-card { border: 1px solid #ccc; margin: 10px 0; padding: 10px; }
            .calendar table { width: 100%; border-collapse: collapse; }
            .calendar th, .calendar td { border: 1px solid #ddd; text-align: center; padding: 5px; }
            .work-day { background-color: #90ee90; } /* Green for work */
            .leave-day { background-color: #ffff99; } /* Yellow for leave */
            .off-day { background-color: #ffcccb; } /* Red for off */
        </style>
    </head>
    <body>
        <c:if test="${empty sessionScope.user}">
            <jsp:forward page="/WEB-INF/login.jsp"/>
        </c:if>

        <!-- Header -->
        <header>
            <h1>Approved Leave Requests</h1>
            <nav>
                <a href="#">Dashboard</a> | 
                <a href="#">Schedules</a> | 
                <a href="#">Leave Requests</a> | 
                <a href="#">Profile</a> | 
                <a href="#">Logout</a>
            </nav>
            <div class="user-info">Welcome, <c:out value="${sessionScope.user != null ? sessionScope.user.username : 'Guest'}"/></div>
        </header>

        <!-- Main Content -->
        <main>
            <!-- Leave Requests List -->
            <section class="leave-list">
                <h2>Approved Leave Requests</h2>
                <c:choose>
                    <c:when test="${not empty requestScope.leaveRequests}">
                        <!-- Tính toán ngày bắt đầu và kết thúc của tuần hiện tại -->
                        <%
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(new java.util.Date()); // Lấy ngày hiện tại của hệ thống
                            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Đặt về thứ Hai của tuần
                            java.sql.Date weekStart = new java.sql.Date(cal.getTimeInMillis());
                            cal.add(Calendar.DATE, 6); // Cộng 6 ngày để đến Chủ nhật
                            java.sql.Date weekEnd = new java.sql.Date(cal.getTimeInMillis());
                            request.setAttribute("weekStart", weekStart);
                            request.setAttribute("weekEnd", weekEnd);
                        %>
                        <c:forEach var="leave" items="${requestScope.leaveRequests}">
                            <div class="leave-card">
                                <!-- Leave Information -->
                                <div class="leave-info">
                                    <h3>Request ID: <c:out value="${leave.requestid}"/></h3>
                                    <p><strong>Employee ID:</strong> <c:out value="${leave.employeeid}"/></p>
                                    <p><strong>Manager ID:</strong> <c:out value="${leave.managerid}"/></p>
                                    <p><strong>Start Date:</strong> 
                                        <fmt:formatDate value="${leave.startdate}" pattern="dd/MM/yyyy"/>
                                    </p>
                                    <p><strong>End Date:</strong> 
                                        <fmt:formatDate value="${leave.enddate}" pattern="dd/MM/yyyy"/>
                                    </p>
                                    <p><strong>Reason:</strong> <c:out value="${leave.reason}"/></p>
                                    <p><strong>Status:</strong> <c:out value="${leave.status}"/></p>
                                    <p><strong>Request Date:</strong> 
                                        <fmt:formatDate value="${leave.requestdate}" pattern="dd/MM/yyyy"/>
                                    </p>
                                </div>

                                <!-- Weekly Schedule (Dynamic based on system date) -->
                                <div class="calendar">
                                    <h4>Weekly Schedule (<fmt:formatDate value="${weekStart}" pattern="dd/MM"/> - <fmt:formatDate value="${weekEnd}" pattern="dd/MM/yyyy"/>)</h4>
                                    <table>
                                        <thead>
                                            <tr>
                                                <c:forEach var="i" begin="0" end="6">
                                                    <!-- Chuyển đổi i thành long trước khi tính toán -->
                                                    <c:set var="currentDate" value="<%= new java.sql.Date(((java.sql.Date)request.getAttribute(\"weekStart\")).getTime() + ((long)((Integer)pageContext.getAttribute(\"i\")).intValue()) * 24 * 60 * 60 * 1000) %>"/>
                                                    <th><fmt:formatDate value="${currentDate}" pattern="EEE dd/MM"/></th>
                                                </c:forEach>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <c:forEach var="i" begin="0" end="6">
                                                    <!-- Chuyển đổi i thành long trước khi tính toán -->
                                                    <c:set var="currentDate" value="<%= new java.sql.Date(((java.sql.Date)request.getAttribute(\"weekStart\")).getTime() + ((long)((Integer)pageContext.getAttribute(\"i\")).intValue()) * 24 * 60 * 60 * 1000) %>"/>
                                                    <c:choose>
                                                        <c:when test="${leave.startdate <= currentDate && leave.enddate >= currentDate}">
                                                            <td class="leave-day">Leave</td>
                                                        </c:when>
                                                        <c:when test="${i == 5 || i == 6}">
                                                            <td class="off-day">Off</td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td class="work-day">Work</td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>No approved leave requests found.</p>
                    </c:otherwise>
                </c:choose>
            </section>
        </main>

        <!-- Footer -->
        <footer>
            <p>© 2025 My Company. All Rights Reserved.</p>
        </footer>
    </body>
</html>