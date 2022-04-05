<#import "parts/admcommon.ftl" as a>

<@a.admnav>
    <div class="container" style="margin-top: 2%">
        <h3 class="text-center">Список пользователей</h3>
        <table class="table adm">
            <thead>
                <tr>
                    <th scope="col">Фамилия</th>
                    <th scope="col">Имя</th>
                    <th scope="col">Изображение</th>
                    <th scope="col">Email</th>
                    <th scope="col">Логин</th>
                    <th scope="col">Роль</th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                <#list users as user>
                    <tr>
                        <td>${user.second_name}</td>
                        <td>${user.name}</td>
                        <td><img style="width: 5em" src="/img/${user.imgProfile}"></td>
                        <td>${user.email}</td>
                        <td>${user.username}</td>
                        <td><#list user.roles as role>${role}<#sep>, </#list></td>
                        <td><a class="btn btn-dark" href="/admin-panel/userlist/${user.id}">Изменить</a></td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</@a.admnav>