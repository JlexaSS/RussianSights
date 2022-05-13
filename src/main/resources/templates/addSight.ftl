<#import "parts/admcommon.ftl" as a>

<@a.admnav>
    <div class="container" style="margin-top: 1%">
        <h3 class="text-center">Добавление новой достопримечательности</h3>
        <form class="row g-3" action="/admin-panel/addsight" method="post" enctype="multipart/form-data">
            <div class="col-md-6">
                <label for="title" class="form-label">Краткое название</label>
                <input type="text" class="form-control" id="title" name="title" required="required">
            </div>
            <div class="col-md-6">
                <label for="name_mark" class="form-label">Наименование</label>
                <input type="text" class="form-control" id="name_mark" name="name_mark" required="required">
            </div>
            <div class="col-md-6">
                <label for="enumType" class="form-label">Тип</label>
                <select class="form-control" id="enumType" name="enumType">
                    <#list enumValues as enum>
                        <option value="${enum}">${enum.getCategory()}</option>
                    </#list>

                </select>
            </div>
            <div class="col-md-6">
                <label for="file" class="form-label">Изображение</label>
                <input class="form-control" type="file" id="file" name="file" required="required">
            </div>
            <div class="col-12">
                <label for="description" class="form-label">Описание</label>
                <input type="text" class="form-control" id="description" name="description" required="required">
            </div>
            <div class="col-12">
                <label for="map" class="form-label">Координаты</label>
                <input type="hidden" class="form-control" id="geo" name="geo" required="required">
                <div id="map" style="width: 50vw; height: 50vh" class="container-fluid map"></div>
            </div>

            <div class="col-2">
                <input class="form-control btn btn-outline-success" style="margin-top: 0.5em" type="submit" value="Добавить"/>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </form>
    </div>
    <script src="https://api-maps.yandex.ru/2.1/?apikey=c5b93fa8-a88c-4043-90c0-946b490fece5&lang=ru_RU" type="text/javascript"></script>
    <script type="text/javascript" src="/static/addSight.js"></script>
</@a.admnav>