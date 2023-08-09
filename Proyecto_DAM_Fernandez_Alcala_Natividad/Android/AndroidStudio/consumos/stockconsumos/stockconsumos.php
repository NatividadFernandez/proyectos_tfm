<?php 
// Conexion a la base de datos
require '../../conexion.php';

// Consulta que devuelve todo el stock de consumos ordenador por id
$consulta = $bd->query("SELECT * FROM stock_consumos ORDER BY id DESC");
$consumos = array();

if(!$consulta){
    printf("Error: %s\n", $bd->error); 
    exit();
} else {
    while($row = $consulta->fetch_assoc()){
        // array de stock de consumo
        $consumos[] = $row;
    }
}

echo json_encode($consumos,JSON_UNESCAPED_UNICODE);
?>