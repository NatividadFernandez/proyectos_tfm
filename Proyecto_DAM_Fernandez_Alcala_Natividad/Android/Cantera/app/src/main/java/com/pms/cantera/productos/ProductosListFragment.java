package com.pms.cantera.productos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductosListFragment extends Fragment {

    // Declaración de variables
    private RecyclerView recyclerView;
    private ProductosAdapter productosAdapter;
    private List<Productos> productosList;
    private FloatingActionButton add_producto;
    private AutoCompleteTextView dropdown;
    private String idUsuario;
    ArrayList<String> fechaList = new ArrayList<>();
    ArrayAdapter<String> fechaAdapter;
    private String fechaSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       return inflater.inflate(R.layout.fragment_productos_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Accedemos al idUsuario desde las preferencias
        SharedPreferences preferencesss = getContext().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        idUsuario = preferencesss.getString("idUsuario","holo");

        // cargamos la fechas en el dropdown
        cargarFechasVoladura();

        add_producto = (FloatingActionButton) view.findViewById(R.id.add_btn_producto);

        // Cuando se pulse el botón añadir producto, llamará al método addProducto
        add_producto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                AddProducto(view);
            }
        });

        dropdown = (AutoCompleteTextView) view.findViewById(R.id.add_voladura_fecha);

        //Asignamos las id a las variables
        recyclerView = view.findViewById(R.id.recyclerList);
        // Establecemos el diseño de los contenidos, es decir, la lista de vistas repetidas en la vista del recyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // creamos un ArrayList
        productosList = new ArrayList<>();

        //Leemos todos los productos
        LoadAllProductos();

    }

    // Cargamos todos los productos en el recyclerView
    private void LoadAllProductos() {
        // Consulta a la base de datos, para que nos devuelva todos los productos
        JsonArrayRequest request = new JsonArrayRequest(Urls.SHOW_ALL_PRODUCTOS_DATA_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i <response.length(); i++){
                    try {
                        // Creo un objetojson, accedo a los valores y se lo a signo a las variables
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

                        // Creación objeto Productos
                        Productos productos = new Productos();
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
                        productosList.add(productos);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // Objeto Adapter, pasamos por parámetro getContext() que se refiere a la clase
                // en la que nos encontramos y a la List creada anteriormente
                productosAdapter = new ProductosAdapter(getContext(),productosList);
                recyclerView.setAdapter(productosAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    // Añadimos un nuevo producto
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void AddProducto(View view) {
        View editLayout = LayoutInflater.from(getContext()).inflate(R.layout.producto_add, null);
        TextView Id = editLayout.findViewById(R.id.id_producto);
        EditText Arena06 = editLayout.findViewById(R.id.add_arena_06);
        EditText Grava612 = editLayout.findViewById(R.id.add_grava_612);
        EditText Grava1220 = editLayout.findViewById(R.id.add_grava_1220);
        EditText Grava2023 = editLayout.findViewById(R.id.add_grava_2023);
        EditText Rechazo = editLayout.findViewById(R.id.add_rechazo);
        EditText Zahorra = editLayout.findViewById(R.id.add_zahorra);
        EditText Escollera = editLayout.findViewById(R.id.add_escollera);
        EditText Mamposteria = editLayout.findViewById(R.id.add_mamposteria);
        AutoCompleteTextView Fecha_voladura = editLayout.findViewById(R.id.add_voladura_fecha);
        // Ponemos el foco en fecha
        Fecha_voladura.requestFocus();
        TextView Id_empleado = editLayout.findViewById(R.id.add_producto_id_empleado);

        // Leemos todas la voladuras
        loadAllVoladuras(Fecha_voladura);

        // Evento cuando cambiamos de fecha en el 'spinner/dropdown'
        Fecha_voladura.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Log.i();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Log.i();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Si la fecha tiene el foco que oculte el teclado
                if(Fecha_voladura.requestFocus()) {
                    Fecha_voladura.setInputType(InputType.TYPE_NULL);
                }
                fechaSpinner = Fecha_voladura.getText().toString();
                //Log.i("La fecha elegida es:",fechaSpinner);
                //String newText = Fecha_voladura.getText().toString();
                //Log.i("Hola",newText);
            }
        });

        fechaSpinner = Fecha_voladura.getText().toString();
        Id_empleado.setText(idUsuario);

        // Dialogo añadir
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setBackground(getContext().getResources().getDrawable(R.drawable.alert_dialog,null));
        builder.setView(editLayout);
        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Obtenemos los valores que ha introducido el usuario
                final String arena_06 = Arena06.getText().toString();
                final String grava_612 = Grava612.getText().toString();
                final String grava_1220 = Grava1220.getText().toString();
                final String grava_2023 = Grava2023.getText().toString();
                final String rechazo =  Rechazo.getText().toString();
                final String zahorra = Zahorra.getText().toString();
                final String escollera = Escollera.getText().toString();
                final String mamposteria = Mamposteria.getText().toString();
                final String fecha_voladura = fechaSpinner;
                final String id_empleado = idUsuario;

                // Comprobamos que no estén vacíos
                if(arena_06.isEmpty() || grava_612.isEmpty() || grava_1220.isEmpty() ||
                        grava_2023.isEmpty() || rechazo.isEmpty() || zahorra.isEmpty() ||
                        escollera.isEmpty() || mamposteria.isEmpty()){
                    Toast.makeText(getContext(), "Algunos campos están vacíos", Toast.LENGTH_SHORT).show();
                } else {
                    // Hacemos una petición post para añadir un nuevo consumo
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.ADD_PRODUCTO_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                            //Recargamos
                            Navigation.findNavController(view).navigate(R.id.action_productosListFragment_self);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Mensaje de error
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            // Creamos una instacia el objeto Map
                            HashMap<String,String> params = new HashMap<>();
                            // Con put ingresamos los datos a enviar
                            params.put("arena_06",arena_06);
                            params.put("grava_612",grava_612);
                            params.put("grava_1220",grava_1220);
                            params.put("grava_2023",grava_2023);
                            params.put("rechazo",rechazo);
                            params.put("zahorra",zahorra);
                            params.put("escollera",escollera);
                            params.put("mamposteria",mamposteria);
                            params.put("voladura_fecha",fecha_voladura);
                            params.put("id_empleado",id_empleado);

                            return params;
                        }
                    };
                    // Creamos instancia de la clase RequestQueue
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    // Agregamos la instancia de nuestro objeto StringRequest, ayuda a procesar las peticiones
                    requestQueue.add(stringRequest);
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Cerramos el dialogo
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    // Añadimos a un arraylist todas las fechas de las voladuras existentes
    private void cargarFechasVoladura(){
        JsonArrayRequest request = new JsonArrayRequest(Urls.SHOW_ALL_VOLADURA_DATA_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i <response.length(); i++){
                        // Objeto json para obtener las fechas
                        JSONObject object = response.getJSONObject(i);
                        String voladura_fecha = object.getString("fecha_voladura").trim();

                        /*Log.i("fecha voladura: ",voladura_fecha);*/

                        // Añadimos al array la fecha
                        fechaList.add(voladura_fecha);
                    }

                    fechaAdapter = new ArrayAdapter<>(getContext(),R.layout.option_item,fechaList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    // Cargamos el arraylist en el dropdown
    private void loadAllVoladuras(AutoCompleteTextView dropdown) {
        dropdown.setText(fechaAdapter.getItem(0).toString(),false);
        dropdown.setAdapter(fechaAdapter);
    }
}