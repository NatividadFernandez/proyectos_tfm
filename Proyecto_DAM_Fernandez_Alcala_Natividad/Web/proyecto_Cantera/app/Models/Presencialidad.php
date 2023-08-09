<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Presencialidad extends Model
{
    use HasFactory;

    protected $fillable = [
        'id_empleado',
        'fecha',
        'fecha_check_in',
        'fecha_check_out'
    ];
}
