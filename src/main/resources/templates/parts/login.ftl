<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input class="form-control btn-outline-danger" type="submit" value="Выйти">
    </form>
</#macro>