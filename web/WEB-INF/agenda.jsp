<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*, java.sql.*, java.text.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Employee Agenda System</title>
        <link rel="stylesheet" href="./CSS/style6.css">
    </head>
    <body>
        <c:if test="${empty sessionScope.user}">
            <jsp:forward page="/WEB-INF/login.jsp"/>
        </c:if>
        <c:set var="weekStart" value="${requestScope.weekStart}"/>
        <c:set var="weekEnd" value="${requestScope.weekEnd}"/>
        <c:set var="list" value="${requestScope.list}"/>

        <!-- Header -->
        <header>
            <h1>Employee Agenda System</h1>
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
                <c:choose>
                    <c:when test="${not empty list}">
                        <!-- Tính toán ngày bắt đầu và kết thúc của tuần hiện tại -->
                        <c:forEach var="leave" items="${list}">
                            <div class="leave-card">
                                <!-- Leave Information -->
                                <div class="leave-info">
                                    <p><strong>Employee ID:</strong> <c:out value="${leave.employeeid}"/></p>
                                    <p><strong>Full Name:</strong> <c:out value="${leave.fullname}"/></p>
                                    <p><strong>Department:</strong> <c:out value="${leave.department}"/></p>
                                    <p><strong>Manager ID:</strong> <c:out value="${leave.managerid}"/></p>
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