<#import "parts/admcommon.ftl" as a>

<@a.admnav>
    <div class="container" style="margin-top: 2%">
        <h3 class="text-center">Список достопримечательностей</h3>
        <table class="table adm">
            <thead>
            <tr>
                <th scope="col">Изображение</th>
                <th scope="col">Наименование</th>
                <th scope="col">Тип</th>
                <th scope="col">Рейтинг</th>
                <th scope="col"><a class="btn-sm btn-outline-light" href="/admin-panel/addsight/">+</a></th>
            </tr>
            </thead>
            <tbody>
            <#list sights as sight>
                <tr>
                    <td><img style="width: 10em" src="/img/${sight.image}"></td>
                    <td>${sight.title}</td>
                    <td>${sight.type_mark}</td>
                    <td>${sight.rating}</td>
                    <td><a class="btn btn-dark" href="/admin-panel/sightslist/${sight.id}">Изменить</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@a.admnav>