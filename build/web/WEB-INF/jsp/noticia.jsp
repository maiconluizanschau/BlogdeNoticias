<%-- 
    Document   : noticia
    Created on : 12/05/2015, 23:19:06
    Author     : maicon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>Noticia: ${noticia.titulo}</title>
</head>
<i>
<h1>${noticia.titulo}</h1>
${noticia.descricao}<br />
  <i></i><fmt:formatDate value="${noticia.dataEhora.getTime()}" pattern="yyyy-MM-dd HH:mm:ss" /></i>

    </body>
</html>
