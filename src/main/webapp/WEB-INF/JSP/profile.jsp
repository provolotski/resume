<%--
  Created by IntelliJ IDEA.
  User: kpss
  Date: 8/11/21
  Time: 8:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>
<div class="container">
    <div class="row">
        <div class="col-lg-4 col-md-4 col-sm-6">
            <resume:profile-main/>
            <div class="hidden-xs">
                <resume:profile-languages/>
                <resume:profile-Hobby/>
                <resume:profile-Info/>
            </div>
        </div>
        <div class="col-mg-8 col-sm-6">
            <resume:profile-Objective/>
            <resume:profile-Skill/>
            <resume:profile-Practices/>
            <resume:profile-certificates/>
            <resume:profile-cource/>
            <resume:profile-educations/>
        </div>
        <div class="col-xs-12 visible-xs-block">
            <resume:profile-languages/>
            <resume:profile-Hobby/>
            <resume:profile-Info/>
        </div>
    </div>
</div>
