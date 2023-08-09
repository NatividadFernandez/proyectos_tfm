<?php
// Conexion a la base de datos
require '../conexion.php';

// Establecemos la zona horaria
date_default_timezone_set('Europe/Madrid');

// Obtenemos la fecha de hoy
$fechaLocal = strval(date('Y-m-d'));

// Valores obtenidos por post
$fechaEntrada = date('Y-m-d H:i:s',strtotime($_POST['fecha_check_in']));
$id_empleado = intval($_POST['id_empleado']);

// Consulta para comprobar si el check in está hecho
$consulta = $bd->query("SELECT * FROM presencialidads WHERE id_empleado=$id_empleado AND fecha='$fechaLocal'"); 

if (mysqli_num_rows($consulta) == 0) {  
    // Realizamos el check in 
    $consulta = $bd->query("INSERT INTO presencialidads (id_empleado,fecha,fecha_check_in) VALUES ($id_empleado,'$fechaLocal','$fechaEntrada')"); 
    
    if(!$consulta){
        printf("Error: %s\n", $bd->error); 
        exit();
    } else {
        $resultado["estado"] = "checkin";
        echo json_encode($resultado,JSON_UNESCAPED_UNICODE);     
    }      
    
}
?>