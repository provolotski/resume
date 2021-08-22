<%--
  Created by IntelliJ IDEA.
  User: ViachaslauProvolocki
  Date: 20.08.2021
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<html lang="en">
<head>
    <meta name="viewport" content="width-device-width, initial-scale=1">
    <jsp:include page="../section/css.jsp"></jsp:include>
</head>
<body class="resume">
<jsp:include page="../section/header.jsp"></jsp:include>
<jsp:include page="../section/nav.jsp"></jsp:include>
<section class="main">
    <sitemesh:write property="body" />
</section>
<jsp:include page="../section/footer.jsp"></jsp:include>
<jsp:include page="../section/js.jsp"></jsp:include>

</body>
</html>
