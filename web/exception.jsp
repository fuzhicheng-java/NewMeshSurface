<%-- 
    Document   : exception
    Created on : Apr 30, 2014, 12:23:27 AM
    Author     : zhichengfu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%! String info=" ";%>
        <% info=request.getParameter("info");%>
        <h1><%=info%></h1>
    </body>
</html>
