<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Url minimizer</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="${contextPath}/pages/css/style.css"/>
</head>
<body>
    <div class="container">
        <h1 class="text-center">Welcome to URL minimizer</h1>
        <p class="text-center">Please fill in this form to create your minimized url.</p>
        <hr>
        <div class = "form-group">
            <form:form method="POST" modelAttribute="url" action="/createURL">
                <div class="form-group ">
                   <spring:bind path="originalURL">
                       <label for="originalUrl"><b>URL</b></label>
                       <div class="form-group inner-addon left-addon ${status.error ? 'has-error' : ''}">
                                <form:input type="text" path="originalURL" class="form-control" placeholder="Enter URL" name="originalUrl" required="true"/>
                            <form:errors path="originalURL"/>
                       </div>
                    </spring:bind>

                    <spring:bind path="expiredDate">
                        <label for="activeDays"><b>Active until (date)</b></label>
                            <div class="form-group inner-addon left-addon ${status.error ? 'has-error' : ''}">
                                    <form:input type="date" path="expiredDate" class="form-control" title="Select createdDate until URL is active" name="activeDays"/>
                            </div>

                        <label for="activeDays"><b>Active until (days)</b></label>
                            <div class="form-group inner-addon left-addon ${status.error ? 'has-error' : ''}">
                                    <form:input type="text" path="activeDays" class="form-control" placeholder="Enter days until URL is active" name="activeDays"/>
                                <form:errors path="expiredDate"/>
                            </div>
                    </spring:bind>

                    <label for="isActive"><b>Active URL?</b></label>
                    <form:checkbox path="active" name="isActive"/>

                    <hr>
                    <p>By creating an account you agree to our <a href="#">Terms & Privacy.</a></p>
                    <button type="submit" id="submit">Get your mini-URL</button>
                </div>
            </form:form>

            <div class="center-block text-center">
                <p id="resultURLText" hidden>Your result URL is</p>
                <a id="resultURL" href="#" hidden></a>
            </div>

        </div>
    </div>
</body>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script src="${contextPath}/pages/js/app.js"></script>
</html>
