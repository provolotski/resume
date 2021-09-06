<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<div class="panel panel-primary certificates">
    <div class="panel-heading">
        <h3 class="panel-title">
            <i class="fa fa-certificate"></i>Certificates <a class="edit-block" href="/edit/certificates">Edit</a>
        </h3>
    </div>
    <div class="panel-body">
        <c:forEach items="${profile.certificates}" var="cert">
            <a data-url="${cert.largeUrl}" data-title ="${cert.name}" href="#" class="thumbnail certificate-link">
                <img alt ="${cert.name}" src="${cert.smallUrl}" class="img-responsive"><span>${cert.name}</span>
            </a>

        </c:forEach>



    </div>
</div>