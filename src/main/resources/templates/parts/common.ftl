<#include "security.ftl">
<#import "login.ftl" as l>

<#macro page>
    <!DOCTYPE html>
    <html>
    <head>
        <meta http-equiv="Content-Type" name="viewport" content="text/html width=device-width, initial-scale=1 charset=utf-8">
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title>RussianSights</title>
        <link rel="stylesheet" href="/static/style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body>
        <header>
            <div class="container-fluid">
            <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
                <div class="container-fluid">
                    <a class="navbar-brand" href="/">RussianSights</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarCollapse">
                        <form class="col-10">
                            <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Поиск" aria-label="Поиск" aria-describedby="searchButton">
                                    <button class="btn btn-outline-secondary" type="button" id="searchButton">Найти</button>
                            </div>
                        </form>
                        <div class="navbar-nav d-inline-flex mt-2 mt-md-0 ms-md-auto" style="flex-direction: row">
                            <div class="nav-item text-nowrap">
                                <a class="nav-link px-3" href="/">Мои места</a>
                            </div>
                            <#if name="Гость">
                            <div class="nav-item text-nowrap">
                                <div class="dropdown dropstart">
                                    <button class="btn btn-dark dropdown-toggle" type="button" id="loginDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                        <img style="width: 2vw" src="/img/68e2b43f-0a8f-4ed6-97a7-9fde4314e702.profile.png">
                                    </button>
                                    <div class="dropdown-menu dropdown-menu" aria-labelledby="loginDropdown">
                                        <form action="/login" id="login" method="post">
                                            <div class="logform">
                                                <label> Логин: </label> <input class="form-control" type="text" name="username"/>
                                                <label> Пароль: </label> <input class="form-control" type="password" name="password"/>
                                                <input type="hidden" name="_csrf" value="${_csrf.token}">
                                                <input class="form-control btn-outline-success" style="margin-top: 0.5em" type="submit" value="Войти"/>
                                            </div>
                                        </form>
                                        <a class="nav-link"  href="/registration">Регистрация</a>
                                    </div>
                                </div>
                            </div>
                            <#else>
                                <div class="dropdown dropstart">
                                    <div class="nav-item text-nowrap dropdown-toggle" id="profileDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                        <a class="nav-link px-3" href="/registration"><img style="width: 2vw" src="/img/${img}"></a>
                                    </div>
                                    <div class="dropdown-menu dropdown-menu" aria-labelledby="profileDropdown">
                                        <div class="logform row g-1">
                                            <div class="col-3">
                                                <img style="width: 2vw" src="/img/${img}">
                                            </div>
                                            <div class="col-9">
                                                <a class="adm" href="/profile/${id}">${username}</a>
                                            </div>
                                            <div>
                                                ${name} ${second_name}
                                            </div>
                                            <#if isAdmin>
                                                <a class="nav-link px-3" href="/admin-panel">Админ-панель</a>
                                            </#if>
                                            <@l.logout/>
                                        </div>
                                    </div>
                                </div>

                            </#if>
                        </div>
                    </div>
                </div>
            </nav>
            </div>

        </header>

            <#nested>
        <script src="https://yandex.st/jquery/2.2.3/jquery.min.js" type="text/javascript"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
    </html>
</#macro>