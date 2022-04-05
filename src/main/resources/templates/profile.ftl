<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">


<@c.page>
    <div class="container" style="margin-top: 2%">
        <div class="row g-3 border justify-content-center" style="margin-top: 2%; margin-left: 10%; margin-right: 10%">
            <div class="col-3 offset-2">
                <img style="width: 5em" src="/img/${CurrentUser.get().imgProfile}">
            </div>
            <div class="col-3">
                <h5>${CurrentUser.get().name} ${CurrentUser.get().second_name}</h5>
                ${CurrentUser.get().email} <br>
                <#if id?? && id == id_user>
                    <a class="btn btn-dark" style="margin-top: 2%" href="/profile/edit/${CurrentUser.get().id}">Изменить</a>
                </#if>
            </div>
            <div>
                Было посещено:
            </div>
            <div style="margin-top: 2%">
                Любимая категория достопримечательностей:
            </div>
        </div>
    </div>
</@c.page>