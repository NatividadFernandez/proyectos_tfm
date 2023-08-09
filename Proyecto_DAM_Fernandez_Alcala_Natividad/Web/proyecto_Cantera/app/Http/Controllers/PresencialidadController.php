<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Presencialidad;
use Illuminate\Support\Facades\DB;

class PresencialidadController extends Controller
{
    /** 
     * Método que comprueba si el usuario está logueado
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     *  Método que muestra los controles horarios de los empleados
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        // Tramo horario
        date_default_timezone_set('Europe/Madrid');
        // Fecha del dia en el que estamos
        $fechaLocal = strval(date('Y-m-d'));

        // Consulta que devuelve todos los controles horarios registrados hasta hoy
        $presencialidad = DB::select("SELECT e.nombre,e.apellidos,r.fecha,r.fecha_check_in,r.fecha_check_out FROM presencialidads r INNER JOIN empleados e ON r.id_empleado = e.user_id ORDER BY r.fecha");
        // Consulta que devuelve los empleados que aún no han hecho el control horario de hoy
        $presencialidadNOT = DB::select("SELECT nombre,apellidos FROM empleados e WHERE NOT EXISTS (SELECT null FROM presencialidads p WHERE p.id_empleado = e.user_id AND fecha = '$fechaLocal')");
        
        // Pasamos por parámetro ambos resultados
        return view('presencialidad.index',compact('presencialidad','presencialidadNOT'));
    }
    
}
