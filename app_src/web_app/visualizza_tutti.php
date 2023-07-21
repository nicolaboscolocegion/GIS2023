<?php

require_once("sessmgr.php");

?>

<!doctype html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="Ufficio segnalazioni contaminazioni della provincia di Belluno">
  <meta name="author" content="">
  <meta name="generator" content="">
  <meta name="robots" content="noindex">

  <title>Ufficio segnalazioni contaminazioni</title>

  <!-- Bootstrap Italia CSS -->
  <link href="bootstrap-italia/dist/css/bootstrap-italia.min.css" rel="stylesheet">

  <!-- Bootstrap Italia custom CSS -->
  <!-- <link href="/css/compiled/bootstrap-italia-custom.min.css" rel="stylesheet"> -->

  <!-- App styles -->
  <link href="/css/main.css" rel="stylesheet">

  <!-- Favicons -->
  <link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">
<link rel="manifest" href="/site.webmanifest">
<link rel="mask-icon" href="/safari-pinned-tab.svg" color="#5bbad5">
<meta name="msapplication-TileColor" content="#da532c">
<meta name="theme-color" content="#ffffff">

  <!-- Twitter -->
  <meta name="twitter:card" content="summary_large_image">
  <meta name="twitter:site" content="@https://twitter.com/teamdigitaleIT">
  <meta name="twitter:creator" content="Team per la Trasformazione Digitale">
  <meta name="twitter:title" content="Bootstrap Italia">
  <meta name="twitter:description"
    content="Bootstrap Italia è un tema Bootstrap 5 per la creazione di applicazioni web nel pieno rispetto delle Linee guida di design per i servizi web della PA">
  <meta name="twitter:image" content="/img/favicons/social-card.png">

  <!-- Facebook -->
  <meta property="og:url" content="/">
  <meta property="og:title" content="Bootstrap Italia">
  <meta property="og:description"
    content="Bootstrap Italia è un tema Bootstrap 5 per la creazione di applicazioni web nel pieno rispetto delle Linee guida di design per i servizi web della PA">
  <meta property="og:type" content="website">
  <meta property="og:image" content="/img/favicons/social-card.png">
  <meta property="og:image:secure_url" content="/img/favicons/social-card.png">
  <meta property="og:image:type" content="image/png">
  <meta property="og:image:width" content="1200">
  <meta property="og:image:height" content="630">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/ol@v7.4.0/ol.css">
    <style>
     
     #map {
          width: 100%;
          height: 50em;
          /* position: fixed; */
          top: 12em;
          right: 0;
          bottom: 0;
         
      }
        .ol-popup {
          position: absolute;
          background-color: #ffffff;
          box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
          padding: 10px;
          border-radius: 4px;
          border: 1px solid #cccccc;
          font-family: Arial, sans-serif;
          font-size: 14px;
          max-width: 200px;
          white-space: nowrap;
          color: #333333;
        }

        .ol-popup:before {
          content: "";
          position: absolute;
          top: 100%;
          left: 50%;
          margin-left: -8px;
          border-width: 8px;
          border-style: solid;
          border-color: transparent transparent #cccccc transparent;
        }

        .ol-popup:after {
          content: "";
          position: absolute;
          top: 100%;
          left: 50%;
          margin-left: -7px;
          border-width: 7px;
          border-style: solid;
          border-color: transparent transparent #ffffff transparent;
        }

        .ol-popup-closer {
          text-decoration: none;
          position: absolute;
          top: 5px;
          right: 5px;
          font-size: 16px;
          font-weight: bold;
          color: #999999;
          line-height: 1;
        }

        #popup-content {
          margin-bottom: 5px;
        }

        #popup-closer:hover {
          color: #555555;
        }
    </style>

</head>

