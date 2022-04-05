<#import "parts/common.ftl" as c>
<@c.page>
    ${message?ifExists}
    <div class="container" style="margin-top: 3%">
        <h3 class="text-center">Авторизация</h3>
        <form class="row g-3" action="/login" method="post">
            <div class="col-md-6 offset-3">
                <label for="username" class="form-label">Логин</label>
                <input type="text" class="form-control" id="username" name="username">
            </div>
            <div class="col-md-6 offset-3">
                <label for="password" class="form-label">Пароль</label>
                <input type="password" class="form-control" id="password" name="password">
            </div>
            <div class="col-2 offset-3">
                <input class="form-control btn btn-outline-success" style="margin-top: 0.5em" type="submit" value="Войти"/>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </form>
    </div>
</@c.page>