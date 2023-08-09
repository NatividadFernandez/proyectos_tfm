<?php

namespace App\Http\Controllers;

use App\Models\Producto;
use App\Models\Voladura;
use App\Models\StockProducto;

use Illuminate\Http\Request;

class ProductoController extends Controller
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
     * Método que muestra todos los productos
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $productos = Producto::all();
        return view('productos.index',compact('productos'));
    }

    /**
     * Método que nos devuelve la view create (form) para poder añadir un nuevo producto
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        // Añado el parametro voladuras para obtener las fechas de estas
        $voladuras = Voladura::all(); /*Para que aparezcan las fechas*/
        return view('productos.create',compact('voladuras'));
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

        // Buscamos los datos de la voladura obtenida por request
        // para despues poder acceder a la fecha de esta
        $voladura = Voladura::findOrFail($request->voladura);

        //Creamos el nuevo producto
        Producto::create([
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

         // Y creamos el stock del producto nuevo
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

         // Redirigimos al index donde nos mostrarán todos los consumos inclusive el añadido
        return redirect()->route('productos.index')->with('Mensaje','Productos insertados con éxito');
    }

    /**
     *  Método que nos devuelve la ficha de información sobre la fila seleccionada 
     *
     * @param  Producto  $producto
     * @return \Illuminate\Http\Response
     */
    public function show(Producto $producto)
    {
         //Al view show, pasamos como parametro el producto seleccionado
        return view('productos.show',compact('producto'));
    }

    /**
     * Método que nos devuelve la view edit (form) para poder actualizar un producto
     *
     * @param  Producto  $producto
     * @return \Illuminate\Http\Response
     */
    public function edit(Producto $producto)
    {
        //Obtengo todas la voladuras
        $voladuras = Voladura::all();
        // Obtengo la voladura obtenida por productos
        $voladura = Voladura::findOrFail($producto->voladura);
        // Pasamo tres parametros, el producto, todas la voladuras y la voladura que esta relacionada con el producto
        return view('productos.edit',compact('producto','voladuras','voladura'));
    }

    /**
     *  Método que obtiene los valores insertados por el usuario, para ser actualizados sobre la fila seleccionada
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  Producto  $producto
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Producto $producto)
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

        // Buscamos los datos de la voladura obtenida por request
        // para despues poder acceder a la fecha de esta
        $voladura = Voladura::findOrFail($request->voladura);

        // Actualizamos la fila seleccionada
        $producto->update([
            'arena_06'=>$request->arena_06,
            'grava_612'=>$request->grava_612,
            'grava_1220'=>$request->grava_1220,
            'grava_2023'=>$request->grava_2023,
            'rechazo'=>$request->rechazo,
            'zahorra' => $request->zahorra,
            'escollera'=>$request->escollera,
            'mamposteria'=>$request->mamposteria,
            'voladura'=> $request->voladura,
            'voladura_fecha'=> $voladura->fecha_voladura
        ]); 

        // Redirigimos a index
        return redirect()->route('productos.index')->with('Mensaje','Productos modificados con éxito');
    }

    /**
     * Método que elimina la fila seleccionada
     *
     * @param  Producto  $producto
     * @return \Illuminate\Http\Response
     */
    public function destroy(Producto $producto)
    {
        //Eliminamos
        $producto->delete();
        //Redirigimos a index
        return redirect()->route('productos.index');
    }
}
