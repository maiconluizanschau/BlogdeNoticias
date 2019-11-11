<%-- 
    Document   : editar-noticia
    Created on : 12/05/2015, 23:18:42
    Author     : maicon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Noticias</title>
    </head>
    <body>
        <strong>${msg}</strong><br/>
<form action="editar-noticia.htm" method="POST">
  Titulo: <input type="text" name="titulo" value="${noticia.titulo}">
  <br>
  Descricao: <input type="text" name="descricao" value="${noticia.descricao}">
  <br>
  <input type="hidden" name="id" value="${noticia.id}" />
  <input type="submit" value="Editar">
</form>
    </body>
</html>
