ymaps.ready(init);

$(document).ready(function () {

    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });

});

function init(){
    var inputSearch = new ymaps.control.SearchControl({
        options: {
            // Пусть элемент управления будет
            // в виде поисковой строки.
            size: 'large',
            // Включим возможность искать
            // не только топонимы, но и организации.
            provider: 'yandex#search'
        }
    })

    var myMap = new ymaps.Map("map", {
            center: [50.60,36.60],
            zoom: 9,
            controls: [inputSearch]
        },{
        yandexMapDisablePoiInteractivity: true
    });

    myMap.events.add('click', function (e) {
        myMap.geoObjects.removeAll()
        var coords = e.get('coords');
        document.getElementById('geo').value = "Point("+coords[0].toPrecision(6) + " " + coords[1].toPrecision(6)+")"
        var object = ymaps.geoQuery({
            type: 'Point',
            coordinates: [coords[0], coords[1]]
        }).addToMap(myMap)

    });


}

