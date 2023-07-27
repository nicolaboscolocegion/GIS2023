var mapView = new ol.View ({
    center: ol.proj.fromLonLat([12.123605, 46.472403]),
    zoom:10
});

var map = new ol.Map ({
    target: 'map',
    view: mapView
});

var osmTile = new ol.layer.Tile ({
    title: 'Open Street Map',
    visible: true,
    source: new ol.source.OSM()
})

map.addLayer(osmTile);

var BellunoFiumi = new ol.layer.Tile({
    title: "Fiumi di Belluno",
    source: new ol.source.TileWMS({
        url: 'https://gis2023.bitsei.it/geoserver/GIS_2023/wms',
        params: {'LAYERS':'GIS_2023:bellunofiumi', 'TILED': true},
        serverType: 'geoserver',
        visible: true
    })
});

var BellunoLaghi = new ol.layer.Tile({
    title: "Laghi di Belluno",
    source: new ol.source.TileWMS({
        //url: 'http://89.40.142.15:8888/geoserver/GIS_2023/wms',
        url: 'https://gis2023.bitsei.it/geoserver/GIS_2023/wms',
        params: {'LAYERS':'GIS_2023:bellunolaghi', 'TILED': true},
        serverType: 'geoserver',
        visible: true
    })
});



map.addLayer(BellunoLaghi)
map.addLayer(BellunoFiumi)

 




document.addEventListener("DOMContentLoaded",  function (event) {
    latitudes = document.getElementById("lat").value;
    longitudes = document.getElementById("long").value;

    const arr1 = latitudes.split(",");
    const arr2 = longitudes.split(",");

    let i = 0;

    while (i < arr1.length) {

        var markerLayer = new ol.layer.Vector({
            source: new ol.source.Vector(),
        });
        
        map.addLayer(markerLayer);
        
        var markerFeature;
        
        var markerStyle = new ol.style.Style({
        image: new ol.style.Icon({
            src: 'location-pin.png', // Path to your custom marker icon
            scale: 0.8, // Adjust the size of the marker icon as needed
            offset: [0.5, 1], // Adjust the offset to align the marker correctly
            anchor: [0.5, 1], // Set the anchor point to the bottom center of the marker icon
        }),
        });

        var clickedCoordinate = [arr1[i], arr2[i]];

        if (markerFeature) {
            markerLayer.getSource().removeFeature(markerFeature);
        }

        markerFeature = new ol.Feature({
            geometry: new ol.geom.Point(clickedCoordinate),
        });

        markerFeature.setStyle(markerStyle);

        markerLayer.getSource().addFeature(markerFeature);

        i++;
    }

    /**/
});