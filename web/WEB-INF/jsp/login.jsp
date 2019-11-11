<%-- 
    Document   : login
    Created on : 13/05/2015, 08:31:12
    Author     : maicon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>login</title>
    </head>
    <body>
       
        
    <strong>${msg}</strong>
<h2>Fazer login</h2>
<form action="login.htm" method="post">
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
