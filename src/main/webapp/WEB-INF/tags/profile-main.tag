<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<div class="panel panel-primary">
    <a href="/edit">
        <img img class="img-responsive photo" src="${profile.largePhoto}" alt="photo"> </a>
    <h1 class="text-center">
        <a style="color: black;" href="/edit">${profile.fullName}</a>
    </h1>
    <h6 class="text-center">
        <strong>${profile.city}, ${profile.country}</strong>
    </h6>
    <h6 class="text-center">
        <strong>Age:</strong> ${profile.age}, <strong>Birthday: </strong> ${profile.birthDay}
    </h6>
    <div class="list-group contacts">
        <a class="list-group-item" href="tel:${profile.phone}"><i class="fa fa-phone"></i> ${profile.phone}</a>
        <a class="list-group-item" href="mailto:${profile.email}"><i class="fa fa-envelope"></i> ${profile.email}</a>
        <a class="${profile.contacts.skype == null?"hidden":"list-group-item"}" href="javascript:void(0);"><i
                class="fa fa-skype"></i> ${profile.contacts.skype}</a>
        <a target="_blank" class="${profile.contacts.vkontakte == null?"hidden":"list-group-item"} "
           href="${profile.contacts.vkontakte}"><i
                class="fa fa-vk"></i>${profile.contacts.vkontakte}</a>
        <a target="_blank" class="${profile.contacts.facebook == null?"hidden":"list-group-item"}"
           href="${profile.contacts.facebook!=null?profile.contacts.facebook:"не указан"}"><i
                class="fa fa-facebook"></i>${profile.contacts.facebook!=null?profile.contacts.facebook:"не указан"}</a>
        <a target="_blank" class="${profile.contacts.linkedin == null?"hidden":"list-group-item"}"
           href="${profile.contacts.linkedin!=null?profile.contacts.linkedin:"не указан"}"><i
                class="fa fa-linkedin"></i>${profile.contacts.linkedin!=null?profile.contacts.linkedin:"не указан"}</a>
        <a target="_blank" class="${profile.contacts.github == null?"hidden":"list-group-item"}"
           href="${profile.contacts.github!=null?profile.contacts.github:"не указан"}"><i
                class="fa fa-github"></i>${profile.contacts.github!=null?profile.contacts.github:"не указан"}</a>
        <a target="_blank" class="${profile.contacts.stackoverflow == null?"hidden":"list-group-item"}"
           href="${profile.contacts.stackoverflow!=null?profile.contacts.stackoverflow:"не указан"}"><i
                class="fa fa-stack-overflow"></i>${profile.contacts.stackoverflow!=null?profile.contacts.stackoverflow:"не указан"}
        </a>
    </div>

</div>