<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>


<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">
            <i class="fa fa-heart"></i> Hobbi <a class="edit-block" href="/edit/hobby">Edit</a>
        </h3>
    </div>
    <div class="panel-body">
        <div class="hobbies">
            <table class="table table-bordered">
                <tbody>
                <c:forEach items="${profile.hobbies}" var="item">
                    <tr>
                        <td><i class="fa fa-heart"></i></td>
                        <td>${item.name}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>