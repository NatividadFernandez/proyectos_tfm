<?php

namespace App\Rules;

use Illuminate\Contracts\Validation\Rule;

class Telefono implements Rule
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
        return preg_match('/(\+34|0034|34)?[ -]*(6|7)[ -]*([0-9][ -]*){8}/',$value);
    }

    /**
     * Get the validation error message.
     *
     * @return string
     */
    public function message()
    {
        return 'El :attribute debe tener 9 dígitos numéricos.';
    }
}
