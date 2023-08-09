<?php
// Conexion a la base de datos
require '../../conexion.php';
require '../../funciones/funciones.php';

// Valores obtenidos por post
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

// Consulta para obtener la voladura con x fecha
$consulta_id_voladura = $bd->query("SELECT * FROM voladuras WHERE fecha_voladura = '$fecha_voladura'");

$voladura_id = 0;

if(!$consulta_id_voladura){
    printf("Error: %s\n", $bd->error); 
    exit();
} else {
    while($row = $consulta_id_voladura->fetch_assoc()){

        $voladura_id = $row['id'];
    }
}

// Comprobamos los valores
if(comprobarNumero($arena_06) && comprobarNumero($grava_612) && comprobarNumero($grava_1220) &&
    comprobarNumero($grava_2023) && comprobarNumero($rechazo) && comprobarNumero($zahorra) &&
    comprobarNumero($escollera) && comprobarNumero($mamposteria)){  

    // Insertamos el stock del producto
    $consulta = $bd->query("INSERT INTO stock_productos (arena_06,grava_612,grava_1220,grava_2023,rechazo,zahorra,escollera,mamposteria,voladura,voladura_fecha,id_empleado) VALUES ('$arena_06','$grava_612','$grava_1220','$grava_2023','$rechazo','$zahorra','$escollera','$mamposteria','$voladura_id','$fecha_voladura','$id_empleado')"); 

    if(!$consulta){
        printf("Error: %s\n", $bd->error); 
        exit();
    } else {
        echo 'Producto insertado con éxito';
    }                            

} else {
    echo 'Los datos introducidos deben de ser números';
}



?>