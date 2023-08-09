<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class StockConsumo extends Model
{
    use HasFactory;

    protected $fillable = [
        'gasoleo',
        'aceite_motor',
        'aceite_hidraulico',
        'aceite_transmisiones',
        'valvulina',
        'grasas',
        'fecha_consumo',
        'id_empleado'
    ];
}
