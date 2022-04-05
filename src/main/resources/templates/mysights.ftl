successcheckfortrue
<#list sights as sight>
    <div class="row border" style="margin-bottom: 3%; --bs-gutter-x:0;">
        <div class="col-5 marks routs" id="${sight.sight.getGeom().getX()} ${sight.sight.getGeom().getY()}">
            <img class="rounded" src="/img/${sight.sight.getImage()}" style="width:10em">
        </div>
        <div class="col-5 offset-1 marks" id="${sight.sight.getGeom().getX()} ${sight.sight.getGeom().getY()}">
            <div class="title">${sight.sight.getName_mark()}</div>
            <span class='description' style="margin-top: 5%">${sight.sight.getType_mark()}</span>

        </div>
        <div class="col-1">
            <form method="post" class="del_sight" id="${sight.sight.getId()}">
                <button type="button" class="btn-close"></button>
            </form>
            <a class="complete_sight" id="${sight.sight.getId()}"><img src="/img/68e2b43f-0a8f-4ed6-97a7-9fde4314e703.complete.png" style="width: 1.3em"></a>
        </div>
    </div>
</#list>
