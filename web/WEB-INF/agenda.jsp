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
        <div class="user-info">Welcome, <c:out value="${sessionScope.user != null ? sessionScope.user.username : 'Guest'}"/></div>
        <!-- Weekly Schedule Header -->
        <div class="weekly-schedule">
            <h4>Weekly Schedule (<fmt:formatDate value="${weekStart}" pattern="dd/MM"/> - <fmt:formatDate value="${weekEnd}" pattern="dd/MM/yyyy"/>)</h4>
        </div>
    </header>

    <!-- Main Content -->
    <main>
        <section class="employee-list">
            <c:choose>
                <c:when test="${not empty list}">
                    <c:forEach var="leave" items="${list}">
                        <div class="employee-card">
                            <!-- Employee Information -->
                            <div class="employee-info">
                                <h3>Employee Details</h3>
                                <p><strong>ID:</strong> <c:out value="${leave.employeeid}"/></p>
                                <p><strong>Full Name:</strong> <c:out value="${leave.fullname}"/></p>
                                <p><strong>Department:</strong> <c:out value="${leave.department}"/></p>
                                <p><strong>Manager ID:</strong> <c:out value="${leave.managerid}"/></p>
                            </div>

                            <!-- Employee Weekly Status -->
                            <div class="calendar">
                                <table>
                                    <thead>
                                        <tr>
                                            <c:forEach var="i" begin="0" end="6">
                                                <c:set var="currentDate" value="<%= new java.sql.Date(((java.sql.Date)request.getAttribute(\"weekStart\")).getTime() + ((long)((Integer)pageContext.getAttribute(\"i\")).intValue()) * 24 * 60 * 60 * 1000) %>"/>
                                                <th><fmt:formatDate value="${currentDate}" pattern="EEE dd/MM"/></th>
                                            </c:forEach>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <c:set var="workDays" value="0"/>
                                            <c:set var="leaveDays" value="0"/>
                                            <c:forEach var="i" begin="0" end="6">
                                                <c:set var="currentDate" value="<%= new java.sql.Date(((java.sql.Date)request.getAttribute(\"weekStart\")).getTime() + ((long)((Integer)pageContext.getAttribute(\"i\")).intValue()) * 24 * 60 * 60 * 1000) %>"/>
                                                <c:choose>
                                                    <c:when test="${i == 5 || i == 6}">
                                                        <td class="off-day">Off</td>
                                                    </c:when>
                                                    <c:when test="${leave.startdate != null && leave.enddate != null && leave.startdate <= currentDate && leave.enddate >= currentDate}">
                                                        <td class="leave-day">Leave</td>
                                                        <c:set var="leaveDays" value="${leaveDays + 1}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="work-day">Work</td>
                                                        <c:set var="workDays" value="${workDays + 1}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="summary">
                                    <p><strong>Work Days:</strong> ${workDays} | <strong>Leave Days:</strong> ${leaveDays}</p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>No employees found.</p>
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