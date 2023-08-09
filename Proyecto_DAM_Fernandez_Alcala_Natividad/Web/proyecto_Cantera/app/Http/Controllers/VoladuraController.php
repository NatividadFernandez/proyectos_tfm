<?php

namespace App\Http\Controllers;

use App\Models\Voladura;
use Illuminate\Http\Request;

class VoladuraController extends Controller
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
     * Método que muestra todas las voladuras
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $voladuras = Voladura::all();
        return view('voladuras.index',compact('voladuras'));
    }

    /**
     * Método que nos devuelve la view create (form) para poder añadir una nueva voladura
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        // Devolvemos la view create
        return view('voladuras.create');
    }

    /**
     * Método que obtiene los valores insertados por el usuario, para ser añadidos a la base de datos
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        //Validamos los campos
        $request->validate([
            'localizacion'=>['required'],
            'superficie'=>['required','numeric','between:0,99999.99'],
            'perforacion'=>['required'],
            'profundidadBarrenos'=>['required','numeric','between:0,99999.99'],
            'numeroBarrenos'=>['required','numeric'],
            'explosivo' => ['required','numeric','between:0,99999.99'],
            'precio'=>['required','numeric','between:0,99999.99'],
            'piedraBruta'=>['required','numeric','between:0,99999.99'],
            'fechaVoladura'=>['required']
        ]);         


        // Creamos una nueva voladura
        Voladura::create([
            'localizacion'=>$request->localizacion,
            'm2_superficie'=>$request->superficie,
            'malla_perforacion'=>$request->perforacion,
            'profundidad_barrenos'=>$request->profundidadBarrenos,
            'numero_barrenos'=>$request->numeroBarrenos,
            'kg_explosivo' => $request->explosivo,
            'precio'=>$request->precio,
            'piedra_bruta'=>$request->piedraBruta,
            'fecha_voladura'=> date('Y-m-d H:i:s', strtotime($request->fechaVoladura)) /* date('Y-m-d', strtotime($request->fechaVoladura)) */,
            'id_empleado'=> auth()->user()->id,
        ]);

         // Redirigimos al index donde nos mostrarán todos las voladuras inclusive el añadido
        return redirect()->route('voladuras.index')->with('Mensaje','Voladura creada con éxito');

    }

    /**
     * Método que nos devuelve la ficha de información sobre la fila seleccionada 
     *
     * @param  Voladura  $voladura
     * @return \Illuminate\Http\Response
     */
    public function show(Voladura $voladura)
    {
         //Al view show, pasamos como parametro la voladura seleccionada
        return view('voladuras.show',compact('voladura'));
    }

    /**
     * Método que nos devuelve la view edit (form) para poder actualizar una voladura
     *
     * @param  Voladura  $voladura
     * @return \Illuminate\Http\Response
     */
    public function edit(Voladura $voladura)
    {
        //Al view edit, pasamos como parametro la voladura seleccionada
        return view('voladuras.edit',compact('voladura'));
    }

    /**
     * Método que obtiene los valores insertados por el usuario, para ser actualizados sobre la fila seleccionada
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  Voladura  $voladura
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Voladura $voladura)
    {
        //Validamos los campos
        $request->validate([
            'localizacion'=>['required'],
            'superficie'=>['required','numeric','between:0,99999.99'],
            'perforacion'=>['required'],
            'profundidadBarrenos'=>['required','numeric','between:0,99999.99'],
            'numeroBarrenos'=>['required','numeric'],
            'explosivo' => ['required','numeric','between:0,99999.99'],
            'precio'=>['required','numeric','between:0,99999.99'],
            'piedraBruta'=>['required','numeric','between:0,99999.99'],
            'fechaVoladura'=>['required']
        ]); 

        // Actualizamos la voladura con los datos obtenidos por request
        $voladura->update([
            'localizacion'=>$request->localizacion,
            'm2_superficie'=>$request->superficie,
            'malla_perforacion'=>$request->perforacion,
            'profundidad_barrenos'=>$request->profundidadBarrenos,
            'numero_barrenos'=>$request->numeroBarrenos,
            'kg_explosivo' => $request->explosivo,
            'precio'=>$request->precio,
            'piedra_bruta'=>$request->piedraBruta,
            'fecha_voladura'=> date('Y-m-d H:i:s', strtotime($request->fechaVoladura)),
            'id_empleado'=> auth()->user()->id,
        ]);

        // Redirigimos a index
        return redirect()->route('voladuras.index')->with('Mensaje','Voladura modificada con éxito');
    }

    /**
     * Método que elimina la fila seleccionada
     *
     * @param  Voladura  $voladura
     * @return \Illuminate\Http\Response
     */
    public function destroy(Voladura $voladura)
    {
        // Eliminamos
        $voladura->delete();
        //Redirigimos a index
        return redirect()->route('voladuras.index');
    }
}
