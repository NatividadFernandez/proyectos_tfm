package com.pms.cantera.productos.stockproductos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pms.cantera.R;
import com.pms.cantera.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockProductosListFragment extends Fragment {

    // Declaración de variables
    private RecyclerView recyclerView;
    private StockProductosAdapter productosAdapter;
    private List<StockProductos> stockProductosList;
    private FloatingActionButton add_stock_producto;
    private AutoCompleteTextView spinner;
    // Formato número real y entero
    NumberFormat nf = new DecimalFormat("#.####");
    private String idUsuario;

    ArrayList<String> fechaList = new ArrayList<>();
    ArrayAdapter<String> fechaAdapter;

    private String fechaSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock_productos_list, container, false);
    }

    // Inicializamos las clases una vez que se sabe que su jerarquía de vista se ha creado por completo
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Accedemos al idUsuario desde las preferencias
        SharedPreferences preferencesss = getContext().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        idUsuario = preferencesss.getString("idUsuario","holo");

        //Asignamos las id a las variables
        spinner = (AutoCompleteTextView) view.findViewById(R.id.add_voladura_fecha);
        recyclerView = view.findViewById(R.id.recyclerList);

        // Establecemos el diseño de los contenidos, es decir, la lista de vistas repetidas en la vista del recyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // creamos un ArrayList
        stockProductosList = new ArrayList<>();

        //Leemos el stock de producto
        LoadAllProductos();

        add_stock_producto = (FloatingActionButton) view.findViewById(R.id.add_btn_stockproducto);

        // Cuando se pulse el botón añadir stock del producto, llamará al método addStockProducto
        add_stock_producto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                AddStockProducto(view);
            }
        });
    }


    // Cargamos todos los productos en el recyclerView
    private void LoadAllProductos() {
        JsonArrayRequest request = new JsonArrayRequest(Urls.SHOW_ALL_STOCK_PRODUCTOS_DATA_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i <response.length(); i++){
                    try {
                        // Consulta a la base de datos, para que nos devuelva el stock de producto
                        JSONObject object = response.getJSONObject(i);
                        Integer id = object.getInt("id");
                        Double arena_06 = object.getDouble("arena_06");
                        Double grava_612 = object.getDouble("grava_612");
                        Double grava_1220 = object.getDouble("grava_1220");
                        Double grava_2023 = object.getDouble("grava_2023");
                        Double rechazo = object.getDouble("rechazo");
                        Double zahorra = object.getDouble("zahorra");
                        Double escollera = object.getDouble("escollera");
                        Double mamposteria = object.getDouble("mamposteria");
                        String voladura_fecha = object.getString("voladura_fecha").trim();
                        Integer id_empleado = object.getInt("id_empleado");

                        // Creación objeto Stock Consumos
                        StockProductos productos = new StockProductos();
                        productos.setId(id);
                        productos.setArena_06(arena_06);
                        productos.setGrava_612(grava_612);
                        productos.setGrava_1220(grava_1220);
                        productos.setGrava_2023(grava_2023);
                        productos.setRechazo(rechazo);
                        productos.setZahorra(zahorra);
                        productos.setEscollera(escollera);
                        productos.setMamposteria(mamposteria);
                        productos.setFecha_voladura(voladura_fecha);
                        productos.setId_empleado(id_empleado);

                        // Añadimos las voladuras a un List
                        stockProductosList.add(productos);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // Objeto Adapter, pasamos por parámetro getContext() que se refiere a la clase
                // en la que nos encontramos y a la List creada anteriormente
                productosAdapter = new StockProductosAdapter(getContext(), stockProductosList);
                recyclerView.setAdapter(productosAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Mensaje de error
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void AddStockProducto(View view){
        // Consultar ultimo stock ingresado
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.SHOW_LATEST_STOCK_PRODUCTOS_DATA_URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(String response) {
                try {
                    // Creo un objetojson, accedo a los valores y se lo a signo a las variables
                    JSONObject object = new JSONObject(response);
                    Integer stock_id = object.getInt("id");
                    Double stock_arena_06 = object.getDouble("arena_06");
                    Double stock_grava_612 = object.getDouble("grava_612");
                    Double stock_grava_1220 = object.getDouble("grava_1220");
                    Double stock_grava_2023 = object.getDouble("grava_2023");
                    Double stock_rechazo = object.getDouble("rechazo");
                    Double stock_zahorra = object.getDouble("zahorra");
                    Double stock_escollera = object.getDouble("escollera");
                    Double stock_mamposteria = object.getDouble("mamposteria");
                    Integer stock_id_voladura = object.getInt("voladura");
                    String stock_voladura_fecha = object.getString("voladura_fecha").trim();
                    Integer stock_id_empleado = object.getInt("id_empleado");

                    // Creación objeto Stock Productos
                    StockProductos stockProductos = new StockProductos();
                    stockProductos.setArena_06(stock_arena_06);
                    stockProductos.setGrava_612(stock_grava_612);
                    stockProductos.setGrava_1220(stock_grava_1220);
                    stockProductos.setGrava_2023(stock_grava_2023);
                    stockProductos.setRechazo(stock_rechazo);
                    stockProductos.setZahorra(stock_zahorra);
                    stockProductos.setEscollera(stock_escollera);
                    stockProductos.setMamposteria(stock_mamposteria);
                    stockProductos.setId_voladura(stock_id_voladura);
                    stockProductos.setFecha_voladura(stock_voladura_fecha);
                    stockProductos.setId_empleado(stock_id_empleado);

                    // Id del xml
                    View editLayout = LayoutInflater.from(getContext()).inflate(R.layout.stock_producto_add, null);
                    TextView Id = editLayout.findViewById(R.id.add_stock_id_producto);
                    EditText Arena06 = editLayout.findViewById(R.id.add_stock_arena_06);
                    EditText Grava612 = editLayout.findViewById(R.id.add_stock_grava_612);
                    EditText Grava1220 = editLayout.findViewById(R.id.add_stock_grava_1220);
                    EditText Grava2023 = editLayout.findViewById(R.id.add_stock_grava_2023);
                    EditText Rechazo = editLayout.findViewById(R.id.add_stock_rechazo);
                    EditText Zahorra = editLayout.findViewById(R.id.add_stock_zahorra);
                    EditText Escollera = editLayout.findViewById(R.id.add_stock_escollera);
                    EditText Mamposteria = editLayout.findViewById(R.id.add_stock_mamposteria);
                    EditText Fecha_voladura = editLayout.findViewById(R.id.add_stock_voladura_fecha);
                    // No se puede modificarse la fecha
                    Fecha_voladura.setEnabled(false);
                    TextView Id_empleado = editLayout.findViewById(R.id.add_stock_producto_id_empleado);

                    // Cambiamos el contenido de los textview por los obtenidos
                    Arena06.setText(nf.format(stockProductos.getArena_06()));
                    Grava612.setText(nf.format(stockProductos.getGrava_612()));
                    Grava1220.setText(nf.format(stockProductos.getGrava_1220()));
                    Grava2023.setText(nf.format(stockProductos.getGrava_2023()));
                    Rechazo.setText(nf.format(stockProductos.getRechazo()));
                    Zahorra.setText(nf.format(stockProductos.getZahorra()));
                    Escollera.setText(nf.format(stockProductos.getEscollera()));
                    Mamposteria.setText(nf.format(stockProductos.getMamposteria()));
                    Fecha_voladura.setText(stockProductos.getFecha_voladura());
                    Id_empleado.setText(idUsuario);

                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                    builder.setBackground(getContext().getResources().getDrawable(R.drawable.alert_dialog,null));
                    builder.setView(editLayout);
                    builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final String arena_06 = Arena06.getText().toString();
                            final String grava_612 = Grava612.getText().toString();
                            final String grava_1220 = Grava1220.getText().toString();
                            final String grava_2023 = Grava2023.getText().toString();
                            final String rechazo =  Rechazo.getText().toString();
                            final String zahorra = Zahorra.getText().toString();
                            final String escollera = Escollera.getText().toString();
                            final String mamposteria = Mamposteria.getText().toString();
                            final String fecha_voladura = Fecha_voladura.getText().toString();
                            final String id_voladura = stockProductos.getId_voladura().toString();
                            final String id_empleado = idUsuario;

                            // Comprobar datos vacios
                            if(arena_06.isEmpty() || grava_612.isEmpty() || grava_1220.isEmpty() ||
                                    grava_2023.isEmpty() || rechazo.isEmpty() || zahorra.isEmpty() ||
                                    escollera.isEmpty() || mamposteria.isEmpty()){
                                Toast.makeText(getContext(), "Algunos campos están vacíos", Toast.LENGTH_SHORT).show();
                            } else {
                                // Añadir
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.ADD_STOCK_PRODUCTO_URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                        //Recargamos
                                        Navigation.findNavController(view).navigate(R.id.action_stockProductosListFragment_self);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String,String> params = new HashMap<>();
                                        //Enviamos datos
                                        params.put("arena_06",arena_06);
                                        params.put("grava_612",grava_612);
                                        params.put("grava_1220",grava_1220);
                                        params.put("grava_2023",grava_2023);
                                        params.put("rechazo",rechazo);
                                        params.put("zahorra",zahorra);
                                        params.put("escollera",escollera);
                                        params.put("mamposteria",mamposteria);
                                        params.put("voladura",id_voladura);
                                        params.put("voladura_fecha",fecha_voladura);
                                        params.put("id_empleado",id_empleado);

                                        return params;
                                    }
                                };

                                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                requestQueue.add(stringRequest);
                            }
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    builder.show();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Creamos instancia de la clase RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        // Agregamos la instancia de nuestro objeto StringRequest, ayuda a procesar las peticiones
        requestQueue.add(stringRequest);
    }


}