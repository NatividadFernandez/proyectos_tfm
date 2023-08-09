<?php 
// Conexion a la base de datos
require '../../conexion.php';

// Valores obtenidos por post
$id = intval($_POST['id']);
$resultado = array();

// Consulta que comprueba si el stock de consumo con id x existe
$consulta = $bd->query("SELECT * FROM stock_consumos WHERE id=$id");

if(!$consulta){
    printf("Error: %s\n", $bd->error); 
    exit();
} else {
    // Eliminamos el stock del consumo
    $consulta2 = $bd->query("DELETE FROM stock_consumos WHERE id=$id");

    if(!$consulta2){
        printf("Error: %s\n", $bd->error); 
        exit();
    } else {
        $resultado['estado'] = "borrar";
        echo json_encode($resultado,JSON_UNESCAPED_UNICODE);
    }
}


?>