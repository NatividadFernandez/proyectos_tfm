<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Producto extends Model
{
    use HasFactory;

    protected $fillable = [
        'arena_06',
        'grava_612',
        'grava_1220',
        'grava_2023',
        'rechazo',
        'zahorra',
        'escollera',
        'mamposteria',
        'voladura',
        'voladura_fecha',
        'id_empleado'
    ];
}
