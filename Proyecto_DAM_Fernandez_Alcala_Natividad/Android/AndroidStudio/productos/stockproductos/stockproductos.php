<?php 
// Conexion a la base de datos
require '../../conexion.php';

// Consulta que devuelve el stock de productos ordenados por id
$consulta = $bd->query("SELECT * FROM stock_productos ORDER BY id DESC");
$productos = array();

if(!$consulta){
    printf("Error: %s\n", $bd->error); 
    exit();
} else {
    while($row = $consulta->fetch_assoc()){
        // Array de stock de productos
        $productos[] = $row;
    }
}

echo json_encode($productos,JSON_UNESCAPED_UNICODE);

?>