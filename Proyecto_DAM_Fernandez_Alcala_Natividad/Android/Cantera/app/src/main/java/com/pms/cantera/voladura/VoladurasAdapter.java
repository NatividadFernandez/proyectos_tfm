package com.pms.cantera.voladura;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.pms.cantera.R;
import com.pms.cantera.Urls;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoladurasAdapter extends RecyclerView.Adapter<VoladurasAdapter.VoladurasHolder> {

    // Declaración de variables
    Context context;
    List<Voladuras> voladurasList;
    // Formaterar los números reales y enteros
    NumberFormat nf = new DecimalFormat("#.####");

    // Constructor del Adapter
    public VoladurasAdapter(Context context, List<Voladuras> voladurasList) {
        this.context = context;
        this.voladurasList = voladurasList;
    }

    @NonNull
    @Override
    public VoladurasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View voladurasLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.voladuras_list,parent,false);
        return new VoladurasHolder(voladurasLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull VoladurasHolder holder, int position) {
        Voladuras voladuras = voladurasList.get(position);
        holder.id.setText(nf.format(voladuras.getId()));
        holder.localizacion.setText(voladuras.getLocalizacion());
        holder.m2_superficie.setText(nf.format(voladuras.getM2_superficie()));
        holder.malla_perforacion.setText(voladuras.getMalla_perforacion());
        holder.profundidad_barrenos.setText(nf.format(voladuras.getProfundidad_barrenos()));
        holder.numero_barrenos.setText(nf.format(voladuras.getNumero_barrenos()));
        holder.kg_explosivo.setText(nf.format(voladuras.getKg_explosivo()));
        holder.precio.setText(nf.format(voladuras.getPrecio()));
        holder.piedra_bruta.setText(nf.format(voladuras.getPiedra_bruta()));
        holder.fecha_voladura.setText(voladuras.getFecha_voladura());
        holder.id_empleado.setText(nf.format(voladuras.getId_empleado()));

        // Obtener datos de las preferencias
        SharedPreferences preferencesss = context.getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        String idUsuario = preferencesss.getString("idUsuario","holo");

        // Boton Mostrar
        holder.btnMostrarVoladura.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                View editLayout = LayoutInflater.from(context).inflate(R.layout.voladuras_show, null);
                TextView Id = editLayout.findViewById(R.id.show_id);
                TextView Localizacion = editLayout.findViewById(R.id.show_localizacion);
                TextView M2_superficie = editLayout.findViewById(R.id.show_m2_superficie);
                TextView Malla_perforacion = editLayout.findViewById(R.id.show_malla_perforacion);
                TextView Profundidad_barrenos = editLayout.findViewById(R.id.show_profundidad_barrenos);
                TextView Numero_barrenos = editLayout.findViewById(R.id.show_numero_barrenos);
                TextView Kg_explosivo = editLayout.findViewById(R.id.show_kg_explosivo);
                TextView Precio = editLayout.findViewById(R.id.show_precio);
                TextView Piedra_bruta = editLayout.findViewById(R.id.show_piedra_bruta);
                TextView Fecha_voladura = editLayout.findViewById(R.id.show_fecha_voladura);
                TextView Id_empleado = editLayout.findViewById(R.id.show_id_empleado);


                // Cambiamos el contenido de los textview
                Id.setText(nf.format(voladuras.getId()));
                Localizacion.setText(voladuras.getLocalizacion());
                M2_superficie.setText(nf.format(voladuras.getM2_superficie()));
                Malla_perforacion.setText(voladuras.getMalla_perforacion());
                Profundidad_barrenos.setText(nf.format(voladuras.getProfundidad_barrenos()));
                Numero_barrenos.setText(nf.format(voladuras.getNumero_barrenos()));
                Kg_explosivo.setText(nf.format(voladuras.getKg_explosivo()));
                Precio.setText(nf.format(voladuras.getPrecio()));
                Piedra_bruta.setText(nf.format(voladuras.getPiedra_bruta()));
                Fecha_voladura.setText(voladuras.getFecha_voladura());
                Id_empleado.setText(idUsuario);

                //Log.i("ID USUARIO ADAPTER: " , idUsuario);

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setBackground(context.getResources().getDrawable(R.drawable.alert_dialog,null));
                builder.setView(editLayout);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                        builder.setBackground(context.getResources().getDrawable(R.drawable.alert_dialog,null));
                        builder.setTitle("¿Borrar Voladura?");
                        builder.setMessage("¿Seguro que quieres borrar la voladura con la fecha: "+voladuras.getFecha_voladura()+" ?");
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Eliminar
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.DELETE_VOLADURA_URL, new Response.Listener<String>() {
                                    // Método que elimina una voladura en caso de que los datos obtenidos sean correctos
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject object = new JSONObject(response);
                                            // Obtenemos el estado que nos devuelve el json
                                            String check = object.getString("estado");
                                            // Si el estado es borrar, borramos la posicion del item
                                            if(check.equals("borrar")){
                                                BorrarVoladura(position);
                                                Toast.makeText(context, "¡Voladura borrada con éxito!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    // Método que notifica si hay un error
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String,String> params = new HashMap<>();
                                        params.put("id",voladuras.getId().toString());
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                requestQueue.add(stringRequest);
                            }
                        });
                        builder.show();
                    }
                });
                builder.show();
            }
        });

        // Boton Editar
        holder.btnEditarVoladura.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                View editLayout = LayoutInflater.from(context).inflate(R.layout.voladura_edit, null);
                TextView Id = editLayout.findViewById(R.id.edt_id);
                EditText Localizacion = editLayout.findViewById(R.id.edt_localizacion);
                EditText M2_superficie = editLayout.findViewById(R.id.edt_m2_superficie);
                EditText Malla_perforacion = editLayout.findViewById(R.id.edt_malla_perforacion);
                EditText Profundidad_barrenos = editLayout.findViewById(R.id.edt_profundidad_barrenos);
                EditText Numero_barrenos = editLayout.findViewById(R.id.edt_numero_barrenos);
                EditText Kg_explosivo = editLayout.findViewById(R.id.edt_kg_explosivo);
                EditText Precio = editLayout.findViewById(R.id.edt_precio);
                EditText Piedra_bruta = editLayout.findViewById(R.id.edt_piedra_bruta);
                EditText Fecha_voladura = editLayout.findViewById(R.id.edt_fecha_voladura);
                TextView Id_empleado = editLayout.findViewById(R.id.edt_id_empleado);

                // Null para que el teclado se oculte
                Fecha_voladura.setInputType(InputType.TYPE_NULL);

                // Al pulsar fecha voladura nos aparece el dialogo para elegir el dia y hora
                Fecha_voladura.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDateTimeDialog(Fecha_voladura);
                    }
                });

                // Cambiamos el contenido de los textview
                Id.setText(nf.format(voladuras.getId()));
                Localizacion.setText(voladuras.getLocalizacion());
                M2_superficie.setText(nf.format(voladuras.getM2_superficie()));
                Malla_perforacion.setText(voladuras.getMalla_perforacion());
                Profundidad_barrenos.setText(nf.format(voladuras.getProfundidad_barrenos()));
                Numero_barrenos.setText(nf.format(voladuras.getNumero_barrenos()));
                Kg_explosivo.setText(nf.format(voladuras.getKg_explosivo()));
                Precio.setText(nf.format(voladuras.getPrecio()));
                Piedra_bruta.setText(nf.format(voladuras.getPiedra_bruta()));
                Fecha_voladura.setText(voladuras.getFecha_voladura());
                Id_empleado.setText(idUsuario);

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setBackground(context.getResources().getDrawable(R.drawable.alert_dialog,null));
                builder.setView(editLayout);
                builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String id = Id.getText().toString();
                        final String localizacion = Localizacion.getText().toString();
                        final String m2_superficie = M2_superficie.getText().toString();
                        final String malla_perforacion = Malla_perforacion.getText().toString();
                        final String profundidad_barrenos = Profundidad_barrenos.getText().toString();
                        final String numero_barrenos =  Numero_barrenos.getText().toString();
                        final String kg_explosivo = Kg_explosivo.getText().toString();
                        final String precio = Precio.getText().toString();
                        final String piedra_bruta = Piedra_bruta.getText().toString();
                        final String fecha_voladura = Fecha_voladura.getText().toString();
                        final String id_empleado = Id_empleado.getText().toString();

                        if(localizacion.isEmpty() || m2_superficie.isEmpty() || malla_perforacion.isEmpty() ||
                                profundidad_barrenos.isEmpty() || numero_barrenos.isEmpty() || kg_explosivo.isEmpty() ||
                                precio.isEmpty() || piedra_bruta.isEmpty() || fecha_voladura.isEmpty()){
                            Toast.makeText(context, "Algunos campos están vacíos", Toast.LENGTH_SHORT).show();
                        } else {
                            // Actualizar
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.EDIT_VOLADURA_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                    //Recargamos
                                    Navigation.findNavController(view).navigate(R.id.action_voladurasListFragment_self);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String> params = new HashMap<>();
                                    // Enviamos datos
                                    params.put("id",id);
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

                            RequestQueue requestQueue = Volley.newRequestQueue(context);
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
            }
        });

    }

    @Override
    public int getItemCount() {
        return voladurasList.size();
    }

    public class VoladurasHolder extends RecyclerView.ViewHolder {
        TextView id,localizacion,m2_superficie,profundidad_barrenos,kg_explosivo,precio,piedra_bruta,malla_perforacion,numero_barrenos,fecha_voladura,id_empleado;
        Button btnEditarVoladura,btnMostrarVoladura;

        public VoladurasHolder(@NonNull View itemView) {
            super(itemView);
            // id de los view del xml
            id = itemView.findViewById(R.id.id);
            localizacion = itemView.findViewById(R.id.localizacion);
            m2_superficie = itemView.findViewById(R.id.m2_superficie);
            malla_perforacion = itemView.findViewById(R.id.malla_perforacion);
            profundidad_barrenos = itemView.findViewById(R.id.profundidad_barrenos);
            numero_barrenos = itemView.findViewById(R.id.numero_barrenos);
            kg_explosivo = itemView.findViewById(R.id.kg_explosivo);
            precio = itemView.findViewById(R.id.precio);
            piedra_bruta = itemView.findViewById(R.id.piedra_bruta);
            fecha_voladura = itemView.findViewById(R.id.fecha_voladura);
            id_empleado = itemView.findViewById(R.id.id_empleado);
            btnMostrarVoladura =  itemView.findViewById(R.id.btnMostrarVoladura);
            btnEditarVoladura = itemView.findViewById(R.id.btnEditarVoladura);
        }
    }

    // Borramos el item del layout
    public void BorrarVoladura (int item){
        voladurasList.remove(item);
        notifyItemRemoved(item);
    }

    // Mostramos el dialogo de fecha y hora
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

                new TimePickerDialog(context,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();

            }
        };

        new DatePickerDialog(context,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }



}
