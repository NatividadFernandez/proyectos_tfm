<?php
// Conexion a la base de datos
require '../conexion.php';
require '../funciones/funciones.php';

// Valores obtenidos por post
$id = intval($_POST['id']);
$gasoleo = $_POST['gasoleo'];
$aceite_motor = $_POST['aceite_motor'];
$aceite_hidraulico = $_POST['aceite_hidraulico'];
$aceite_transmisiones = $_POST['aceite_transmisiones'];
$valvulina = $_POST['valvulina'];
$grasas = $_POST['grasas'];
$id_empleado = intval($_POST['id_empleado']);
$fecha_recepcion = date('Y-m-d H:i:s',strtotime($_POST['fecha_recepcion']));

// Comprobamos los valores
if(comprobarNumero($gasoleo) && comprobarNumero($aceite_motor) && comprobarNumero($aceite_hidraulico) &&
    comprobarNumero($aceite_transmisiones) && comprobarNumero($valvulina) && comprobarNumero($grasas)){  

    // Actualizamos el consumo
    $consulta = $bd->query("UPDATE consumos SET gasoleo = '$gasoleo', aceite_motor = '$aceite_motor', aceite_hidraulico = '$aceite_hidraulico',
    aceite_transmisiones = '$aceite_transmisiones', valvulina = '$valvulina', grasas = '$grasas',
    fecha_recepcion = '$fecha_recepcion', id_empleado = '$id_empleado' WHERE id='$id'");    

    
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