<?php

   
    $servername = "localhost";
    $username = "postgres";
    $password = "***REMOVED***";
    $dbname = "gis2023";


    $conn = pg_connect("host=" . $servername . " port=5432 dbname=" . $dbname . " user=" . $username . " password=" . $password);

?>