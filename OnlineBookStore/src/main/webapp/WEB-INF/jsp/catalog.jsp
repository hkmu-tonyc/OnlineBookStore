<!DOCTYPE html>
<html>
<head>
    <title>Online Book Store</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<h2>Books</h2>
<security:authorize access="hasRole('ADMIN')">
    <a href="<c:url value="/user" />">Manage User Accounts</a><br /><br />
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
<a href="<c:url value="/book/create" />">Create a Book</a><br/><br/>
</security:authorize>
<c:choose>
    <c:when test="${fn:length(bookDatabase) == 0}">
        <i>New books will be released soon.</i>
    </c:when>
    <c:otherwise>
        <c:forEach items="${bookDatabase}" var="entry">
            Book No. ${entry.id}:
            <a href="<c:url value="/book/view/${entry.id}" />">
                <c:out value="${entry.bookName}"/></a>
            (author: <c:out value="${entry.author}"/>)
            <security:authorize access="hasRole('ADMIN') or
 principal.username=='${entry.bookName}'">
                [<a href="<c:url value="/book/edit/${entry.id}" />">Edit</a>]
            </security:authorize>
            <security:authorize access="hasRole('ADMIN')">
                [<a href="<c:url value="/book/delete/${entry.id}" />">Delete</a>]
            </security:authorize>
            <br />
        </c:forEach>
    </c:otherwise>
</c:choose>
</body>
</html>