<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <link rel="stylesheet" href="./CSS/style1.css">
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                document.getElementById("showPassword").addEventListener("change", function () {
                    let passwordField = document.getElementById("password");
                    passwordField.type = this.checked ? "text" : "password";
                });
            });
        </script>
    </head>
    <body>
        <div class="login-container">
            <h2>Login</h2>
            <form action="login" method="post">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required placeholder="Enter email">

                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required placeholder="Enter password">

                <div class="show-password-container">
                    <input type="checkbox" id="showPassword">
                    <label for="showPassword" style="margin: 0; font-weight: normal;">Show Password</label>
                </div>

                <button type="submit">SIGN IN</button>
            </form>

            <p><a href="#">Forgot Username / Password?</a></p>
            <p>Don't have an account? <a href="#">Sign up</a></p>
        </div>
        <c:if test="${not empty sessionScope.message}">
            <script>
                alert("${sessionScope.message}");
            </script>
            <c:remove var="message" scope="session"/>
        </c:if>
    </body>
</html>
