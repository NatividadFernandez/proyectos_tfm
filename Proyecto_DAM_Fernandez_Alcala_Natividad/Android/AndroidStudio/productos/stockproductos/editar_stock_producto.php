<?php
// Conexion a la base de datos
require '../../conexion.php';
require '../../funciones/funciones.php';

// Valores obtenidos por post
$id = intval($_POST['id']);
$arena_06 = $_POST['arena_06'];
$grava_612 = $_POST['grava_612'];
$grava_1220 = $_POST['grava_1220'];
$grava_2023 = $_POST['grava_2023'];
$rechazo = $_POST['rechazo'];
$zahorra = $_POST['zahorra'];
$escollera = $_POST['escollera'];
$mamposteria = $_POST['mamposteria'];
$id_empleado = intval($_POST['id_empleado']);
$fecha_voladura = date('Y-m-d H:i:s',strtotime($_POST['voladura_fecha']));

// Comprobamos los valores
if(comprobarNumero($arena_06) && comprobarNumero($grava_612) && comprobarNumero($grava_1220) &&
    comprobarNumero($grava_2023) && comprobarNumero($rechazo) && comprobarNumero($zahorra) &&
    comprobarNumero($escollera) && comprobarNumero($mamposteria)){  

    // Actualizamos el stock del producto
    $consulta = $bd->query("UPDATE stock_productos SET arena_06 = '$arena_06', grava_612 = '$grava_612', grava_1220 = '$grava_1220',
    grava_2023 = '$grava_2023', rechazo = '$rechazo', zahorra = '$zahorra', escollera = '$escollera', mamposteria = '$mamposteria',
    voladura_fecha = '$fecha_voladura', id_empleado='$id_empleado' WHERE id='$id'");
    
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