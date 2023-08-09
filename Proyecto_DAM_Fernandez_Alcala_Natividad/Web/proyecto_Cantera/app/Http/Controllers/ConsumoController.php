<?php

namespace App\Http\Controllers;
use App\Models\Consumo;
use App\Models\StockConsumo;
use Illuminate\Http\Request;

class ConsumoController extends Controller
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
     * Método que muestra todos los consumos
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $consumos = Consumo::all();
        return view('consumos.index',compact('consumos'));
    }

    /**
     * Método que nos devuelve la view create (form) para poder añadir un nuevo consumo
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
       // Devolvemos el view create
        return view('consumos.create'/* ,compact('voladuras') */);
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
            'gasoleo'=>['required','numeric','between:0,99999.99'],
            'aceite_motor'=>['required','numeric','between:0,99999.99'],
            'aceite_hidraulico'=>['required','numeric','between:0,99999.99'],
            'aceite_transmisiones'=>['required','numeric','between:0,99999.99'],
            'valvulina'=>['required','numeric','between:0,99999.99'],
            'grasas' => ['required','numeric','between:0,99999.99'],
            'fecha_recepcion'=>['required']
        ]); 

        //Creamos el nuevo consumo
        Consumo::create([
            'gasoleo'=>$request->gasoleo,
            'aceite_motor'=>$request->aceite_motor,
            'aceite_hidraulico'=>$request->aceite_hidraulico,
            'aceite_transmisiones'=>$request->aceite_transmisiones,
            'valvulina'=>$request->valvulina,
            'grasas' => $request->grasas,
            'fecha_recepcion'=>$request->fecha_recepcion,
            'id_empleado'=> auth()->user()->id,
        ]);

        // Y creamos el stock del consumo nuevo
        StockConsumo::create([
            'gasoleo'=>$request->gasoleo,
            'aceite_motor'=>$request->aceite_motor,
            'aceite_hidraulico'=>$request->aceite_hidraulico,
            'aceite_transmisiones'=>$request->aceite_transmisiones,
            'valvulina'=>$request->valvulina,
            'grasas' => $request->grasas,
            'fecha_consumo'=>$request->fecha_recepcion,
            'id_empleado'=> auth()->user()->id,
        ]);

        // Redirigimos al index donde nos mostrarán todos los consumos inclusive el añadido
        return redirect()->route('consumos.index')->with('Mensaje','Consumos insertados con éxito');
    }

    /**
     * Método que nos devuelve la ficha de información sobre la fila seleccionada 
     *
     * @param  Consumo  $consumo
     * @return \Illuminate\Http\Response
     */
    public function show(Consumo $consumo)
    {
        //Al view show, pasamos como parametro el consumo seleccionado
        return view('consumos.show',compact('consumo'));
    }

    /**
     * Método que nos devuelve la view edit (form) para poder actualizar un consumo
     *
     * @param  Consumo  $consumo
     * @return \Illuminate\Http\Response
     */
    public function edit(Consumo $consumo)
    {
        //Al view edit, pasamos como parametro el consumo seleccionado
        return view('consumos.edit',compact('consumo'/* ,'voladuras','voladura' */));
    }

    /**
     *  Método que obtiene los valores insertados por el usuario, para ser actualizados sobre la fila seleccionada
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  Consumo $consumo
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Consumo $consumo)
    {
        //Validamos los campos
        $request->validate([
            'gasoleo'=>['required','numeric','between:0,99999.99'],
            'aceite_motor'=>['required','numeric','between:0,99999.99'],
            'aceite_hidraulico'=>['required','numeric','between:0,99999.99'],
            'aceite_transmisiones'=>['required','numeric','between:0,99999.99'],
            'valvulina'=>['required','numeric','between:0,99999.99'],
            'grasas' => ['required','numeric','between:0,99999.99'],
            'fecha_recepcion'=>['required']
        ]); 

        // Actualizamos la fila seleccionada
        $consumo->update([
            'gasoleo'=>$request->gasoleo,
            'aceite_motor'=>$request->aceite_motor,
            'aceite_hidraulico'=>$request->aceite_hidraulico,
            'aceite_transmisiones'=>$request->aceite_transmisiones,
            'valvulina'=>$request->valvulina,
            'grasas' => $request->grasas,
            'fecha_recepcion'=>$request->fecha_recepcion,
            'id_empleado'=> auth()->user()->id,
        ]);

        // Redirigimos a index
        return redirect()->route('consumos.index')->with('Mensaje','Consumos modificados con éxito');
    }

    /**
     * Método que elimina la fila seleccionada
     *
     * @param  Consumo  $consumo
     * @return \Illuminate\Http\Response
     */
    public function destroy(Consumo $consumo)
    {
        //Eliminamos
        $consumo->delete();
        //Redirigimos a index
        return redirect()->route('consumos.index');
    }
}
