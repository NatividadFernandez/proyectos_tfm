package com.pms.cantera.voladura;

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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class VoladurasListFragment extends Fragment {

    // Declaración de variables
    private RecyclerView recyclerView;
    private VoladurasAdapter voladurasAdapter;
    private List<Voladuras> voladurasList;
    private FloatingActionButton add_voladura;
    private String idUsuario;

    // Se carga la vista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voladuras_list, container, false);
    }

    // Inicializamos las clases una vez que se sabe que su jerarquía de vista se ha creado por completo
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Accedemos al idUsuario desde las preferencias
        SharedPreferences preferencesss = getContext().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        idUsuario = preferencesss.getString("idUsuario","id");

        //Asignamos las id a las variables
        recyclerView = view.findViewById(R.id.recyclerList);
        // Establecemos el diseño de los contenidos, es decir, la lista de vistas repetidas en la vista del recyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//getActivity()

        // creamos un ArrayList
        voladurasList = new ArrayList<>();

        //Leemos todas las voladuras
        loadAllVoladuras();

        // Botón añadir voladuras
        add_voladura = (FloatingActionButton) view.findViewById(R.id.add_btn_voladura);

        // Cuando se pulse el botón añadir voladura, llamará al método addVoladura
        add_voladura.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                addVoladura(view);
            }
        });
    }

    // Método que leerá todas la voladuras existentes en la basade de datos
    private void loadAllVoladuras() {
        // Consulta a la base de datos, para que nos devuelva tos las voladuras
        JsonArrayRequest request = new JsonArrayRequest(Urls.SHOW_ALL_VOLADURA_DATA_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Recorremos el array de voladuras
                for(int i = 0; i <response.length(); i++){
                    try {
                        // Creo un objetojson, accedo a los valores y se lo a signo a las variables
                        JSONObject object = response.getJSONObject(i);
                        Integer id = object.getInt("id");
                        String localizacion = object.getString("localizacion").trim();
                        Double m2_superficie = object.getDouble("m2_superficie");
                        String malla_perforacion = object.getString("malla_perforacion").trim();
                        Double profundidad_barrenos = object.getDouble("profundidad_barrenos");
                        Integer numero_barrenos = object.getInt("numero_barrenos");
                        Double kg_explosivo = object.getDouble("kg_explosivo");
                        Double precio = object.getDouble("precio");
                        Double piedra_bruta = object.getDouble("piedra_bruta");
                        String fecha_voladura = object.getString("fecha_voladura").trim();
                        Integer id_empleado = object.getInt("id_empleado");

                        // Creación objeto Voladura
                        Voladuras voladuras = new Voladuras();
                        voladuras.setId(id);
                        voladuras.setLocalizacion(localizacion);
                        voladuras.setM2_superficie(m2_superficie);
                        voladuras.setMalla_perforacion(malla_perforacion);
                        voladuras.setProfundidad_barrenos(profundidad_barrenos);
                        voladuras.setNumero_barrenos(numero_barrenos);
                        voladuras.setKg_explosivo(kg_explosivo);
                        voladuras.setPrecio(precio);
                        voladuras.setPiedra_bruta(piedra_bruta);
                        voladuras.setFecha_voladura(fecha_voladura);
                        voladuras.setId_empleado(id_empleado);

                        // Añadimos las voladuras a un List
                        voladurasList.add(voladuras);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // Objeto Adapter, pasamos por parámetro getContext() que se refiere a la clase
                // en la que nos encontramos y a la List creada anteriormente
                voladurasAdapter = new VoladurasAdapter(getContext(),voladurasList);
                recyclerView.setAdapter(voladurasAdapter);
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

    // Método que añade una nueva voladura
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addVoladura(View view){
        // Instancia del archivo XML de diseño
        View editLayout = LayoutInflater.from(getContext()).inflate(R.layout.voladura_add, null);
        // Asignamos las id a las variables
        TextView Id = editLayout.findViewById(R.id.add_id);
        EditText Localizacion = editLayout.findViewById(R.id.add_localizacion);
        EditText M2_superficie = editLayout.findViewById(R.id.add_m2_superficie);
        EditText Malla_perforacion = editLayout.findViewById(R.id.add_malla_perforacion);
        EditText Profundidad_barrenos = editLayout.findViewById(R.id.add_profundidad_barrenos);
        EditText Numero_barrenos = editLayout.findViewById(R.id.add_numero_barrenos);
        EditText Kg_explosivo = editLayout.findViewById(R.id.add_kg_explosivo);
        EditText Precio = editLayout.findViewById(R.id.add_precio);
        EditText Piedra_bruta = editLayout.findViewById(R.id.add_piedra_bruta);
        EditText Fecha_voladura = editLayout.findViewById(R.id.add_fecha_voladura);
        TextView Id_empleado = editLayout.findViewById(R.id.add_id_empleado);

        // Ocultamos el teclado
        Fecha_voladura.setInputType(InputType.TYPE_NULL);

        // Cuando se pulse el campo Fecha voladura, llamamos al método showDateTimeDialog
        Fecha_voladura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(Fecha_voladura);
            }
        });

        // Cambio el contenido del textview por el id del Usuario
        Id_empleado.setText(idUsuario);

        // Dialogo Añadir
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setBackground(getContext().getResources().getDrawable(R.drawable.alert_dialog,null));
        builder.setView(editLayout);
        // Si se pulsa Añadir
        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Obtenemos los valores que ha introducido el usuario
                final String localizacion = Localizacion.getText().toString();
                final String m2_superficie = M2_superficie.getText().toString();
                final String malla_perforacion = Malla_perforacion.getText().toString();
                final String profundidad_barrenos = Profundidad_barrenos.getText().toString();
                final String numero_barrenos =  Numero_barrenos.getText().toString();
                final String kg_explosivo = Kg_explosivo.getText().toString();
                final String precio = Precio.getText().toString();
                final String piedra_bruta = Piedra_bruta.getText().toString();
                final String fecha_voladura = Fecha_voladura.getText().toString();
                final String id_empleado = idUsuario;

                // Comprobamos que no estén vacíos
                if(localizacion.isEmpty() || m2_superficie.isEmpty() || malla_perforacion.isEmpty() ||
                        profundidad_barrenos.isEmpty() || numero_barrenos.isEmpty() || kg_explosivo.isEmpty() ||
                        precio.isEmpty() || piedra_bruta.isEmpty() || fecha_voladura.isEmpty()){
                    Toast.makeText(getContext(), "Algunos campos están vacíos", Toast.LENGTH_SHORT).show();

                } else {
                    // Hacemos una petición post para añadir la nueva voladura
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.ADD_VOLADURA_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Mensaje desde archivo php
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                            //Recargamos
                            Navigation.findNavController(view).navigate(R.id.action_voladurasListFragment_self);
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
                            params.put("localizacion",localizacion);
                            params.put("m2_superficie",m2_superficie);
                            params.put("malla_perforacion",malla_perforacion);
                            params.put("profundidad_barrenos",profundidad_barrenos);
                            params.put("numero_barrenos",numero_barrenos);
                            params.put("kg_explosivo",kg_explosivo);
                            params.put("precio",precio);
                            params.put("piedra_bruta",piedra_bruta);
                            params.put("fecha_voladura",fecha_voladura);
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
        // Si se pulsa cancelar
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Cerramos el dialogo
                dialogInterface.dismiss();
            }
        });
        // Se muestra el dialogo
        builder.show();
    }

    // Dialogo de fecha y hora
    private void showDateTimeDialog(EditText fecha_voladura) {
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
                        fecha_voladura.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(getContext(),timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();

            }
        };

        new DatePickerDialog(getContext(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}