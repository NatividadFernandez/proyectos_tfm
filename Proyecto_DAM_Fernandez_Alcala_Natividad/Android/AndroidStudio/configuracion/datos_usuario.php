<?php
// Conexion a la base de datos
require '../conexion.php';

// Valores obtenidos por post
$usu_id = $_POST['id_empleado'];
$usu_nombre = $_POST['nombre'];
$usu_email = $_POST['email'];
$usu_password = $_POST['password'];
$usu_password_new = $_POST['new_password'];
$usu_password_verifity = $_POST['verifity_password'];

// Consulta si existe el usuario
$consulta = $bd->query("SELECT * FROM users WHERE email='$usu_email' AND id='$usu_id'");

if(!$consulta){
    printf("Error: %s\n", $bd->error); 
    exit();
} else {
    if($row = $consulta->fetch_assoc()){
        // Verificamos la contraseña actual
        if(password_verify($usu_password,$row['password'])){ 
            //Comprobamos que la nueva contraseña sea igua la la verificada
            if($usu_password_new == $usu_password_verifity){
                //Encriptamos la contraseña
                $passwordHash = password_hash($usu_password_new, PASSWORD_DEFAULT);
                //Actualizamos la contraseña ya encriptada
                $consulta = $bd->query("UPDATE users SET `password`= '$passwordHash' WHERE email='$usu_email' AND id='$usu_id'");
                
                if(!$consulta){
                    printf("Error: %s\n", $bd->error); 
                    exit();
                } else {
                    echo "Contraseña actualizada con exito";
                }
    
            } else {
                echo "La nueva contraseña debe ser igual a la verificada";
            }

        } else {
            echo "La contraseña actual es incorrecta";
        }
    }
}

?>