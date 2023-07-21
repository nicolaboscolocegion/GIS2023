<?php

    session_start();
    

    require_once("conndb.php");
    

    
    $sql = "SELECT id_user, users.user, pass, nome, cognome FROM users;";
    $result = pg_query($conn, $sql);

    $flag = 0;

   
        
    while($row = pg_fetch_array($result)) {
        if ($row['user'] == $_POST['user'] && $row['pass'] == hash("sha512", $_POST['pass'])) {
            $_SESSION["id_utente"] = $row['id_utente'];
            $_SESSION["user"] = $row["user"];
            $_SESSION["nome"] = $row["nome"];
            $_SESSION["cognome"] = $row["cognome"];
            header("Location: visualizza_report.php");
            $flag = 1;
        }
        else if ($row['user'] == $_POST['user'] && $row['pass'] != $_POST['pass']) {
            $_POST['auth'] = 0;
            require_once "login.php"; //PASSWORD ERRATA
        }
    }

    if ($flag == 0) {
        $_POST['auth'] = 1;
        require_once "login.php"; //UTENTE NON TROVATO
    }

    
?>