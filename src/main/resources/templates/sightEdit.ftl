<#import "parts/admcommon.ftl" as a>

<@a.admnav>
    <div class="container" style="margin-top: 2%">
        <h3 class="text-center">Редактирование достопримечательности</h3>
        <form action="/admin-panel/sightslist" method="post" enctype="multipart/form-data">
            <div class="row g-3 border justify-content-center" style="margin-top: 2%; margin-left: 10%; margin-right: 10%">
                <div class="col-5">
                    <img style="width: 20em" src="/img/${sight.get().image}">
                    <label for="image" class="form-label" style="margin-top: 2%">Изображение</label>
                    <input type="file" class="form-control" id="image" name="image">
                </div>
                <div class="col-7">
                    <label for="name_mark" class="form-label">Наименование</label>
                    <input type="text" class="form-control" id="name_mark" name="name_mark" value="${sight.get().name_mark}">
                    <label for="title" class="form-label">Заголовок</label>
                    <input type="text" class="form-control" id="title" name="title" value="${sight.get().title}">
                    <label for="description" class="form-label">Описание</label>
                    <textarea type="text" class="form-control" id="description" name="description" rows="4">${sight.get().description}</textarea>
                </div>
                <div class="col-5">
                    <label for="type_mark" class="form-label">Тип объекта</label>
                    <select class="form-control" id="type_mark" name="type_mark">
                        <option selected value="Музей">Музей</option>
                        <option value="Памятник">Памятник</option>
                        <option value="Храм">Храм</option>
                        <option value="Природа">Природа</option>
                        <option value="Скульптура">Скульптура</option>
                        <option value="Культурное наследие">Культурное наследие</option>
                    </select>
                </div>
                <div class="col-7">
                    <input type="hidden" name="id_sight" value="${sight.get().getId()}">
                    <input type="hidden" value="${_csrf.token}" name="_csrf">
                    <button class="form-control btn btn-dark" type="submit" style="margin-top: 2em">Сохранить</button>
                </div>
            </div>
        </form>
    </div>
</@a.admnav>