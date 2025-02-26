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
        <!-- Filter Section -->
        <section class="filter-section">
            <h2>Filter Employees</h2>
            <form action="" method="get">
                <input type="text" name="search" placeholder="Search by Name or ID" value="${param.search}">
                <select name="department">
                    <option value="">All Departments</option>
                    <option value="IT" ${param.department == 'IT' ? 'selected' : ''}>IT</option>
                    <option value="HR" ${param.department == 'HR' ? 'selected' : ''}>HR</option>
                    <option value="Finance" ${param.department == 'Finance' ? 'selected' : ''}>Finance</option>
                </select>
                <button type="submit">Filter</button>
            </form>
        </section>

        <!-- Employee List with Schedules -->
        <section class="employee-list">
            <h2>Employee Schedules</h2>
            <c:choose>
                <c:when test="${not empty employees and not empty employees}">
                    <c:forEach var="emp" items="${employees}">
                        <div class="employee-card">
                            <!-- Employee Information -->
                            <div class="employee-info">
                                <h3><c:out value="${emp.fullName != null ? emp.fullName : 'Unknown'}"/></h3>
                                <p><strong>ID:</strong> <c:out value="${emp.employeeId != null ? emp.employeeId : 'N/A'}"/></p>
                                <p><strong>Department:</strong> <c:out value="${emp.department != null ? emp.department : 'N/A'}"/></p>
                                <p><strong>Manager ID:</strong> <c:out value="${emp.managerId != null ? emp.managerId : 'N/A'}"/></p>
                            </div>

                            <!-- Weekly Calendar for Each Employee -->
                            <div class="calendar">
                                <h4>Weekly Schedule (Feb 24 - Mar 2, 2025)</h4>
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Mon</th>
                                            <th>Tue</th>
                                            <th>Wed</th>
                                            <th>Thu</th>
                                            <th>Fri</th>
                                            <th>Sat</th>
                                            <th>Sun</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td class="work-day">🟩</td> <!-- Work Day (Sample) -->
                                            <td class="work-day">🟩</td> <!-- Work Day (Sample) -->
                                            <td class="off-day">🟥</td>  <!-- Off Day (Sample) -->
                                            <td class="work-day">🟩</td> <!-- Work Day (Sample) -->
                                            <td class="work-day">🟩</td> <!-- Work Day (Sample) -->
                                            <td class="off-day">🟥</td>  <!-- Off Day (Sample) -->
                                            <td class="leave-day">🟨</td> <!-- Leave Day (Sample) -->
                                        </tr>
                                    </tbody>
                                </table>
                                <p class="summary">Worked: 5 days, Off: 2 days</p>
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
        <p>&copy; 2025 My Company. All Rights Reserved.</p>
    </footer>
</body>
</html>