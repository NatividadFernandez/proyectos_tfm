<?php 
// Conexión a la base de datos
require '../conexion.php';

$id = intval($_POST['id']);
$resultado = array();

// Consultamos si existe la voladura
$consulta = $bd->query("SELECT * FROM voladuras WHERE id=$id");

if(!$consulta){
    printf("Error: %s\n", $bd->error); 
    exit();
} else {
    // Eliminamos la voladura
    $consulta2 = $bd->query("DELETE FROM voladuras WHERE id=$id");
    if(!$consulta2){
        printf("Error: %s\n", $bd->error); 
        exit();
    } else {
        $resultado['estado'] = "borrar";
        echo json_encode($resultado,JSON_UNESCAPED_UNICODE);
    }
}


?>