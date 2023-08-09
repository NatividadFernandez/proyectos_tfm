package com.pms.cantera.consumos.stockconsumos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.pms.cantera.consumos.Consumos;
import com.pms.cantera.consumos.ConsumosAdapter;
import com.pms.cantera.productos.stockproductos.StockProductos;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockConsumosListFragment extends Fragment {

    // Declaración de variables
    private RecyclerView recyclerView;
    private StockConsumosAdapter stockConsumosAdapter;
    private List<StockConsumos> stockConsumosList;
    private FloatingActionButton add_stock_consumo;
    private String idUsuario;
    // Formato número real y entero
    NumberFormat nf = new DecimalFormat("#.####");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock_consumos_list, container, false);
    }
    // Inicializamos las clases una vez que se sabe que su jerarquía de vista se ha creado por completo
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            // Accedemos al idUsuario desde las preferencias
            SharedPreferences preferencesss = getContext().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
            idUsuario = preferencesss.getString("idUsuario", "holo");

        }catch (NullPointerException e){
            Log.i("Preferencias","No hay id del usuario");
        }

        //Asignamos las id a las variables
        recyclerView = view.findViewById(R.id.recyclerList);
        // Establecemos el diseño de los contenidos, es decir, la lista de vistas repetidas en la vista del recyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // creamos un ArrayList
        stockConsumosList = new ArrayList<>();

        //Leemos todos los stock consumos
        loadAllStockConsumos();

        add_stock_consumo = (FloatingActionButton) view.findViewById(R.id.add_btn_stock_consumo);

        // Cuando se pulse el botón añadir consumo, llamará al método addStockConsumo
        add_stock_consumo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                addStockConsumo(view);
            }
        });
    }

    // Cargamos el stock de consumos en el recyclerView
    private void loadAllStockConsumos() {
        // Consulta a la base de datos, para que nos devuelva el stock de consumos
        JsonArrayRequest request = new JsonArrayRequest(Urls.SHOW_ALL_STOCK_CONSUMOS_DATA_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i <response.length(); i++){
                    try {
                        // Creo un objetojson, accedo a los valores y se lo a signo a las variables
                        JSONObject object = response.getJSONObject(i);
                        Integer id = object.getInt("id");
                        Double gasoleo = object.getDouble("gasoleo");
                        Double aceite_motor = object.getDouble("aceite_motor");
                        Double aceite_hidraulico = object.getDouble("aceite_hidraulico");
                        Double aceite_transmisiones = object.getDouble("aceite_transmisiones");
                        Double valvulina = object.getDouble("valvulina");
                        Double grasas = object.getDouble("grasas");
                        String fecha_consumo = object.getString("fecha_consumo").trim();
                        Integer id_empleado = object.getInt("id_empleado");

                        // Creación objeto Stock Consumos
                        StockConsumos consumos = new StockConsumos();
                        consumos.setId(id);
                        consumos.setGasoleo(gasoleo);
                        consumos.setAceite_motor(aceite_motor);
                        consumos.setAceite_hidraulico(aceite_hidraulico);
                        consumos.setAceite_transmisiones(aceite_transmisiones);
                        consumos.setValvulina(valvulina);
                        consumos.setGrasas(grasas);
                        consumos.setFecha_consumo(fecha_consumo);
                        consumos.setId_empleado(id_empleado);

                        // Añadimos las voladuras a un List
                        stockConsumosList.add(consumos);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // Objeto Adapter, pasamos por parámetro getContext() que se refiere a la clase
                // en la que nos encontramos y a la List creada anteriormente
                stockConsumosAdapter = new StockConsumosAdapter(getContext(),stockConsumosList);
                recyclerView.setAdapter(stockConsumosAdapter);
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

    // Añadimos un nuevo stock del consumo
    private void addStockConsumo(View view){
        // Consultar ultimo stock ingresado
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.SHOW_LATEST_STOCK_CONSUMOS_DATA_URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(String response) {
                try {
                    // Creo un objetojson, accedo a los valores y se lo a signo a las variables
                    JSONObject object = new JSONObject(response);
                    Integer stock_id_consumo = object.getInt("id");
                    Double stock_gasoleo = object.getDouble("gasoleo");
                    Double stock_aceite_motor = object.getDouble("aceite_motor");
                    Double stock_aceite_hidraulico = object.getDouble("aceite_hidraulico");
                    Double stock_aceite_transmisiones = object.getDouble("aceite_transmisiones");
                    Double stock_valvulina = object.getDouble("valvulina");
                    Double stock_grasas = object.getDouble("grasas");
                    String stock_fecha_consumo = object.getString("fecha_consumo").trim();
                    Integer stock_id_empleado = object.getInt("id_empleado");

                    // Creación objeto Stock Consumos
                    StockConsumos stockConsumos = new StockConsumos();
                    stockConsumos.setGasoleo(stock_gasoleo);
                    stockConsumos.setAceite_motor(stock_aceite_motor);
                    stockConsumos.setAceite_hidraulico(stock_aceite_hidraulico);
                    stockConsumos.setAceite_transmisiones(stock_aceite_transmisiones);
                    stockConsumos.setValvulina(stock_valvulina);
                    stockConsumos.setGrasas(stock_grasas);
                    stockConsumos.setFecha_consumo(stock_fecha_consumo);
                    stockConsumos.setId_empleado(stock_id_empleado);

                    // Id del xml
                    View addLayout = LayoutInflater.from(getContext()).inflate(R.layout.stock_consumo_add, null);
                    EditText etGasoleo = addLayout.findViewById(R.id.add_stock_gasoleo);
                    EditText etAceite_Motor = addLayout.findViewById(R.id.add_stock_aceite_motor);
                    EditText etAceite_Hidraulico = addLayout.findViewById(R.id.add_stock_aceite_hidraulico);
                    EditText etAceite_Transmisiones = addLayout.findViewById(R.id.add_stock_aceite_transmisiones);
                    EditText etValvulina = addLayout.findViewById(R.id.add_stock_valvulina);
                    EditText etGrasas = addLayout.findViewById(R.id.add_stock_grasas);
                    EditText etFecha_consumo = addLayout.findViewById(R.id.add_stock_fecha_consumo);
                    // No se puede modificarse la fecha
                    etFecha_consumo.setEnabled(false);
                    TextView etId_empleado = addLayout.findViewById(R.id.add_stock_consumo_id_empleado);

                    // Cambiamos el contenido de los textview por los obtenidos
                    etGasoleo.setText(nf.format(stockConsumos.getGasoleo()));
                    etAceite_Motor.setText(nf.format(stockConsumos.getAceite_motor()));
                    etAceite_Hidraulico.setText(nf.format(stockConsumos.getAceite_hidraulico()));
                    etAceite_Transmisiones.setText(nf.format(stockConsumos.getAceite_transmisiones()));
                    etValvulina.setText(nf.format(stockConsumos.getValvulina()));
                    etGrasas.setText(nf.format(stockConsumos.getGrasas()));
                    etFecha_consumo.setText(stockConsumos.getFecha_consumo());
                    etId_empleado.setText(idUsuario);

                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                    builder.setBackground(getContext().getResources().getDrawable(R.drawable.alert_dialog,null));
                    builder.setView(addLayout);
                    builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final String gasoleo = etGasoleo.getText().toString();
                            final String aceite_motor = etAceite_Motor.getText().toString();
                            final String aceite_hidraulico = etAceite_Hidraulico.getText().toString();
                            final String aceite_transmisiones = etAceite_Transmisiones.getText().toString();
                            final String valvulina = etValvulina.getText().toString();
                            final String grasas = etGrasas.getText().toString();
                            final String fecha_consumo = etFecha_consumo.getText().toString();
                            final String id_empleado = idUsuario;

                            // Comprobar datos vacios
                            if(gasoleo.isEmpty() || aceite_motor.isEmpty() || aceite_hidraulico.isEmpty() ||
                                    aceite_hidraulico.isEmpty() || aceite_transmisiones.isEmpty() || valvulina.isEmpty() ||
                                    grasas.isEmpty()){
                                Toast.makeText(getContext(), "Algunos campos están vacíos", Toast.LENGTH_SHORT).show();
                            } else {
                                // Añadir
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.ADD_STOCK_CONSUMO_URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                        //Recargamos
                                        Navigation.findNavController(view).navigate(R.id.action_stockConsumosList_self);
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
                                        params.put("gasoleo",gasoleo);
                                        params.put("aceite_motor",aceite_motor);
                                        params.put("aceite_hidraulico",aceite_hidraulico);
                                        params.put("aceite_transmisiones",aceite_transmisiones);
                                        params.put("valvulina",valvulina);
                                        params.put("grasas",grasas);
                                        params.put("fecha_consumo",fecha_consumo);
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
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Creamos instancia de la clase RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        // Agregamos la instancia de nuestro objeto StringRequest, ayuda a procesar las peticiones
        requestQueue.add(stringRequest);
    }
}