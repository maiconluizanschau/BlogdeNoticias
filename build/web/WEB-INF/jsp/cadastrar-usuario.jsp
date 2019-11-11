<%-- 
    Document   : cadastrar-usuario
    Created on : 12/05/2015, 23:18:29
    Author     : maicon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>cadastrar usuarios</title>
    </head>
    <body>
        <strong>${msg}</strong><br/>
<form action="cadastrar-usuario.htm" method="POST">
    Nome: <input type="text" name="nome">
    <br>
    Login: <input type="text" name="login">
    <br>Senha: <input type="password" name="senha"><br />
    <input type="hidden" name="ativo" value="1" />
    <input type="submit" value="Cadastrar">
</form>



<br/>
<a href="usuarios.htm">Ver todos!</a>

    </body>
</html>
