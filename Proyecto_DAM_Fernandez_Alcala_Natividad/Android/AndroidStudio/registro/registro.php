<?php
// Conexion a la base de datos  
require '../conexion.php';

// Establecemos la zona horaria
date_default_timezone_set('Europe/Madrid');

//echo date('l jS \of F Y H:i:s A');
//echo date('d/m/Y H:i:s');
//echo date('Y-m-d');

// Obtenemos la fecha de hoy
$fechaLocal = strval(date('Y-m-d'));

$resultado = array();

$fechaEntrada = "";
$fechaSalida = "";

// Valores obtenidos por post
$fechaEntrada = isset($_POST['fecha_check_in'])? intval($_POST['fecha_check_in']): '';
$fechaSalida = isset($_POST['fecha_check_out'])? intval($_POST['fecha_check_out']): '';
$id_empleado = intval($_POST['id_empleado']);

// Consulta para saber si el usuario ya he realizado el check in
$consultaE = $bd->query("SELECT * FROM presencialidads WHERE id_empleado=$id_empleado AND fecha='$fechaLocal'"); 

// Devolveremos un estado según si el usuario ha relizado el check in o check out
if (mysqli_num_rows($consultaE) > 0) {    

    if($row = $consultaE->fetch_assoc()){
        $fechaSalida = $row['fecha_check_out'];
    }

    if($fechaSalida != ''){
        $resultado["estado"] = "checkout";
        echo json_encode($resultado,JSON_UNESCAPED_UNICODE);  

    } else {
        $resultado["estado"] = "checkin";
        echo json_encode($resultado,JSON_UNESCAPED_UNICODE); 
    }
} else {
    $resultado["estado"] = "inicio";
    echo json_encode($resultado,JSON_UNESCAPED_UNICODE); 
}

?>