<body>

  <div class="it-header-wrapper">
    <div class="it-header-slim-wrapper">
      <div class="container">
        <div class="row">
          <div class="col-12">
            <div class="it-header-slim-wrapper-content">
              <a class="d-none d-lg-block navbar-brand" href="https://www.regione.veneto.it/">Regione del Veneto</a>
              <div class="nav-mobile">
                <nav aria-label="Navigazione accessoria">
                  <a class="it-opener d-lg-none" data-bs-toggle="collapse" href="#menu1a" role="button"
                    aria-expanded="false" aria-controls="menu4">
                    <span>Regione del Veneto</span>
                    <svg class="icon" aria-hidden="true">
                      <use href="bootstrap-italia/dist/svg/sprites.svg#it-expand"></use>
                    </svg>
                  </a>
                  <div class="link-list-wrapper collapse" id="menu1a">
                    <ul class="link-list">
                      <li><a class="dropdown-item list-item" href="https://gis2023.bitsei.it">Torna alla Home</a></li>
              
                    </ul>
                  </div>
                </nav>
              </div>
              <div class="it-header-slim-right-zone">
                <div class="nav-item dropdown">
                  <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown" aria-expanded="false">
                    <span class="visually-hidden">Selezione lingua: lingua selezionata</span>
                    <span>ITA</span>
                    <svg class="icon d-none d-lg-block">
                      <use href="bootstrap-italia/dist/svg/sprites.svg#it-expand"></use>
                    </svg>
                  </a>
                  <div class="dropdown-menu">
                    <div class="row">
                      <div class="col-12">
                        <div class="link-list-wrapper">
                          <ul class="link-list">
                            <li><a class="dropdown-item list-item" href="#"><span>ITA <span
                                    class="visually-hidden">selezionata</span></span></a></li>
                            <li><a class="dropdown-item list-item" href="#"><span>ENG</span></a></li>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="it-access-top-wrapper">
                  <a class="btn btn-primary btn-sm" href="logout.php">Logout</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="it-nav-wrapper">
      <div class="it-header-center-wrapper">
        <div class="container">
          <div class="row">
            <div class="col-12">
              <div class="it-header-center-content-wrapper">
                <div class="it-brand-wrapper">
                  <a href="https://www.provincia.belluno.it/home">
                    <img src="araldico.png" style="margin-right:15px; width:5%;">
                    <div class="it-brand-text">
                      <div class="it-brand-title">Ufficio segnalazioni contaminazioni</div>
                      <div class="it-brand-tagline d-none d-md-block">Provincia di Belluno</div>
                    </div>
                  </a>
                </div>
                <div class="it-right-zone">
                  <div class="it-socials d-none d-md-flex">
                    <span>Seguici su</span>
                    <ul>
                      <li>
                        <a href="https://www.facebook.com/ProvinciaDiBelluno" aria-label="Facebook" target="_blank">
                          <svg class="icon">
                            <use href="bootstrap-italia/dist/svg/sprites.svg#it-facebook"></use>
                          </svg>
                        </a>
                      </li>
                      <li>
                        <a href="https://github.com/nicolaboscolocegion/GIS2023" aria-label="Github" target="_blank">
                          <svg class="icon">
                            <use href="bootstrap-italia/dist/svg/sprites.svg#it-github"></use>
                          </svg>
                        </a>
                      </li>
                      <li>
                        <a href="https://twitter.com/ProvinciaBL" aria-label="Twitter" target="_blank">
                          <svg class="icon">
                            <use href="bootstrap-italia/dist/svg/sprites.svg#it-twitter"></use>
                          </svg>
                        </a>
                      </li>
                    </ul>
                  </div>
                  <div class="it-search-wrapper">
                    <span class="d-none d-md-block">Cerca</span>
                    <a class="search-link rounded-icon" aria-label="Cerca nel sito" href="#">
                      <svg class="icon">
                        <use href="bootstrap-italia/dist/svg/sprites.svg#it-search"></use>
                      </svg>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="it-header-navbar-wrapper">
        <div class="container">
          <div class="row">
            <div class="col-12">
              <!--start nav-->
              <!--<nav class="navbar navbar-expand-lg has-megamenu" aria-label="Navigazione principale">
                <button class="custom-navbar-toggler" type="button" aria-controls="nav1" aria-expanded="false"
                  aria-label="Mostra/Nascondi la navigazione" data-bs-toggle="navbarcollapsible" data-bs-target="#nav1">
                  <svg class="icon bg-override">
                    <use href="bootstrap-italia/dist/svg/sprites.svg#it-burger"></use>
                  </svg>
                </button>
                <div class="navbar-collapsable" id="nav1" style="display: none;">
                  <div class="overlay" style="display: none;"></div>
                  <div class="close-div">
                    <button class="btn close-menu" type="button">
                      <span class="visually-hidden">Nascondi la navigazione</span>
                      <svg class="icon">
                        <use href="bootstrap-italia/dist/svg/sprites.svg#it-close-big"></use>
                      </svg>
                    </button>
                  </div>
                  <div class="menu-wrapper">
                    <ul class="navbar-nav">
                      <li class="nav-item active"><a class="nav-link active" href="#" aria-current="page"><span>Link 1
                            (attivo)</span></a></li>
                      <li class="nav-item"><a class="nav-link disabled" href="#" aria-disabled="true"><span>Link 2
                            (disabilitato)</span></a></li>
                      <li class="nav-item"><a class="nav-link" href="#"><span>Link 3</span></a></li>
                      <li class="nav-item"><a class="nav-link" href="#"><span>Link 4</span></a></li>
                      <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown" aria-expanded="false"
                          id="mainNavDropdown1">
                          <span>Menu Dropdown</span>
                          <svg class="icon icon-xs">
                            <use href="bootstrap-italia/dist/svg/sprites.svg#it-expand"></use>
                          </svg>
                        </a>
                        <div class="dropdown-menu" role="region" aria-labelledby="mainNavDropdown1">
                          <div class="link-list-wrapper">
                            <div class="link-list-heading">Sezione</div>
                            <ul class="link-list">
                              <li><a class="dropdown-item list-item" href="#"><span>Link lista 1</span></a></li>
                              <li><a class="dropdown-item list-item" href="#"><span>Link lista 2</span></a></li>
                              <li><a class="dropdown-item list-item" href="#"><span>Link lista 3</span></a></li>
                              <li><span class="divider"></span></li>
                              <li><a class="dropdown-item list-item" href="#"><span>Link lista 4</span></a></li>
                            </ul>
                          </div>
                        </div>
                      </li>
                      <li class="nav-item dropdown megamenu">
                        <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown" aria-expanded="false"
                          id="mainNavMegamenu1">
                          <span>Megamenu</span>
                          <svg class="icon icon-xs">
                            <use href="bootstrap-italia/dist/svg/sprites.svg#it-expand"></use>
                          </svg>
                        </a>
                        <div class="dropdown-menu" role="region" aria-labelledby="mainNavMegamenu1">
                          <div class="row">
                            <div class="col-12 col-lg-4">
                              <div class="link-list-wrapper">
                                <div class="link-list-heading">Sezione 1</div>
                                <ul class="link-list">
                                  <li><a class="dropdown-item list-item" href="#"><span>Link lista 1</span></a></li>
                                  <li><a class="dropdown-item list-item" href="#"><span>Link lista 2</span></a></li>
                                  <li><a class="dropdown-item list-item" href="#"><span>Link lista 3</span></a></li>
                                </ul>
                              </div>
                            </div>
                            <div class="col-12 col-lg-4">
                              <div class="link-list-wrapper">
                                <ul class="link-list">
                                  <li>
                                    <div class="link-list-heading">Sezione 2</div>
                                  </li>
                                  <li><a class="dropdown-item list-item" href="#"><span>Link lista 4</span></a></li>
                                  <li><a class="dropdown-item list-item" href="#"><span>Link lista 5</span></a></li>
                                  <li><a class="dropdown-item list-item" href="#"><span>Link lista 6</span></a></li>
                                </ul>
                              </div>
                            </div>
                            <div class="col-12 col-lg-4">
                              <div class="link-list-wrapper">
                                <ul class="link-list">
                                  <li>
                                    <div class="link-list-heading">Sezione 3</div>
                                  </li>
                                  <li><a class="dropdown-item list-item" href="#"><span>Link lista 7</span></a></li>
                                  <li><a class="dropdown-item list-item" href="#"><span>Link lista 8</span></a></li>
                                  <li><a class="dropdown-item list-item" href="#"><span>Link lista 9</span></a></li>
                                </ul>
                              </div>
                            </div>
                          </div>
                        </div>
                      </li>
                    </ul>
                  </div>-->
                </div>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="container my-4">
    
    <form method="POST" action="visualizza_tutti.php">
    <div class="select-wrapper">
    <label for="defaultSelectDisabled">Seleziona anno</label>
    <select id="defaultSelectDisabled" name="anno" onchange="this.form.submit();">
    <?php
    
    require_once("conndb.php");

    $result = pg_query($conn, "select distinct extract(year from data_report) as anno FROM reports;");
    while ($arr = pg_fetch_array($result)) {
      if (isset($_POST["anno"]) && $_POST["anno"] == $arr[0]) {
        echo "<option value=\"" . $arr[0] . "\" selected>" . $arr[0] . "</option>";
      }
      else if (!isset($_POST["anno"]) && $arr[0] == date("Y")) {
        echo "<option value=\"" . $arr[0] . "\" selected>" . $arr[0] . "</option>";
      }
      else {
        echo "<option value=\"" . $arr[0] . "\">" . $arr[0] . "</option>";
      }
    }
    
    ?>
    </select>
  </div>

    </form>

  </div>
  
  <div>
  <?php
    
    require_once("conndb.php");

    //$latitudes = "[";
    //$longitudes = "[";
    $i = 1;
    $sql = "";
    if (isset($_POST["anno"])) {
      $sql = "SELECT id_report, data_report, ST_AsText (position) as pos, pollutant, report_description, nome_file, elevation FROM reports WHERE extract(year from data_report) = " . $_POST["anno"] . ";";
    }
    else {
      $sql = "SELECT id_report, data_report, ST_AsText (position) as pos, pollutant, report_description, nome_file, elevation FROM reports WHERE extract(year from data_report) = " . date("Y") . ";";
    }
    $result = pg_query($conn, $sql);
    while ($arr = pg_fetch_array($result)) {

        $wft = substr($arr[2], 6);
        $coordinates = explode(" ", substr($wft, 0, strlen($wft)-1));

        $latitudes .= $coordinates[0] . ",";
        $longitudes .= $coordinates[1] . ",";
    }

    $latitudes = substr($latitudes, 0, strlen($latitudes)-1);
    $longitudes = substr($longitudes, 0, strlen($longitudes)-1);
    
    //$latitudes .= "]";
    //$longitudes .= "]";

    echo "<input type=\"hidden\" id=\"lat\" value=\"" . $latitudes . "\">";
    echo "<input type=\"hidden\" id=\"long\" value=\"" . $longitudes . "\">";
    ?>
  
    <div id="map"></div>
    <div id="popup" class="ol-popup">
        <a href="#" id="popup-closer" class="ol-popup-closer"></a>
        <div id="popup-content"></div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/ol@v7.4.0/dist/ol.js"></script>
    
  </div>

    
  </div>  
    

  <footer class="it-footer">
    <div class="it-footer-main">
      <div class="container">
        <section>
          <div class="row clearfix">
            <div class="col-sm-12">
              <div class="it-brand-wrapper">
                <a href="https://www.provincia.belluno.it/home">
                  <img src="araldico.png" style="margin-right:15px; width:3%;">
                  <div class="it-brand-text">
                    <h2 class="no_toc">Provincia di Belluno</h2>
                    <h3 class="no_toc d-none d-md-block">Regione del Veneto</h3>
                  </div>
                </a>
              </div>
            </div>
          </div>
        </section>
        <section>
          <div class="row">
            <div class="col-lg-3 col-md-3 col-sm-6 pb-2">
              <h4>
                <a href="#" title="Vai alla pagina: Amministrazione">Amministrazione</a>
              </h4>
              <div class="link-list-wrapper">
                <ul class="footer-list link-list clearfix">
                  <li><a class="list-item" href="#" title="Vai alla pagina: Giunta e consiglio">Giunta e consiglio</a>
                  </li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Aree di competenza">Aree di competenza</a>
                  </li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Dipendenti">Dipendenti</a></li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Luoghi">Luoghi</a></li>
                  <li><a class="list-item" href="#"
                      title="Vai alla pagina: Associazioni e società partecipate">Associazioni e società partecipate</a>
                  </li>
                </ul>
              </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-6 pb-2">
              <h4>
                <a href="#" title="Vai alla pagina: Servizi">Servizi</a>
              </h4>
              <div class="link-list-wrapper">
                <ul class="footer-list link-list clearfix">
                  <li><a class="list-item" href="#" title="Vai alla pagina: Pagamenti">Pagamenti</a></li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Sostegno">Sostegno</a></li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Domande e iscrizioni">Domande e
                      iscrizioni</a></li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Segnalazioni">Segnalazioni</a></li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Autorizzazioni e concessioni">Autorizzazioni
                      e concessioni</a></li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Certificati e dichiarazioni">Certificati e
                      dichiarazioni</a></li>
                </ul>
              </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-6 pb-2">
              <h4>
                <a href="#" title="Vai alla pagina: Novità">Novità</a>
              </h4>
              <div class="link-list-wrapper">
                <ul class="footer-list link-list clearfix">
                  <li><a class="list-item" href="#" title="Vai alla pagina: Notizie">Notizie</a></li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Eventi">Eventi</a></li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Comunicati stampa">Comunicati stampa</a>
                  </li>
                </ul>
              </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-6">
              <h4>
                <a href="#" title="Vai alla pagina: Documenti">Documenti</a>
              </h4>
              <div class="link-list-wrapper">
                <ul class="footer-list link-list clearfix">
                  <li><a class="list-item" href="#" title="Vai alla pagina: Progetti e attività">Progetti e attività</a>
                  </li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Delibere, determine e ordinanze">Delibere,
                      determine e ordinanze</a></li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Bandi">Bandi</a></li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Concorsi">Concorsi</a></li>
                  <li><a class="list-item" href="#" title="Vai alla pagina: Albo pretorio">Albo pretorio</a></li>
                </ul>
              </div>
            </div>
          </div>
        </section>
        <section class="py-4 border-white border-top">
          <div class="row">
            <div class="col-lg-4 col-md-4 pb-2">
              <h4><a href="#" title="Vai alla pagina: Contatti">Contatti</a></h4>
              <p>
                <strong>Provincia di Belluno</strong><br />
                Via S. Andrea, 5 - 32100 Belluno (BL) C.F. - 93005430256 - P.IVA - 00847010253
              </p>
              <div class="link-list-wrapper">
                <ul class="footer-list link-list clearfix">
                  <li><a class="list-item" href="#" title="Vai alla pagina: Posta Elettronica Certificata">Posta
                      Elettronica Certificata</a></li>
                  <li>
                    <a class="list-item" href="#" title="Vai alla pagina: URP - Ufficio Relazioni con il Pubblico">URP -
                      Ufficio Relazioni con il Pubblico</a>
                  </li>
                </ul>
              </div>
            </div>
            <div class="col-lg-4 col-md-4 pb-2">
              <h4><a href="https://www.provincia.belluno.it/home" title="Vai alla pagina: Provincia di Belluno">Provincia di Belluno</a></h4>
            </div>
            <div class="col-lg-4 col-md-4 pb-2">
              <div class="pb-2">
                <h4><a href="#" title="Vai alla pagina: Seguici su">Seguici su</a></h4>
                <ul class="list-inline text-left social">
                  <li class="list-inline-item">
                    <a class="p-2 text-white" href="#" target="_blank"><svg class="icon icon-sm icon-white align-top">
                        <use xlink:href="bootstrap-italia/dist/svg/sprites.svg#it-designers-italia"></use>
                      </svg><span class="visually-hidden">Designers Italia</span></a>
                  </li>
                  <li class="list-inline-item">
                    <a class="p-2 text-white" href="#" target="_blank"><svg class="icon icon-sm icon-white align-top">
                        <use xlink:href="bootstrap-italia/dist/svg/sprites.svg#it-twitter"></use>
                      </svg><span class="visually-hidden">Twitter</span></a>
                  </li>
                  <li class="list-inline-item">
                    <a class="p-2 text-white" href="#" target="_blank"><svg class="icon icon-sm icon-white align-top">
                        <use xlink:href="bootstrap-italia/dist/svg/sprites.svg#it-medium"></use>
                      </svg><span class="visually-hidden">Medium</span></a>
                  </li>
                  <li class="list-inline-item">
                    <a class="p-2 text-white" href="#" target="_blank"><svg class="icon icon-sm icon-white align-top">
                        <use xlink:href="bootstrap-italia/dist/svg/sprites.svg#it-behance"></use>
                      </svg><span class="visually-hidden">Behance</span></a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
    <div class="it-footer-small-prints clearfix">
      <div class="container">
        <h3 class="visually-hidden">Sezione Link Utili</h3>
        <ul class="it-footer-small-prints-list list-inline mb-0 d-flex flex-column flex-md-row">
          <li class="list-inline-item"><a href="#" title="Note Legali">Media policy</a></li>
          <li class="list-inline-item"><a href="#" title="Note Legali">Note legali</a></li>
          <li class="list-inline-item"><a href="#" title="Privacy-Cookies">Privacy policy</a></li>
          <li class="list-inline-item"><a href="#" title="Mappa del sito">Mappa del sito</a></li>
        </ul>
      </div>
    </div>
  </footer>


  <!-- window.__PUBLIC_PATH__ points to fonts folder location -->
  <script>window.__PUBLIC_PATH__ = 'bootstrap-italia/dist/fonts'</script>

  <script src="bootstrap-italia/dist/js/bootstrap-italia.bundle.min.js"></script>

  <!-- alternatively, just use the bundled version of the library -->
  <!-- <script src="./bootstrap-italia/dist/js/bootstrap-italia.bundle.min.js"></script>-->

  <!-- App scripts -->
  <script src="js/all_visualizer.js"></script>


</body>

</html>
