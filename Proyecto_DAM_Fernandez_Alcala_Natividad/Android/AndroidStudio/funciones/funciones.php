<?php

//Validar número real / entero
function comprobarNumero($num)
{
    return preg_match("/^\d{1,10}(\.\d{1,3})?$/", $num);
}

?>