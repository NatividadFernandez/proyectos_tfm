<?php 
// Conectar a la base de datos
require '../conexion.php';

// Consulta que devuelve todos los productos 
$consulta = $bd->query("SELECT * FROM productos ORDER BY id DESC");
$productos = array();

if(!$consulta){
    printf("Error: %s\n", $bd->error); 
    exit();
} else {
    while($row = $consulta->fetch_assoc()){

        $productos[] = $row;
    }
}

echo json_encode($productos,JSON_UNESCAPED_UNICODE);
?>