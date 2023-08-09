<?php
// Conexion a la base de datos
require '../conexion.php';

// Establecemos la zona horaria
date_default_timezone_set('Europe/Madrid');

// Obtenemos la fecha de hoy
$fechaLocal = strval(date('Y-m-d'));

// Valores obtenidos por post
$fechaSalida = date('Y-m-d H:i:s',strtotime($_POST['fecha_check_out']));
$id_empleado = intval($_POST['id_empleado']);

// Consulta para comprobar si el check in está hecho para poder hacer el check out
$consulta = $bd->query("SELECT * FROM presencialidads WHERE id_empleado=$id_empleado AND fecha='$fechaLocal'"); 

if (mysqli_num_rows($consulta) > 0) {   
    // Realizamos el check out
    $consulta = $bd->query("UPDATE presencialidads SET fecha_check_out = '$fechaSalida' WHERE id_empleado='$id_empleado' AND fecha ='$fechaLocal'"); 

    if(!$consulta){
        printf("Error: %s\n", $bd->error); 
        exit();
    } else {
        $resultado["estado"] = "checkout";
        echo json_encode($resultado,JSON_UNESCAPED_UNICODE);   
    }   
} 

    











?>