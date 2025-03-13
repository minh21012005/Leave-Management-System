<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Review Leave Request</title>
        <link rel="stylesheet" type="text/css" href="./CSS/style5.css">
        <script>
            function paging(page, event) {
                event.preventDefault();
                var form = document.createElement("form");
                form.method = "POST";
                form.action = "pagingreviewrequest";

                var input = document.createElement("input");
                input.type = "hidden";
                input.name = "currentpage";
                input.value = page;
                form.appendChild(input);

                document.body.appendChild(form);
                form.submit();
            }
        </script>
    </head>
    <body>
        <header>
            <h1>Review Leave Request</h1>
        </header>

        <c:set var="list" value="${requestScope.list}"/>
        <c:set var="currentPage" value="${requestScope.currentPage}"/>
        <c:set var="totalPages" value="${requestScope.totalPages}"/>

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
            <div class="pagination">
                <!-- Nút First (chỉ hiển thị nếu currentPage > 3) -->
                <c:if test="${currentPage > 3}">
                    <a href="reviewrequest" class="page-link">« First</a>
                </c:if>

                <!-- Hiển thị 2 trang trước trang hiện tại, nhưng không hiển thị trang 1 nếu có First -->
                <c:forEach begin="${currentPage - 2 > 1 ? currentPage - 2 : 1}" end="${currentPage - 1}" var="i">
                    <c:if test="${i > 1 || currentPage <= 3}">
                        <a href="a" onclick="paging(${i}, event)" class="page-link">${i}</a>
                    </c:if>
                </c:forEach>

                <!-- Trang hiện tại -->
                <a href="#" class="page-link active">${currentPage}</a>

                <!-- Hiển thị 2 trang sau trang hiện tại, nhưng không hiển thị totalPages nếu có Last -->
                <c:forEach begin="${currentPage + 1}" end="${currentPage + 2 < totalPages ? currentPage + 2 : totalPages}" var="i">
                    <c:if test="${i < totalPages || currentPage >= totalPages - 2}">
                        <a href="a" onclick="paging(${i}, event)" class="page-link">${i}</a>
                    </c:if>
                </c:forEach>

                <!-- Nút Last (chỉ hiển thị nếu currentPage < totalPages - 2) -->
                <c:if test="${currentPage < totalPages - 2}">
                    <a href="a" onclick="paging(${totalPages}, event)" class="page-link">Last »</a>
                </c:if>
            </div>
        </div>

        <footer>
            <p>© 2025 My Company. All Rights Reserved.</p>
        </footer>

    </body>
</html>