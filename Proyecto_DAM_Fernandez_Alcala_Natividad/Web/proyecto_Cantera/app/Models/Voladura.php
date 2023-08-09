<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Voladura extends Model
{
    use HasFactory;

    protected $fillable = [
        'localizacion',
        'm2_superficie',
        'malla_perforacion',
        'profundidad_barrenos',
        'numero_barrenos',
        'kg_explosivo',
        'precio',
        'piedra_bruta',
        'fecha_voladura',
        'id_empleado'
    ];
}
