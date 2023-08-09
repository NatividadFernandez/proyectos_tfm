<?php

namespace App\Http\Controllers;

use App\Models\StockProducto;
use App\Models\Voladura;

use Illuminate\Http\Request;

class StockProductoController extends Controller
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
     * Método que muestra todo el stock de productos
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $stock_productos = StockProducto::all();
        return view('stock_productos.index',compact('stock_productos'));
    }

    /**
     * Método que nos devuelve la ficha de información sobre la fila seleccionada 
     *
     * @param  StockProducto  $stock_producto
     * @return \Illuminate\Http\Response
     */
    public function show(StockProducto $stock_producto)
    {
        //Al view show, pasamos como parametro el stock del producto seleccionado
        return view('stock_productos.show',compact('stock_producto'));
    }

    /**
     * Método que nos devuelve la view edit (form) para poder actualizar el stock de productos
     *
     * @param  StockProducto  $stock_producto
     * @return \Illuminate\Http\Response
     */
    public function edit(StockProducto $stock_producto)
    {
        //Obtenemos la voladura que está relaccionada con el producto
        $voladura = Voladura::findOrFail($stock_producto->voladura);
         //Al view edit, pasamos como parametro el stock de productos seleccionado y la voladura que pertenecea este stock
        return view('stock_productos.edit',compact('stock_producto','voladura'));
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
            'arena_06'=>['required','numeric','between:0,99999.99'],
            'grava_612'=>['required','numeric','between:0,99999.99'],
            'grava_1220'=>['required','numeric','between:0,99999.99'],
            'grava_2023'=>['required','numeric','between:0,99999.99'],
            'rechazo'=>['required','numeric','between:0,99999.99'],
            'zahorra' => ['required','numeric','between:0,99999.99'],
            'escollera'=>['required','numeric','between:0,99999.99'],
            'mamposteria'=>['required','numeric','between:0,99999.99'],
            'voladura'=>['required']
        ]);

        // Buscamos la voladura que pertenece al producto
        $voladura = Voladura::findOrFail($request->voladura);

        //Cada vez que queramos actualizar el stock se hace una nueva insercción
        StockProducto::create([
            'arena_06'=>$request->arena_06,
            'grava_612'=>$request->grava_612,
            'grava_1220'=>$request->grava_1220,
            'grava_2023'=>$request->grava_2023,
            'rechazo'=>$request->rechazo,
            'zahorra' => $request->zahorra,
            'escollera'=>$request->escollera,
            'mamposteria'=>$request->mamposteria,
            'voladura'=> $request->voladura,
            'voladura_fecha'=> $voladura->fecha_voladura,
            'id_empleado'=> auth()->user()->id,
        ]); 

        // Redirigimos a index
        return redirect()->route('stock_productos.index')->with('Mensaje','Stock actualizado con éxito');
    }

    /**
     * Método que elimina la fila seleccionada
     *
     * @param  StockProducto  $stock_producto
     * @return \Illuminate\Http\Response
     */
    public function destroy(StockProducto $stock_producto)
    {
        //Eliminamos
        $stock_producto->delete();
        //Redirigimos a index
        return redirect()->route('stock_productos.index')->with('Mensaje','Stock del producto borrado con éxito');
    }
}
