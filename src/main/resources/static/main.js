ymaps.ready(init);


function init(){

    var location = ymaps.geolocation;
    var myMap = new ymaps.Map("map", {
        center: [61.6,97.237],
        zoom: 3,
        controls: []
    },{
            yandexMapDisablePoiInteractivity: true
        }),
        objectManager = new ymaps.ObjectManager({
        clusterize: true,
        gridSize: 32,
        //clusterDisableClickZoom: false
    }), myRoute;

    var loc = location.get({
        mapStateAutoApply:true,
        })
    .then(
        function(result) {
            myMap.setCenter(result.geoObjects.get(0).geometry.getCoordinates(),10);
            return result.geoObjects.get(0).geometry.getCoordinates();
        },
        function(err) {
            console.log('Ошибка: ' + err)
        }
    );


    var geolocationControl = new ymaps.control.GeolocationControl({
        options: {noPlacemark: true}
    });
    geolocationControl.events.add('locationchange', function (event) {
        var position = event.get('position');
        myMap.panTo(position)
    });
    myMap.controls.add(geolocationControl);

    updateJson();
    objectManager.objects.options.set('preset', 'islands#greenDotIcon');
    objectManager.clusters.options.set('preset', 'islands#redClusterIcons');


    var listBoxItems = ['Музей', 'Памятник', 'Храм', 'Природа', 'Скульптура', 'Культурное наследие']
            .map(function (title) {
                return new ymaps.control.ListBoxItem({
                    data: {
                        content: title
                    },
                    state: {
                        selected: true
                    }
                })
            }),
        reducer = function (filters, filter) {
            filters[filter.data.get('content')] = filter.isSelected();
            return filters;
        },
        listBoxControl = new ymaps.control.ListBox({
            data: {
                content: 'Фильтр',
                title: 'Фильтр'
            },
            items: listBoxItems,
            state: {
                // Признак, развернут ли список.
                expanded: true,
                filters: listBoxItems.reduce(reducer, {})
            }
        });
    myMap.controls.add(listBoxControl);

    listBoxControl.events.add(['select', 'deselect'], function (e) {
        var listBoxItem = e.get('target');
        var filters = ymaps.util.extend({}, listBoxControl.state.get('filters'));
        filters[listBoxItem.data.get('content')] = listBoxItem.isSelected();
        listBoxControl.state.set('filters', filters);
    });

    var filterMonitor = new ymaps.Monitor(listBoxControl.state);
    filterMonitor.add('filters', function (filters) {
        objectManager.setFilter(getFilterFunction(filters));
    });

    function getFilterFunction(categories) {
        return function (obj) {
            var content = obj.properties.balloonContent;
            return categories[content]
        }
    }

    /*
    function updateJson(){
        $.ajax({
            url: "/json"
        }).done(function () {
            $.ajax({
                type: "GET",
                url: "static/new.json?random=" + Math.random(),
                async:false,
                cache:false,
                isLocal: true
            }).done(function(data) {
                objectManager.removeAll();
                myMap.geoObjects.removeAll();
                objectManager.add(data);
                myMap.geoObjects.add(objectManager)
            });
        })

    }*/

    function updateJson(){
        $.ajax({
            url: "/json"
        }).done(function (data) {
            objectManager.removeAll();
            myMap.geoObjects.removeAll();
            objectManager.add(data);
            myMap.geoObjects.add(objectManager)
        })
    }

    myMap.geoObjects.events.add('balloonopen', function (e) {
        document.getElementById("add_sight").addEventListener('click', clickHandler)
    })

    function clickHandler() {
        var data = document.getElementById('id_sight').value;
        var token = $("meta[name='_csrf']").attr("content");
        $.ajax({
            type: "POST",
            url: "/",
            headers: {"X-CSRF-TOKEN": token},
            data: {id_sight: data},
            success: function (data) {
                if (data === 'Достопримечательность добавлена!'){
                    Swal.fire({
                        icon: 'success',
                        title: 'Успешно!',
                        text: data
                    })
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Ошибка!',
                        text: data
                    })
                }
                UpdateMySights()
            }
        });
    }

    var token = $("meta[name='_csrf']").attr("content");
    $("#right_panel").click(UpdateMySights);
    $("#get_route").click(getRoute)
    $("#clear_route").click(clearRoute)


    function UpdateMySights(){
        $.ajax({
            type: "GET",
            headers: {"X-CSRF-TOKEN": token},
            url: "/mysights",
            success: function (data) {
                data = data.split("checkfortrue")
                if (data[0] === "error"){
                    $(".offcanvas-footer").empty();
                }
                $("#mysights").empty();
                $("#mysights").append(data[1]);
                var mod = document.querySelectorAll('.del_sight');
                var marks = document.querySelectorAll('.marks');
                var complete = document.querySelectorAll('.complete_sight');
                for (var i = 0; i < marks.length; i++){
                    marks[i].addEventListener('click', toSight)
                }
                for (var i = 0; i < mod.length; i++){
                    mod[i].addEventListener('click', deleteMySight)
                }
                for (var i = 0; i < complete.length; i++){
                    complete[i].addEventListener('click', completeMySight)
                }
                if (myRoute != null){
                    getRoute();
                }
            }
        });
    }

    function completeMySight() {
        var data = $(this).attr('id');
        var token = $("meta[name='_csrf']").attr("content");
        Swal.fire({
            title: "Оцените достопримечательность:",
            html: '<div class="rating-area">' +
            '<input type="radio" id="star-5" name="rating" value="5">' +
            '<label for="star-5" title="Оценка «5»"></label>	' +
            '<input type="radio" id="star-4" name="rating" value="4">' +
            '<label for="star-4" title="Оценка «4»"></label>    ' +
            '<input type="radio" id="star-3" name="rating" value="3">' +
            '<label for="star-3" title="Оценка «3»"></label>  ' +
            '<input type="radio" id="star-2" name="rating" value="2">' +
            '<label for="star-2" title="Оценка «2»"></label>    ' +
            '<input type="radio" id="star-1" name="rating" value="1">' +
            '<label for="star-1" title="Оценка «1»"></label>' +
           ' </div>',
            confirmButtonText: 'Отправить',
            showCancelButton: true
        }).then((res) => {
            if(res.isConfirmed){
                for (var i = 0; i < 5; i++){
                    if ($("#star-"+(i+1))[0].checked){
                        rate = i+1
                    }
                }
                $.ajax({
                    type: "POST",
                    url: "/completemysight",
                    headers: {"X-CSRF-TOKEN": token},
                    data: {id_sight: data, rate: rate},
                    success: function () {
                        UpdateMySights();
                    }
                });
            }
        })


    }

    function deleteMySight() {
        var data = $(this).attr('id');
        var token = $("meta[name='_csrf']").attr("content");
        $.ajax({
            type: "POST",
            url: "/delmysight",
            headers: {"X-CSRF-TOKEN": token},
            data: {id_sight: data},
            success: function () {
                UpdateMySights();
            }
        });
    }

    function toSight() {
        var data = $(this).attr('id');
        data = data.replaceAll(",", ".").split(" ")
        myMap.setCenter([data[0], data[1]], 15)
    }


    function getRoute(){
        myRoute && myMap.geoObjects.remove(myRoute);
        var route = new ymaps.multiRouter.MultiRoute({
            referencePoints:[
                [loc._value],
            ]
        },{
            wayPointVisible:false,
            pinActiveIconFillColor: "#2158f5",
            routeActiveStrokeWidth: 6,
            routeActiveStrokeColor: "#598bef",
        })
        var points = document.querySelectorAll(".routs");
        var data;
        var reference = route.model.getReferencePoints();
        for (var i = 0; i < points.length; i++){
            data = points[i].id
            data = data.replaceAll(",", ".").split(" ")
            reference.push(data[0]+','+ data[1]+',')
        }

        route.model.setReferencePoints(reference, [])
        myMap.geoObjects.add(myRoute=route);
    }

    function clearRoute() {
        myRoute && myMap.geoObjects.remove(myRoute);
    }

}
