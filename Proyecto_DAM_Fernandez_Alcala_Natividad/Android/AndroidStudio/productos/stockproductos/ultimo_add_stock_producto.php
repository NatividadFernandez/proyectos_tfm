<?php 
// Conexion de base de datos
require '../../conexion.php';

// Consulta que devuelve el ultimo stock de producto
$consulta = $bd->query("SELECT * FROM stock_productos ORDER BY id DESC LIMIT 1");
$productos = array();

if(!$consulta){
    printf("Error: %s\n", $bd->error); 
    exit();
} else {
    if($row = $consulta->fetch_assoc()){
        echo json_encode($row,JSON_UNESCAPED_UNICODE);
    }
}



?>