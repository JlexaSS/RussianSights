<#import "parts/admcommon.ftl" as a>

<@a.admnav>
    <div class="container" style="margin-top: 2%">
        <h3 class="text-center">Редактирование пользователя</h3>
        <div class="row g-3 border justify-content-center" style="margin-top: 2%; margin-left: 10%; margin-right: 10%">
            <div class="col-3 offset-2">
                <img style="width: 5em" src="/img/${user.imgProfile}">
            </div>
            <div class="col-3">
                <h5>${user.name} ${user.second_name}</h5>
                ${user.email}
            </div>
            <form action="/admin-panel/userlist" method="post">
                <div class="col-6 offset-3">
                    <label for="username" class="form-label">Логин</label>
                    <input type="text" class="form-control" id="username" value="${user.username}" name="username">
                </div>
                <div class="col-6 offset-3" style="margin-top: 2%">
                    <#list roles as role>
                        <input class="form-check-input" type="checkbox" id="${role}" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>
                        <label class="form-check-label" for="${role}">${role}</label>
                    </#list>
                </div>
                <input type="hidden" value="${user.id}" name="userId">
                <input type="hidden" value="${_csrf.token}" name="_csrf">
                <div class="col-2 offset-3" style="margin-top: 2%; margin-bottom: 2%">
                    <button class="form-control btn btn-dark" type="submit">Сохранить</button>
                </div>
            </form>
        </div>
    </div>
</@a.admnav>