<!DOCTYPE html>
<html>
<head><title>Online Book Store</title></head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<h2>Create a User</h2>
<form:form method="POST" modelAttribute="siteUser">
    <form:label path="username">Username</form:label><br/>
    <form:input type="text" path="username"/><br/><br/>
    <form:label path="password">Password</form:label><br/>
    <form:input type="text" value="{noop}" path="password"/><br/><br/>
    <form:label path="fullname">Full Name</form:label><br/>
    <form:input type="text" path="fullname"/><br/><br/>
    <form:label path="email">Email</form:label><br/>
    <form:input type="text" path="email"/><br/><br/>
    <form:label path="address">Delivery Address</form:label><br/>
    <form:input type="text" path="address"/><br/><br/>
    <form:label path="roles">Roles</form:label><br/>
    <form:checkbox path="roles" value="ROLE_USER"/>ROLE_USER
    <form:checkbox path="roles" value="ROLE_ADMIN"/>ROLE_ADMIN
    <br/><br/>
    <input type="submit" value="Add User"/>
</form:form>
</body>
</html>