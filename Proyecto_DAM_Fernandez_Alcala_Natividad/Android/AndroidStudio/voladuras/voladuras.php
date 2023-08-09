<?php 

//Conexión a la base de datos 
require '../conexion.php';

//Consulta que devuelve todas las voladuras ordenadar por fecha
$consulta = $bd->query("SELECT * FROM voladuras ORDER BY fecha_voladura DESC");
$voladuras = array();

if(!$consulta){
    printf("Error: %s\n", $bd->error); 
    exit();
} else {
    // Añadimos el resultado de la consulta a un array
    while($row = $consulta->fetch_assoc()){

        $voladuras[] = $row;
    }
}

echo json_encode($voladuras,JSON_UNESCAPED_UNICODE);

?>