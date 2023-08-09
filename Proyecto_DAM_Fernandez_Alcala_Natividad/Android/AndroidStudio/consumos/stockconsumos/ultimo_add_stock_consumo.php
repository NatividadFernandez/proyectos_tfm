<?php 
// Conexion a la base de datos
require '../../conexion.php';

// Consulta que devuelve el ultimo stock de consumo ordenador por id
$consulta = $bd->query("SELECT * FROM stock_consumos ORDER BY id DESC LIMIT 1");

if(!$consulta){
    printf("Error: %s\n", $bd->error); 
    exit();
} else {
    if($row = $consulta->fetch_assoc()){

        echo json_encode($row,JSON_UNESCAPED_UNICODE);
    }
}



?>