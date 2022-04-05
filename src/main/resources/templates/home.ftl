<#import "parts/common.ftl" as c>
<@c.page>
    <div class="offcanvas offcanvas-start" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="leftCollapse">
        <div class="offcanvas-header">
            <h5 class="offcanvas-title" id="offcanvasScrollingLabel">Colored with scrolling</h5>
            <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <p>Здарова</p>
        </div>
    </div>
    <div class="offcanvas offcanvas-end" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="rightCollapse">
        <div class="offcanvas-header" style="padding-bottom: 0;">
            <h5 class="offcanvas-title">Текущие достопримечательности:</h5>
            <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <div class="overflow-auto" id="mysights">
            </div>
        </div>
        <div class="offcanvas-footer">
            <button type="button" class="btn btn-dark" style="margin-left: 5%" id="get_route">Построить маршрут</button>
            <button type="button" class="btn btn-dark" style="margin-left: 10%" id="clear_route">Очистить</button>
        </div>
    </div>
    <button class="btn btn-sm btn-dark canvas" type="button" data-bs-toggle="offcanvas" data-bs-target="#leftCollapse" aria-controls="leftCollapse">></button>
    <form action="/mysights" method="GET">
        <button class="btn btn-sm btn-dark canvas-right" type="button" data-bs-toggle="offcanvas" data-bs-target="#rightCollapse" aria-controls="rightCollapse" id="right_panel"><</button>
    </form>
    <div id="map" style="width: 100vw; height: 91.5vh" class="container-fluid map"></div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.4.4/sweetalert2.min.js"></script>
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.4.4/sweetalert2.min.css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://api-maps.yandex.ru/2.1/?apikey=c5b93fa8-a88c-4043-90c0-946b490fece5&lang=ru_RU" type="text/javascript"></script>
    <script type="text/javascript" src="/static/main.js"></script>
</@c.page>