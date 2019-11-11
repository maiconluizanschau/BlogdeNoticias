<%-- 
    Document   : editar-usuario
    Created on : 12/05/2015, 23:18:54
    Author     : maicon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastrar usuarios</title>
    </head>
    <body>
   <strong>${msg}</strong><br/>
<form action="editar-usuario.htm" method="POST">
  Nome: <input type="text" name="nome" value="${usuario.nome}">
  <br>
  Login: <input type="text" name="login" value="${usuario.login}">
  <br>Senha: <input type="password" name="senha" ${usuario.senha}><br />
  <!-- Ativo -->
  Situacao: <select name="ativo">
    <option value="1">Ativo</option>
    <option value="0">Inativo</option>
  </select>
  <br/>
  <input type="hidden" name="id" value="${usuario.id}" />
  <input type="submit" value="Editar">
</form>

<br/>
<a href="usuarios.htm">Ver todos!</a>

    </body>
</html>
