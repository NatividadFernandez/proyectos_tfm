<?php
// Conexión a la base de datos
require '../conexion.php';
require '../funciones/funciones.php';

// valores obtenidos por post
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

    // Insertamos una nueva voladura
    $consulta = $bd->query("INSERT INTO voladuras (localizacion,m2_superficie,malla_perforacion,profundidad_barrenos,numero_barrenos,kg_explosivo,precio,piedra_bruta,fecha_voladura,id_empleado) VALUES ('$localizacion','$m2_superficie','$malla_perforacion','$profundidad_barrenos','$numero_barrenos','$kg_explosivo','$precio','$piedra_bruta','$fecha_voladura','$id_empleado')"); 

    if(!$consulta){
        printf("Error: %s\n", $bd->error); 
        exit();
    } else {
        echo 'Voladura insertada con éxito';
    }                           

} else {
    echo 'Los datos introducidos deben de ser números';
}

?>