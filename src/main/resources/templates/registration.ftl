<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
${message?ifExists}
    <div class="container" style="margin-top: 3%">
        <h3 class="text-center">Регистрация</h3>
        <form class="row g-3" action="/registration" method="post" enctype="multipart/form-data">
            <div class="col-md-6">
                <label for="second_name" class="form-label">Фамилия</label>
                <input type="text" class="form-control" id="second_name" name="second_name">
            </div>
            <div class="col-md-6">
                <label for="name" class="form-label">Имя</label>
                <input type="text" class="form-control" id="name" name="name">
            </div>
            <div class="col-12">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Ivanov@ivan.ru">
            </div>
            <div class="col-md-6">
                <label for="username" class="form-label">Логин</label>
                <input type="text" class="form-control" id="username" name="username">
            </div>
            <div class="col-md-6">
                <label for="password" class="form-label">Пароль</label>
                <input type="password" class="form-control" id="password" name="password">
            </div>
            <div class="col-12">
                <label for="formFile" class="form-label">Изображение профиля</label>
                <input class="form-control" type="file" id="formFile" name="file">
            </div>
            <div class="col-2">
                <input class="form-control btn btn-outline-success" style="margin-top: 0.5em" type="submit" value="Зарегистрироваться"/>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </form>
    </div>
</@c.page>