<?php

    if(session_id() == '' || !isset($_SESSION)) {
        session_start();
    }

    if(!isset($_SESSION["user"])) {
        
        header("Location: login.php?auth=3");
    }    


?>