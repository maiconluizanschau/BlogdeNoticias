<%-- 
    Document   : usuarios
    Created on : 12/05/2015, 23:19:27
    Author     : maicon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
     <strong>Voce esta logado como: ${usuario.nome}</strong><br/>
<table>
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Login</th>
        <th>Senha</th>
        <th>Editar</th>
        <th>Remover</th>
    </tr>
    <c:forEach items="${listaUsuarios}" var="u"> <!-- u serÃ¡ a variavel -->
        <tr>
            <td>${u.id}</td>
            <td><a href="busca-usuario.htm?id=${u.id}">${u.nome}</a></td>
            <td>${u.login}</td>
            <td>${u.senha}</td>
            <td><a href="editar-usuario.htm?id=${u.id}" title="Editar usuario!">Editar</a></td>
            <td><a href="remover-usuario.htm?id=${u.id}" onclick="return confirm('Tem certeza que deseja excluir?');" title="Excluir usuÃ¡rio!">x</a></td>
        </tr>
    </c:forEach>
</table>
<br/>
<h2>Noticias do usuario</h2>
<c:choose>
    <c:when test="${not empty noticiasUsuario}">
        <table>
            <tr>
                <th>Noticia</th>
                <th>Data/Hora</th>
                <th>Editar</th>
                <th>Remover</th>
            </tr>
            <c:forEach items="${noticiasUsuario}" var="noticia">
                <tr>
                    <td><a href="noticia.htm?id=${noticia.id}">${noticia.titulo}</a></td><br/>
                    <td><fmt:formatDate value="${noticia.data_hora.getTime()}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td><a href="editar-noticia.htm?id=${noticia.id}" title="Editar noticia!">Editar</a></td>
                    <td><a href="remover-noticia.htm?id=${noticia.id}" onclick="return confirm('Tem certeza que deseja excluir?');" title="Excluir noticia!">x</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:when test="${empty noticiasUsuario}">
        <p>Voce ainda nao cadastro nenhuma noticia! <a href="cadastrar-noticia.htm">Cadastre a primeira</a>!</p>
    </c:when>
</c:choose>
<br />



<a href="cadastrar-noticia.htm">Cadastrar nova noticia</a><br/>
<a href="sair.htm"><< Sair</a>
    </body>
</html>
