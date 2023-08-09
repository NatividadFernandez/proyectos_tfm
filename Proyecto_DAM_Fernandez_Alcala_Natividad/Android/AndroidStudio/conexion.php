<?php
    $sql_host="localhost";
    $sql_usuario="root";
    $sql_pass="";
    $sql_db="cantera";

    $bd = new mysqli($sql_host, $sql_usuario, $sql_pass, $sql_db);
    if ($bd->connect_error) { die('Error de Conexión ('.$mysqli->connect_errno.') '.$mysqli->connect_error); }
    mysqli_set_charset($bd,"utf8");

?>