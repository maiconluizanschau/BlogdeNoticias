<%-- 
    Document   : usuario
    Created on : 12/05/2015, 23:19:16
    Author     : maicon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>${usuario.nome} - Usuario</title>
</head>
<body>
<strong>Nome:</strong> ${usuario.nome} <br />
<strong>Login:</strong> ${usuario.login} <br />
<strong>Senha:</strong> ${usuario.senha}
    </body>
</html>
