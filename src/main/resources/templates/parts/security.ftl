<#assign
 know = Session.SPRING_SECURITY_CONTEXT??
>
<#if know>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        username = user.getUsername()
        id = user.getId()
        img = user.getImgProfile()
        name = user.getName()
        second_name = user.getSecond_name()
        isAdmin = user.isAdmin()
        refresh = false
    >
<#else>
    <#assign
        name = "Гость"
        isAdmin = false
        id = 0
    >
</#if>