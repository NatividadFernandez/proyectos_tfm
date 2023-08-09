<?php 
// Conexion a la base de datos
require '../../conexion.php';

// Valores obtenidos por post
$id = intval($_POST['id']);
$resultado = array();

// Consultamos si el stock de x id existe
$consulta = $bd->query("SELECT * FROM stock_productos WHERE id=$id");

if(!$consulta){
    printf("Error: %s\n", $bd->error); 
    exit();
} else {
    // Eliminamos el stock del producto
    $consulta2 = $bd->query("DELETE FROM stock_productos WHERE id=$id");
    
    if(!$consulta2){
        printf("Error: %s\n", $bd->error); 
        exit();
    } else {
        $resultado['estado'] = "borrar";
        echo json_encode($resultado,JSON_UNESCAPED_UNICODE);
    }
}


?>