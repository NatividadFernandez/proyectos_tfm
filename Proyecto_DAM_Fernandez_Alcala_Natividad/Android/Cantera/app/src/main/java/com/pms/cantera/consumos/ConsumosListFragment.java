package com.pms.cantera.consumos;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumosListFragment extends Fragment {

    // Declaración de variables
    private RecyclerView recyclerView;
    private ConsumosAdapter consumosAdapter;
    private List<Consumos> consumosList;
    private FloatingActionButton add_consumo;
    private String idUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumos_list, container, false);
    }

    // Inicializamos las clases una vez que se sabe que su jerarquía de vista se ha creado por completo
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Accedemos al idUsuario desde las preferencias
        SharedPreferences preferencesss = getContext().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        idUsuario = preferencesss.getString("idUsuario","holo");

        //Asignamos las id a las variables
        recyclerView = view.findViewById(R.id.recyclerList);
        // Establecemos el diseño de los contenidos, es decir, la lista de vistas repetidas en la vista del recyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // creamos un ArrayList
        consumosList = new ArrayList<>();

        //Leemos todos los consumos
        loadAllConsumos();

        add_consumo = (FloatingActionButton) view.findViewById(R.id.add_btn_consumo);

        // Cuando se pulse el botón añadir consumo, llamará al método addConsumo
        add_consumo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                addConsumo(view);
            }
        });
    }

    // Cargamos todos los consumos en el recyclerView
    private void loadAllConsumos() {
        // Consulta a la base de datos, para que nos devuelva todos los consumos
        JsonArrayRequest request = new JsonArrayRequest(Urls.SHOW_ALL_CONSUMOS_DATA_URL, new Response.Listener<JSONArray>() {
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
                        String fecha_recepcion = object.getString("fecha_recepcion").trim();
                        Integer id_empleado = object.getInt("id_empleado");

                        // Creación objeto Consumos
                        Consumos consumos = new Consumos();
                        consumos.setId(id);
                        consumos.setGasoleo(gasoleo);
                        consumos.setAceite_motor(aceite_motor);
                        consumos.setAceite_hidraulico(aceite_hidraulico);
                        consumos.setAceite_transmisiones(aceite_transmisiones);
                        consumos.setValvulina(valvulina);
                        consumos.setGrasas(grasas);
                        consumos.setFecha_recepcion(fecha_recepcion);
                        consumos.setId_empleado(id_empleado);

                        // Añadimos las voladuras a un List
                        consumosList.add(consumos);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // Objeto Adapter, pasamos por parámetro getContext() que se refiere a la clase
                // en la que nos encontramos y a la List creada anteriormente
                consumosAdapter = new ConsumosAdapter(getContext(),consumosList);
                recyclerView.setAdapter(consumosAdapter);
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

    // Añadimos un nuevo Consumo
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addConsumo(View view){
        // Instancia del archivo XML de diseño
        View addLayout = LayoutInflater.from(getContext()).inflate(R.layout.consumo_add, null);
        // Asignamos las id a las variables
        TextView Id = addLayout.findViewById(R.id.id_consumo);
        EditText Gasoleo = addLayout.findViewById(R.id.add_gasoleo);
        EditText Aceite_Motor = addLayout.findViewById(R.id.add_aceite_motor);
        EditText Aceite_Hidraulico = addLayout.findViewById(R.id.add_aceite_hidraulico);
        EditText Aceite_Trasmisiones = addLayout.findViewById(R.id.add_aceite_transmisiones);
        EditText Valvulina = addLayout.findViewById(R.id.add_valvulina);
        EditText Grasas = addLayout.findViewById(R.id.add_grasas);
        EditText Fecha_recepcion = addLayout.findViewById(R.id.add_fecha_recepcion);
        TextView Id_empleado = addLayout.findViewById(R.id.add_consumo_id_empleado);

        // Ocultamos el teclado
        Fecha_recepcion.setInputType(InputType.TYPE_NULL);

        // Cuando se pulse el campo Fecha voladura, llamamos al método showDateTimeDialog
        Fecha_recepcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(Fecha_recepcion);
            }
        });

        // Cambio el contenido del textview por el id del Usuario
        Id_empleado.setText(idUsuario);

        // Dialogo Añadir
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setBackground(getContext().getResources().getDrawable(R.drawable.alert_dialog,null));
        builder.setView(addLayout);
        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Obtenemos los valores que ha introducido el usuario
                final String gasoleo = Gasoleo.getText().toString();
                final String aceite_motor = Aceite_Motor.getText().toString();
                final String aceite_hidraulico = Aceite_Hidraulico.getText().toString();
                final String aceite_transmisiones = Aceite_Trasmisiones.getText().toString();
                final String valvulina =  Valvulina.getText().toString();
                final String grasas = Grasas.getText().toString();
                final String fecha_recepcion = Fecha_recepcion.getText().toString();
                final String id_empleado = idUsuario;

                // Comprobamos que no estén vacíos
                if(gasoleo.isEmpty() || aceite_motor.isEmpty() || aceite_hidraulico.isEmpty() ||
                        aceite_transmisiones.isEmpty() || valvulina.isEmpty() || grasas.isEmpty() ||
                        fecha_recepcion.isEmpty()){
                    Toast.makeText(getContext(), "Algunos campos están vacíos", Toast.LENGTH_SHORT).show();
                } else {
                    // Hacemos una petición post para añadir un nuevo consumo
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.ADD_CONSUMO_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Mensaje desde archivo php
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                            //Recargamos
                            Navigation.findNavController(view).navigate(R.id.action_consumosListFragment_self);
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
                            params.put("gasoleo",gasoleo);
                            params.put("aceite_motor",aceite_motor);
                            params.put("aceite_hidraulico",aceite_hidraulico);
                            params.put("aceite_transmisiones",aceite_transmisiones);
                            params.put("valvulina",valvulina);
                            params.put("grasas",grasas);
                            params.put("fecha_recepcion",fecha_recepcion);
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

    // Dialogo de fecha y hora
    private void showDateTimeDialog(EditText fecha_recepcion) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        fecha_recepcion.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(getContext(),timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();

            }
        };

        new DatePickerDialog(getContext(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}