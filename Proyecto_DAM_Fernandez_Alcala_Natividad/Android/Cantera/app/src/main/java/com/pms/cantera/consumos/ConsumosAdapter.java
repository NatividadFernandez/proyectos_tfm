package com.pms.cantera.consumos;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

public class ConsumosAdapter extends RecyclerView.Adapter<ConsumosAdapter.ConsumosHolder> {

    // Declaración de variables
    Context context;
    List<Consumos> consumosList;
    // Formaterar los números reales y enteros
    NumberFormat nf = new DecimalFormat("#.####");

    // Constructor del Adapter
    public ConsumosAdapter(Context context, List<Consumos> consumosList) {
        this.context = context;
        this.consumosList = consumosList;
    }

    @NotNull
    @Override
    public ConsumosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View consumosLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.consumos_list,parent,false);
        return new ConsumosAdapter.ConsumosHolder(consumosLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsumosHolder holder, int position) {
        Consumos consumos = consumosList.get(position);
        holder.id.setText(nf.format(consumos.getId()));
        holder.gasoleo.setText(nf.format(consumos.getGasoleo()));
        holder.aceite_motor.setText(nf.format(consumos.getAceite_motor()));
        holder.aceite_hidraulico.setText(nf.format(consumos.getAceite_hidraulico()));
        holder.aceite_transmisiones.setText(nf.format(consumos.getAceite_transmisiones()));
        holder.valvulina.setText(nf.format(consumos.getValvulina()));
        holder.grasas.setText(nf.format(consumos.getGrasas()));
        holder.fecha_recepcion.setText(consumos.getFecha_recepcion());
        holder.id_empleado.setText(nf.format(consumos.getId_empleado()));

        // Obtener datos de las preferencias
        SharedPreferences preferencesss = context.getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        String idUsuario = preferencesss.getString("idUsuario","holo");

        // Boton Mostrar
        holder.btnMostrarConsumo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                View showLayout = LayoutInflater.from(context).inflate(R.layout.consumos_show, null);
                TextView Id = showLayout.findViewById(R.id.show_id_consumo);
                TextView Gasoleo = showLayout.findViewById(R.id.show_gasoleo);
                TextView Aceite_Motor = showLayout.findViewById(R.id.show_aceite_motor);
                TextView Aceite_Hidraulico = showLayout.findViewById(R.id.show_aceite_hidraulico);
                TextView Aceite_Transmisiones = showLayout.findViewById(R.id.show_aceite_transmisiones);
                TextView Valvulina = showLayout.findViewById(R.id.show_valvulina);
                TextView Grasas = showLayout.findViewById(R.id.show_grasas);
                TextView Fecha_recepcion = showLayout.findViewById(R.id.show_fecha_recepcion);
                TextView Id_empleado = showLayout.findViewById(R.id.show_consumo_id_empleado);

                // Cambiamos el contenido de los textview
                Id.setText(nf.format(consumos.getId()));
                Gasoleo.setText(nf.format(consumos.getGrasas()));
                Aceite_Motor.setText(nf.format(consumos.getAceite_motor()));
                Aceite_Hidraulico.setText(nf.format(consumos.getAceite_hidraulico()));
                Aceite_Transmisiones.setText(nf.format(consumos.getAceite_transmisiones()));
                Valvulina.setText(nf.format(consumos.getValvulina()));
                Grasas.setText(nf.format(consumos.getGrasas()));
                Fecha_recepcion.setText(consumos.getFecha_recepcion());
                Id_empleado.setText(idUsuario);

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setBackground(context.getResources().getDrawable(R.drawable.alert_dialog,null));
                builder.setView(showLayout);
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
                        builder.setTitle("¿Borrar Consumo?");
                        builder.setMessage("¿Seguro que quieres borrar los productos con la fecha: "+consumos.getFecha_recepcion()+" ?");
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.DELETE_CONSUMO_URL, new Response.Listener<String>() {
                                    // Método que elimina un consumo en caso de que los datos obtenidos sean correctos
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject object = new JSONObject(response);
                                            // Obtenemos el estado que nos devuelve el json
                                            String check = object.getString("estado");

                                            // Si el estado es borrar, borramos la posicion del item
                                            if(check.equals("borrar")){
                                                BorrarConsumo(position);
                                                Toast.makeText(context, "¡Consumo borrado con éxito!", Toast.LENGTH_SHORT).show();
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
                                        params.put("id",consumos.getId().toString());
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
        holder.btnEditarConsumo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                View editLayout = LayoutInflater.from(context).inflate(R.layout.consumo_edit, null);
                TextView Id = editLayout.findViewById(R.id.edt_id_consumo);
                EditText Gasoleo = editLayout.findViewById(R.id.edt_gasoleo);
                EditText Aceite_Motor = editLayout.findViewById(R.id.edt_aceite_motor);
                EditText Aceite_Hidraulico = editLayout.findViewById(R.id.edt_aceite_hidraulico);
                EditText Aceite_Transmisiones = editLayout.findViewById(R.id.edt_aceite_transmisiones);
                EditText Valvulina = editLayout.findViewById(R.id.edt_valvulina);
                EditText Grasas = editLayout.findViewById(R.id.edt_grasas);
                EditText Fecha_recepcion = editLayout.findViewById(R.id.edt_fecha_recepcion);
                TextView Id_empleado = editLayout.findViewById(R.id.edt_consumo_id_empleado);

                // Null para que el teclado se oculte
                Fecha_recepcion.setInputType(InputType.TYPE_NULL);

                // Al pulsar fecha voladura nos aparece el dialogo para elegir el dia y hora
                Fecha_recepcion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDateTimeDialog(Fecha_recepcion);
                    }
                });


                // Cambiamos el contenido de los textview
                Id.setText(nf.format(consumos.getId()));
                Gasoleo.setText(nf.format(consumos.getGasoleo()));
                Aceite_Motor.setText(nf.format(consumos.getAceite_motor()));
                Aceite_Hidraulico.setText(nf.format(consumos.getAceite_hidraulico()));
                Aceite_Transmisiones.setText(nf.format(consumos.getAceite_transmisiones()));
                Valvulina.setText(nf.format(consumos.getValvulina()));
                Grasas.setText(nf.format(consumos.getGrasas()));
                Fecha_recepcion.setText(consumos.getFecha_recepcion());
                Id_empleado.setText(idUsuario);

                /*Log.i("Fecha", consumos.getFecha_recepcion());
                Log.i("ID USUARIO ADAPTER", idUsuario);*/

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setBackground(context.getResources().getDrawable(R.drawable.alert_dialog,null));
                builder.setView(editLayout);
                builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String id = Id.getText().toString();
                        final String gasoleo = Gasoleo.getText().toString();
                        final String aceite_motor = Aceite_Motor.getText().toString();
                        final String aceite_hidraulico = Aceite_Hidraulico.getText().toString();
                        final String aceite_transmisiones = Aceite_Transmisiones.getText().toString();
                        final String valvulina =  Valvulina.getText().toString();
                        final String grasas = Grasas.getText().toString();
                        final String fecha_recepcion = Fecha_recepcion.getText().toString();
                        final String id_empleado = Id_empleado.getText().toString();

                        // Comprobar datos vacios
                        if(gasoleo.isEmpty() || aceite_motor.isEmpty() || aceite_hidraulico.isEmpty() ||
                                aceite_transmisiones.isEmpty() || valvulina.isEmpty() || grasas.isEmpty() ||
                                fecha_recepcion.isEmpty()){
                            Toast.makeText(context, "Algunos campos están vacíos", Toast.LENGTH_SHORT).show();
                        } else {
                            // Actualizamos
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.EDIT_CONSUMO_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                    //Recargamos
                                    Navigation.findNavController(view).navigate(R.id.action_consumosListFragment_self);
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
        return consumosList.size();
    }

    public class ConsumosHolder extends RecyclerView.ViewHolder{

        TextView id,gasoleo,aceite_motor,aceite_hidraulico,aceite_transmisiones,valvulina,grasas,fecha_recepcion,id_empleado;
        Button btnEditarConsumo,btnMostrarConsumo;

        public ConsumosHolder(@NonNull View itemView) {
            super(itemView);
            // id de los view del xml
            id = itemView.findViewById(R.id.id_consumo);
            gasoleo = itemView.findViewById(R.id.gasoleo);
            aceite_motor = itemView.findViewById(R.id.aceite_motor);
            aceite_hidraulico = itemView.findViewById(R.id.aceite_hidraulico);
            aceite_transmisiones = itemView.findViewById(R.id.aceite_transmisiones);
            valvulina = itemView.findViewById(R.id.valvulina);
            grasas = itemView.findViewById(R.id.grasas);
            fecha_recepcion = itemView.findViewById(R.id.fecha_recepcion);
            id_empleado = itemView.findViewById(R.id.consumo_id_empleado);
            btnMostrarConsumo = itemView.findViewById(R.id.btnMostrarConsumo);
            btnEditarConsumo = itemView.findViewById(R.id.btnEditarConsumo);
        }
    }

    // Borramos el item del layout
    public void BorrarConsumo (int item){
        consumosList.remove(item);
        notifyItemRemoved(item);
    }

    // Mostramos el dialogo de fecha y hora
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

                new TimePickerDialog(context,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();

            }
        };

        new DatePickerDialog(context,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
