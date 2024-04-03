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
<h2>Add a new release.</h2>
<form:form method="POST" enctype="multipart/form-data" modelAttribute="bookForm">
    <form:label path="bookName">Book Name</form:label><br/>
    <form:input type="text" path="bookName"/><br/><br/>
    <form:label path="author">Author</form:label><br/>
    <form:input type="text" path="author"/><br/><br/>
    <form:label path="price">Price</form:label><br/>
    <form:input type="int" path="price"/><br/><br/>
    <form:label path="description">Description</form:label><br/>
    <form:textarea path="description" rows="5" cols="30"/><br/><br/>
    <b>Cover Photo</b><br/>
    <input type="file" name="coverPhotos" accept="image/png, image/gif, image/jpeg"/><br/><br/>
    <input type="submit" value="Submit"/>
</form:form>
</body>
</html>