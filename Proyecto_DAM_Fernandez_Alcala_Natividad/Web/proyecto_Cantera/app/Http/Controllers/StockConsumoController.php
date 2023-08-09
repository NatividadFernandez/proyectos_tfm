<?php

namespace App\Http\Controllers;
use App\Models\StockConsumo;

use Illuminate\Http\Request;

class StockConsumoController extends Controller
{
    /**
     *  Método que comprueba si el usuario está logueado
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     * Método que muestra todo el stock de consumo
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $stock_consumos = StockConsumo::all();
        return view('stock_consumos.index',compact('stock_consumos'));
    }

    /**
     * Método que nos devuelve la ficha de información sobre la fila seleccionada 
     *
     * @param  StockConsumo  $stock_consumo
     * @return \Illuminate\Http\Response
     */
    public function show(StockConsumo $stock_consumo)
    {
        //Al view show, pasamos como parametro el stock del consumo seleccionado
        return view('stock_consumos.show',compact('stock_consumo'));
    }

    /**
     * Método que nos devuelve la view edit (form) para poder actualizar el stock del consumo
     *
     * @param  StockConsumo  $stock_consumo
     * @return \Illuminate\Http\Response
     */
    public function edit(StockConsumo $stock_consumo)
    {
        //Al view edit, pasamos como parametro el stock del consumo seleccionado
        return view('stock_consumos.edit',compact('stock_consumo'));
    }

    /**
     * Método que obtiene los valores insertados por el usuario, para ser actualizados sobre la fila seleccionada
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request)
    {
        //Validamos los campos
        $request->validate([
            'gasoleo'=>['required','numeric','between:0,99999.99'],
            'aceite_motor'=>['required','numeric','between:0,99999.99'],
            'aceite_hidraulico'=>['required','numeric','between:0,99999.99'],
            'aceite_transmisiones'=>['required','numeric','between:0,99999.99'],
            'valvulina'=>['required','numeric','between:0,99999.99'],
            'grasas' => ['required','numeric','between:0,99999.99'],
            'fecha_consumo'=>['required']
        ]); 

        //Cada vez que queramos actualizar el stock se hace una nueva insercción
        StockConsumo::create([
            'gasoleo'=>$request->gasoleo,
            'aceite_motor'=>$request->aceite_motor,
            'aceite_hidraulico'=>$request->aceite_hidraulico,
            'aceite_transmisiones'=>$request->aceite_transmisiones,
            'valvulina'=>$request->valvulina,
            'grasas' => $request->grasas,
            'fecha_consumo'=>$request->fecha_consumo,
            'id_empleado'=> auth()->user()->id,
        ]);

        // Redirigimos a index
        return redirect()->route('stock_consumos.index')->with('Mensaje','Stock Consumos modificados con éxito');
    }

    /**
     *  Método que elimina la fila seleccionada
     *
     * @param  StockConsumo $stock_consumo
     * @return \Illuminate\Http\Response
     */
    public function destroy(StockConsumo $stock_consumo)
    {
        //Eliminamos
        $stock_consumo->delete();
        //Redirigimos a index
        return redirect()->route('stock_consumos.index')->with('Mensaje','Stock de consumos borrado con éxito');
    }
}
