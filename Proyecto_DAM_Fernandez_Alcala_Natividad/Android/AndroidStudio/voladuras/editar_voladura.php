<?php
// Conexión a la base de datos
require '../conexion.php';
require '../funciones/funciones.php';

// Obtenemos valores por port
$id = intval($_POST['id']);
$localizacion = $_POST['localizacion'];
$m2_superficie = $_POST['m2_superficie'];
$malla_perforacion = $_POST['malla_perforacion'];
$profundidad_barrenos = $_POST['profundidad_barrenos'];
$numero_barrenos = $_POST['numero_barrenos'];
$kg_explosivo = $_POST['kg_explosivo'];
$precio = $_POST['precio'];
$piedra_bruta = $_POST['piedra_bruta'];
$id_empleado = intval($_POST['id_empleado']);
$fecha_voladura = date('Y-m-d H:i:s',strtotime($_POST['fecha_voladura']));

// Comprobamos si algun valor es incorrecto
if(comprobarNumero($m2_superficie) && comprobarNumero($profundidad_barrenos) && comprobarNumero($numero_barrenos) &&
    comprobarNumero($kg_explosivo) && comprobarNumero($precio) && comprobarNumero($piedra_bruta)){  

    // Actualizamos la voladura
    $consulta = $bd->query("UPDATE voladuras SET localizacion = '$localizacion', m2_superficie = '$m2_superficie', malla_perforacion = '$malla_perforacion',
    profundidad_barrenos = '$profundidad_barrenos', numero_barrenos = '$numero_barrenos', kg_explosivo = '$kg_explosivo', precio = '$precio', piedra_bruta = '$piedra_bruta',
    fecha_voladura = '$fecha_voladura', id_empleado='$id_empleado' WHERE id='$id'");
    
    if(!$consulta){
        printf("Error: %s\n", $bd->error); 
        exit();
    } else {
        echo 'Actualizado con éxito';
    }                          

} else {
    echo 'Los datos introducidos deben de ser números';
}




?>