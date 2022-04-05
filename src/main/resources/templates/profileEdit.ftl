<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
    <div class="container" style="margin-top: 2%">
    <#if id == id_user && id != 0>
        <h3 class="text-center">Редактирование ${CurrentUser.get().getUsername()}</h3>
        <div class="row g-3 border justify-content-center" style="margin-top: 2%; margin-left: 10%; margin-right: 10%">
            <form method="post" action="/profile/edit/" enctype="multipart/form-data">
            <div class="col-4 offset-4">
                <h4>${error?ifExists}</h4>
                <img style="width: 5em; margin: 4%" src="/img/${CurrentUser.get().imgProfile}">
                <label for="formFile">Изображение профиля</label>
                <input class="form-control" type="file" id="formFile" name="file">
                <label class="form-label">Имя</label>
                <input type="text" class="form-control" value="${CurrentUser.get().name}" name="name">
                <label class="form-label">Фамилия</label>
                <input type="text" class="form-control" value="${CurrentUser.get().second_name}" name="second_name">
                <label class="form-label">Email</label>
                <input type="email" class="form-control" value="${CurrentUser.get().email}" name="email">
                <label class="form-label">Пароль</label>
                <input type="password" class="form-control" name="password">
                <input type="hidden" value="${CurrentUser.get().id}" name="userId">
                <input type="hidden" value="${_csrf.token}" name="_csrf">
                <button class="form-control btn btn-dark" style="margin-top: 2%; margin-bottom: 2%" type="submit">Сохранить</button>
            </div>
            </form>
        </div>
        <#else>
            <h3 class="text-center">Страница недоступна!</h3>
    </#if>
    </div>

</@c.page>