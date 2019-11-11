<%-- 
    Document   : cadastrar-noticia
    Created on : 12/05/2015, 23:18:12
    Author     : maicon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuarios</title>
    </head>
    <body>
    <strong>${msg}</strong><br/>
<form action="cadastrar-noticia.htm" method="POST">
    Titulo: <input type="text" name="titulo">
    <br>
    Descricao: <input type="text" name="descricao">
    <br>
    <input type="submit" value="Cadastrar">
</form>
<a href="usuarios.htm">Ver todos !</a>
    </body>
</html>
