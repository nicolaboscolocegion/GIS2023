<?php

    
    require_once("sessmgr.php");
    require_once("conndb.php");
    $result = NULL;


    include("vendor/autoload.php");

    use proj4php\Proj4php;
    use proj4php\Proj;
    use proj4php\Point;

    // Initialise Proj4
    $proj4 = new Proj4php();

    // Create two different projections.
    $projL93    = new Proj('EPSG:3857', $proj4);
    $projWGS84  = new Proj('EPSG:4326', $proj4);

    // Create a point.
    $pointSrc = new Point($_POST["latitude"], $_POST["longitude"], $projL93);

    // Transform the point between datums.
    $pointDest = $proj4->transform($projWGS84, $pointSrc);
    //echo "Conversion: " . $pointDest->toShortString() . " in WGS84<br><br>";

    $newcoords = explode(' ', $pointDest->toShortString());
    $newlat = $newcoords[0];
    $newlon = $newcoords[1];

    $url = "https://api.opentopodata.org/v1/eudem25m?locations=" . $newlon . "," . $newlat;
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

    $output = curl_exec($ch);
    $res = json_decode( $output );

    $elevation = $res->results[0]->elevation;



    if (isset($_POST["inputUlterioriInfo"]) && $_POST["inputUlterioriInfo"] != "" && $_POST["inputUlterioriInfo"] != NULL) {
        $result = pg_query($conn, "INSERT INTO reports (data_report, position, pollutant, report_description, elevation) VALUES ('" . date("Y-m-d") . "', ST_GeomFromText('POINT(" . $_POST["latitude"] . " " . $_POST["longitude"] . ")', 3857), '" . $_POST["inputInquinante"] . "', '" . $_POST["inputUlterioriInfo"] . "', " . $elevation . ") RETURNING id_report;");
    }
    else {
        $result = pg_query($conn, "INSERT INTO reports (data_report, position, pollutant, elevation) VALUES ('" . date("Y-m-d") . "', ST_GeomFromText('POINT(" . $_POST["latitude"] . " " . $_POST["longitude"] . ")', 3857), '" . $_POST["inputInquinante"] . "', " . $elevation . ") RETURNING id_report;");
    }


    $arr = pg_fetch_array($result);
    $oid = $arr[0];
    //$oid = pg_last_oid($result);


    if (is_uploaded_file($_FILES["upload_immagine"]["tmp_name"])) {
        $info = pathinfo($_FILES['upload_immagine']['name']);
        $ext = $info['extension']; // get the extension of the file
        $newname = "REPORT_" . $oid . "." . $ext; 
        $target = 'images/' . $newname;
        move_uploaded_file($_FILES["upload_immagine"]["tmp_name"], $target);
        $result = pg_query($conn, "UPDATE reports SET nome_file = '" . $newname . "' WHERE id_report = " . $oid . ";");
    }

    header("Location: https://gis2023.bitsei.it?success=1");
?>