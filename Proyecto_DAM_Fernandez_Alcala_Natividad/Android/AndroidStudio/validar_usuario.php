<?php
// Abrimos conexión con la base de datos
require 'conexion.php';

// Obtenemos los valores por post
$usu_email = $_POST['email'];
$usu_password = $_POST['password'];

/* $usu_email = 'nati@gmail.com';
$usu_password = '12345678'; */

// Nos ayuda a prevenir ataques de inyecciones sql
$consulta = $bd->prepare("SELECT * FROM users WHERE email=?");
$consulta->bind_param('s',$usu_email);
$consulta->execute();

$resultado = $consulta->get_result();
if($fila = $resultado->fetch_assoc()){
    // Verificamos la contraseña
    if(password_verify($usu_password,$fila['password'])){     
        echo json_encode($fila,JSON_UNESCAPED_UNICODE);
    }     
}

// Cerramos conexión
$consulta->close();
$bd->close();

?>