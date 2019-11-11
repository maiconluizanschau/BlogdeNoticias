<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>login</title>
    </head>

    <body>
    <strong>${msg}</strong>
<h2>Fazer login</h2>
<form action="index.htm" method="post">
    Usuario: <input type="text" name="usuario">
    <br>
    Senha: <input type="password" name="senha"><br />
    <input type="submit" value="Entrar">
</form>
<br />
<a href="cadastrar-usuario.htm">Cadastrar usuario</a>

<h2>ultimas noticias</h2>
<table>
    <c:forEach items="${listaNoticias}" var="noticia">
    <tr>
        <th>Noticia</th>
        <th>Data/Hora</th>
    </tr>
    <tr>
        <td><a href="noticia.htm?id=${noticia.id}">${noticia.titulo}</a></td><br/>
        <td><fmt:formatDate value="${noticia.dataEhora.getTime()}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    </tr>
    </c:forEach>
</table>
    </body>
</html>
