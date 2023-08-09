<?php

namespace App\Rules;

use Illuminate\Contracts\Validation\Rule;

class Dni implements Rule
{
    /**
     * Create a new rule instance.
     *
     * @return void
     */
    public function __construct()
    {
        //
    }

    /**
     * Determine if the validation rule passes.
     *
     * @param  string  $attribute
     * @param  mixed  $value
     * @return bool
     */
    public function passes($attribute, $value)
    {
        //
        $Expresion_Dni = preg_match('/\d{8}[a-z A-Z]/',$value);
        $valido = false;

        if($Expresion_Dni == true){
            $letra = strtoupper(substr($value,-1));
            $numeros = substr($value,0,-1);
            if(substr("TRWAGMYFPDXBNJZSQVHLCKE", $numeros%23, 1) == $letra && strlen($letra) == 1 && strlen ($numeros) == 8){
                $valido = true;
            } 
        }
       
        return $valido;
    }

    /**
     * Get the validation error message.
     *
     * @return string
     */
    public function message()
    {
        return 'El :attribute está mal escrito.';
    }
}
