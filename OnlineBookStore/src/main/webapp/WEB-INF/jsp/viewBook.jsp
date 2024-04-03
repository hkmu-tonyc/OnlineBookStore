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
<h2>Book #${bookId}: <c:out value="${book.bookName}"/></h2>
<security:authorize access="hasRole('ADMIN') or
                            principal.username=='${ticket.bookName}'">
    [<a href="<c:url value="/book/edit/${book.id}" />">Edit</a>]
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
    [<a href="<c:url value="/book/delete/${book.id}" />">Delete</a>]
</security:authorize>
<br/><br/>
<i>Author - <c:out value="${book.author}"/></i><br/><br/>
<i>Price - $<c:out value="${book.price}"/></i><br/><br/>
<i>Description - <c:out value="${book.description}"/></i><br/><br/>
<c:if test="${!empty book.coverPhotos}">
    Cover Photos:<br/><br/>
    <c:forEach items="${book.coverPhotos}" var="coverPhoto" varStatus="status">
        <c:if test="${!status.first}">, </c:if>
        <img src="<c:url value="/book/${bookId}/coverPhoto/${coverPhoto.id}" />" height="500" width="350">
        <a href="<c:url value="/book/${bookId}/coverPhoto/${coverPhoto.id}" />">
            <c:out value="${coverPhoto.name}"/></a>
        <security:authorize access="hasRole('ADMIN')">
            [<a href="<c:url value="/book/${bookId}/delete/${coverPhoto.id}" />">Delete</a>]
        </security:authorize>
    </c:forEach><br/><br/>
</c:if>
<a href="<c:url value="/book" />">Return to book catalog</a>
</body>
</html>
