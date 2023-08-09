<?php 
// Conexion de base de datos
require '../conexion.php';

// Consulta que devuelve todos los consumos orenador por fecha
$consulta = $bd->query("SELECT * FROM consumos ORDER BY fecha_recepcion DESC");
$consumos = array();

if(!$consulta){
    printf("Error: %s\n", $bd->error); 
    exit();
} else {
    while($row = $consulta->fetch_assoc()){
        // array de consumos
        $consumos[] = $row;
    }
}

echo json_encode($consumos,JSON_UNESCAPED_UNICODE);
?>