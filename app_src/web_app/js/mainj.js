var mapView = new ol.View ({
    center: ol.proj.fromLonLat([12.223605, 46.472403]),
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

 


var markerLayer = new ol.layer.Vector({
  source: new ol.source.Vector(),
});

map.addLayer(markerLayer);

var markerFeature;


map.on('click', function (event) {
  var clickedCoordinate = event.coordinate;

  // Remove the previous marker if it exists
  if (markerFeature) {
    markerLayer.getSource().removeFeature(markerFeature);
  }

  // Create a new marker feature at the selected position
  markerFeature = new ol.Feature({
    geometry: new ol.geom.Point(clickedCoordinate),
  });

  // Add the marker feature to the marker layer
  markerLayer.getSource().addFeature(markerFeature);
});

var markerStyle = new ol.style.Style({
  image: new ol.style.Icon({
    src: 'location-pin.png', // Path to your custom marker icon
    scale: 0.8, // Adjust the size of the marker icon as needed
    offset: [0.5, 1], // Adjust the offset to align the marker correctly
    anchor: [0.5, 1], // Set the anchor point to the bottom center of the marker icon
  }),
});

//ol.proj.transform([23.4, 42.5], 'EPSG:4326','EPSG:5514');

map.on('click', function (event) {
  var clickedCoordinate = event.coordinate;

  if (markerFeature) {
    markerLayer.getSource().removeFeature(markerFeature);
  }

  markerFeature = new ol.Feature({
    geometry: new ol.geom.Point(clickedCoordinate),
  });

  markerFeature.setStyle(markerStyle); // Apply the custom style to the marker feature

  markerLayer.getSource().addFeature(markerFeature);
});

var popup = new ol.Overlay({
  element: document.getElementById('popup'), // ID of the popup element in your HTML
  positioning: 'bottom-center',
  stopEvent: false,
  offset: [0, -10], // Adjust the offset to position the popup relative to the marker
});
map.addOverlay(popup);

map.on('click', function (event) {
  var clickedCoordinate = event.coordinate;

  if (markerFeature) {
    markerLayer.getSource().removeFeature(markerFeature);
  }

  markerFeature = new ol.Feature({
    geometry: new ol.geom.Point(clickedCoordinate),
  });

  markerFeature.setStyle(markerStyle);

  markerLayer.getSource().addFeature(markerFeature);

  // Update the content of the popup with the selected coordinates
  var popupContent = document.getElementById('popup-content');
  popupContent.innerHTML = 'Selected Coordinates: ' + clickedCoordinate;
  //const words = clickedCoordinate.split(',');
  document.getElementById("latitude").value = clickedCoordinate[0];
  document.getElementById("longitude").value = clickedCoordinate[1];

  // Position the popup relative to the marker's position
  popup.setPosition(clickedCoordinate);
  popupContent.style.display = 'block';
  popupContent.style.width = '32em';
});




function fromDeviceCoord(lat, long) {


  var clickedCoordinate = ol.proj.transform([long,lat], 'EPSG:4326','EPSG:3857');

  //var clickedCoordinate = String(arr1[1]).concat(',',String(arr1[0]));

  if (markerFeature) {
    markerLayer.getSource().removeFeature(markerFeature);
  }

  markerFeature = new ol.Feature({
    geometry: new ol.geom.Point(clickedCoordinate),
  });

  markerFeature.setStyle(markerStyle);

  markerLayer.getSource().addFeature(markerFeature);

  // Update the content of the popup with the selected coordinates
  var popupContent = document.getElementById('popup-content');
  popupContent.innerHTML = 'Selected Coordinates: ' + clickedCoordinate;
  //const words = clickedCoordinate.split(',');
  document.getElementById("latitude").value = clickedCoordinate[0];
  document.getElementById("longitude").value = clickedCoordinate[1];

  // Position the popup relative to the marker's position
  popup.setPosition(clickedCoordinate);
  popupContent.style.display = 'block';
  popupContent.style.width = '32em';

}