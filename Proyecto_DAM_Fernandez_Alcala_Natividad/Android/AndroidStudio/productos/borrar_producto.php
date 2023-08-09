<?php 
// Conexion a la base de datos
require '../conexion.php';

// Valores obtenidos por post
$id = intval($_POST['id']);
$resultado = array();

// Consultamos si existe el producto
$consulta = $bd->query("SELECT * FROM productos WHERE id=$id");

if(!$consulta){
    printf("Error: %s\n", $bd->error); 
    exit();
} else {
    // Eliminamos el producto
    $consulta2 = $bd->query("DELETE FROM productos WHERE id=$id");
    
    if(!$consulta2){
        printf("Error: %s\n", $bd->error); 
        exit();
    } else {
        $resultado['estado'] = "borrar";
        echo json_encode($resultado,JSON_UNESCAPED_UNICODE);
    }
}


?>