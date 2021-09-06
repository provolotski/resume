<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">
            <i class="fa fa-briefcase"></i> Practic Experience <a class="edit-block" href="/edit/practics">Edit</a>
        </h3>
    </div>
    <div class="panel-body">
        ${profile.practics[0]}

            <ul class="timeline">
                <c:forEach items="${profile.practics}" var="prc">
                    <li>
                        <div class="timeline-badge danger">
                            <i class="fa fa-briefcase"></i>
                        </div>
                        <div class="timeline-panel">
                            <div class="timeline-heading">
                                <h4 class="timeline-title">${prc.company}</h4>
                                <p>
                                    <small class="dates"><i class="fa fa-calendar"></i>  ${prc.beginDate}  Mar 2016 - ${prc.finishDate}
                                        <strong class="label label-danger">  Current</strong> </small>
                                </p>
                            </div>
                            <div class="timeline-body">
                                <p>
                                    <strong>Responsibilities included:</strong> ${prc.responsibilities}
                                </p>
                                <p>
                                    <strong>Demo: </strong><a href="${prc.demo}">${prc.demo}</a>
                                </p>
                                <p>
                                    <strong>Source code: </strong><a href="${prc.src}">${prc.src}</a>
                                </p>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>

    </div>
</div>