<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <c:set var="weekDays" value="${requestScope.weekDays}"/>
    <c:set var="list" value="${requestScope.list}"/>

    <!-- Header -->
    <header>
        <h1>Employee Agenda System</h1>
        <div class="user-info">Welcome, <c:out value="${sessionScope.user != null ? sessionScope.user.username : 'Guest'}"/></div>
        <div class="weekly-schedule">
            <h4>Weekly Schedule (<fmt:formatDate value="${weekStart}" pattern="dd/MM"/> - <fmt:formatDate value="${weekEnd}" pattern="dd/MM/yyyy"/>)</h4>
        </div>
    </header>

    <!-- Main Content -->
    <main>
        <section class="employee-list">
            <c:choose>
                <c:when test="${not empty list}">
                    <c:forEach var="employee" items="${list}">
                        <div class="employee-card">
                            <div class="employee-info">
                                <h3>Employee Details</h3>
                                <p><strong>ID:</strong> <c:out value="${employee.employeeid}"/></p>
                                <p><strong>Full Name:</strong> <c:out value="${employee.fullname}"/></p>
                                <p><strong>Department:</strong> <c:out value="${employee.department}"/></p>
                                <p><strong>Manager ID:</strong> <c:out value="${employee.managerid}"/></p>
                            </div>
                            <div class="calendar">
                                <table>
                                    <thead>
                                        <tr>
                                            <c:forEach var="day" items="${weekDays}">
                                                <th><fmt:formatDate value="${day}" pattern="EEE dd/MM"/></th>
                                            </c:forEach>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <c:set var="workDays" value="0"/>
                                            <c:set var="leaveDays" value="0"/>
                                            <c:forEach var="currentDate" items="${weekDays}" varStatus="loop">
                                                <fmt:formatDate var="currentDateStr" value="${currentDate}" pattern="yyyy-MM-dd"/>
                                                <c:set var="isLeave" value="false"/>
                                                <c:forEach var="period" items="${employee.leavePeriods}">
                                                    <fmt:formatDate var="startDateStr" value="${period.startDate}" pattern="yyyy-MM-dd"/>
                                                    <fmt:formatDate var="endDateStr" value="${period.endDate}" pattern="yyyy-MM-dd"/>
                                                    <c:if test="${startDateStr <= currentDateStr && endDateStr >= currentDateStr}">
                                                        <c:set var="isLeave" value="true"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:choose>
                                                    <c:when test="${loop.index == 5 || loop.index == 6}">
                                                        <td class="off-day">Off</td>
                                                    </c:when>
                                                    <c:when test="${isLeave}">
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

    <footer>
        <p>© 2025 My Company. All Rights Reserved.</p>
    </footer>
</body>
</html>