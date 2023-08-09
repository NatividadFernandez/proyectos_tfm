<?php
// Conexion a la base de datos
require '../../conexion.php';
require '../../funciones/funciones.php';

// Valores obtenidos por post
$gasoleo = $_POST['gasoleo'];
$aceite_motor = $_POST['aceite_motor'];
$aceite_hidraulico = $_POST['aceite_hidraulico'];
$aceite_transmisiones = $_POST['aceite_transmisiones'];
$valvulina = $_POST['valvulina'];
$grasas = $_POST['grasas'];
$id_empleado = intval($_POST['id_empleado']);
$fecha_consumo = date('Y-m-d H:i:s',strtotime($_POST['fecha_consumo']));

// Comprobamos los valores
if(comprobarNumero($gasoleo) && comprobarNumero($aceite_motor) && comprobarNumero($aceite_hidraulico) &&
    comprobarNumero($aceite_transmisiones) && comprobarNumero($valvulina) && comprobarNumero($grasas)){  

    // Insertamos el stock de consumo
    $consulta = $bd->query("INSERT INTO stock_consumos (gasoleo,aceite_motor,aceite_hidraulico,aceite_transmisiones,valvulina,grasas,fecha_consumo,id_empleado) VALUES ('$gasoleo','$aceite_motor','$aceite_hidraulico','$aceite_transmisiones','$valvulina','$grasas','$fecha_consumo','$id_empleado')"); 

    if(!$consulta){
        printf("Error: %s\n", $bd->error); 
        exit();
    } else {
        echo 'Consumo insertado con éxito';       
    }                            

} else {
    echo 'Los datos introducidos deben de ser números';
}



?>