package com.pms.cantera.productos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosHolder> {

    // Declaración de variables
    Context context;
    List<Productos> productosList;
    // Formaterar los números reales y enteros
    NumberFormat nf = new DecimalFormat("#.####");

    // Constructor del Adapter
    public ProductosAdapter(Context context, List<Productos> productosList) {
        this.context = context;
        this.productosList = productosList;
    }

    @NonNull
    @Override
    public ProductosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productosLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.productos_list,parent,false);
        return new ProductosHolder(productosLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosHolder holder, int position) {
        Productos productos = productosList.get(position);
        holder.id.setText(nf.format(productos.getId()));
        holder.arena_06.setText(nf.format(productos.getArena_06()));
        holder.grava_612.setText(nf.format(productos.getGrava_612()));
        holder.grava_1220.setText(nf.format(productos.getGrava_1220()));
        holder.grava_2023.setText(nf.format(productos.getGrava_2023()));
        holder.rechazo.setText(nf.format(productos.getRechazo()));
        holder.zahorra.setText(nf.format(productos.getZahorra()));
        holder.escollera.setText(nf.format(productos.getEscollera()));
        holder.mamposteria.setText(nf.format(productos.getMamposteria()));
        holder.voladura_fecha.setText(productos.getFecha_voladura());
        holder.id_empleado.setText(nf.format(productos.getId_empleado()));

        // Obtener datos de las preferencias
        SharedPreferences preferencesss = context.getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        String idUsuario = preferencesss.getString("idUsuario","holo");

        // Boton Mostrar
        holder.btnMostrarProducto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                View editLayout = LayoutInflater.from(context).inflate(R.layout.productos_show, null);
                TextView Id = editLayout.findViewById(R.id.show_id_producto);
                TextView Arena06 = editLayout.findViewById(R.id.show_arena_06);
                TextView Grava612 = editLayout.findViewById(R.id.show_grava_612);
                TextView Grava1220 = editLayout.findViewById(R.id.show_grava_1220);
                TextView Grava2023 = editLayout.findViewById(R.id.show_grava_2023);
                TextView Rechazo = editLayout.findViewById(R.id.show_rechazo);
                TextView Zahorra = editLayout.findViewById(R.id.show_zahorra);
                TextView Escollera = editLayout.findViewById(R.id.show_escollera);
                TextView Mamposteria = editLayout.findViewById(R.id.show_mamposteria);
                TextView Fecha_voladura = editLayout.findViewById(R.id.show_voladura_fecha);
                TextView Id_empleado = editLayout.findViewById(R.id.show_producto_id_empleado);

                // Cambiamos el contenido de los textview
                Id.setText(nf.format(productos.getId()));
                Arena06.setText(nf.format(productos.getArena_06()));
                Grava612.setText(nf.format(productos.getGrava_612()));
                Grava1220.setText(nf.format(productos.getGrava_1220()));
                Grava2023.setText(nf.format(productos.getGrava_2023()));
                Rechazo.setText(nf.format(productos.getRechazo()));
                Zahorra.setText(nf.format(productos.getZahorra()));
                Escollera.setText(nf.format(productos.getEscollera()));
                Mamposteria.setText(nf.format(productos.getMamposteria()));
                Fecha_voladura.setText(productos.getFecha_voladura());

                Log.i("ID USUARIO ADAPTER", idUsuario);

                Id_empleado.setText(idUsuario);

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
                        builder.setTitle("¿Borrar Producto?");
                        builder.setMessage("¿Seguro que quieres borrar los productos con la fecha: "+productos.getFecha_voladura()+" ?");
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.DELETE_PRODUCTO_URL, new Response.Listener<String>() {
                                    // Método que elimina una voladura en caso de que los datos obtenidos sean correctos
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject object = new JSONObject(response);
                                            // Obtenemos el estado que nos devuelve el json
                                            String check = object.getString("estado");
                                            // Si el estado es borrar, borramos la posicion del item
                                            if(check.equals("borrar")){
                                                BorrarProducto(position);
                                                Toast.makeText(context, "¡Productos borrados con éxito!", Toast.LENGTH_SHORT).show();
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
                                        params.put("id",productos.getId().toString());
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
        holder.btnEditarProducto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                View editLayout = LayoutInflater.from(context).inflate(R.layout.producto_edit, null);
                TextView Id = editLayout.findViewById(R.id.edt_id_producto);
                EditText Arena06 = editLayout.findViewById(R.id.edt_arena_06);
                EditText Grava612 = editLayout.findViewById(R.id.edt_grava_612);
                EditText Grava1220 = editLayout.findViewById(R.id.edt_grava_1220);
                EditText Grava2023 = editLayout.findViewById(R.id.edt_grava_2023);
                EditText Rechazo = editLayout.findViewById(R.id.edt_rechazo);
                EditText Zahorra = editLayout.findViewById(R.id.edt_zahorra);
                EditText Escollera = editLayout.findViewById(R.id.edt_escollera);
                EditText Mamposteria = editLayout.findViewById(R.id.edt_mamposteria);
                EditText Fecha_voladura = editLayout.findViewById(R.id.edt_voladura_fecha);
                TextView Id_empleado = editLayout.findViewById(R.id.edt_producto_id_empleado);
                // No se puede cambiar la fecha
                Fecha_voladura.setEnabled(false);
                /*Fecha_voladura.setInputType(InputType.TYPE_NULL);

                Fecha_voladura.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDateTimeDialog(Fecha_voladura);
                    }
                });*/

                // Cambiamos el contenido de los textview
                Id.setText(nf.format(productos.getId()));
                Arena06.setText(nf.format(productos.getArena_06()));
                Grava612.setText(nf.format(productos.getGrava_612()));
                Grava1220.setText(nf.format(productos.getGrava_1220()));
                Grava2023.setText(nf.format(productos.getGrava_2023()));
                Rechazo.setText(nf.format(productos.getRechazo()));
                Zahorra.setText(nf.format(productos.getZahorra()));
                Escollera.setText(nf.format(productos.getEscollera()));
                Mamposteria.setText(nf.format(productos.getMamposteria()));
                Fecha_voladura.setText(productos.getFecha_voladura());
                Id_empleado.setText(idUsuario);

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setBackground(context.getResources().getDrawable(R.drawable.alert_dialog,null));
                builder.setView(editLayout);
                builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String id = Id.getText().toString();
                        final String arena_06 = Arena06.getText().toString();
                        final String grava_612 = Grava612.getText().toString();
                        final String grava_1220 = Grava1220.getText().toString();
                        final String grava_2023 = Grava2023.getText().toString();
                        final String rechazo =  Rechazo.getText().toString();
                        final String zahorra = Zahorra.getText().toString();
                        final String escollera = Escollera.getText().toString();
                        final String mamposteria = Mamposteria.getText().toString();
                        final String fecha_voladura = Fecha_voladura.getText().toString();
                        final String id_empleado = Id_empleado.getText().toString();

                        // Comprobar datos vacios
                        if(arena_06.isEmpty() || grava_612.isEmpty() || grava_1220.isEmpty() ||
                                grava_2023.isEmpty() || rechazo.isEmpty() || zahorra.isEmpty() ||
                                escollera.isEmpty() || mamposteria.isEmpty() || fecha_voladura.isEmpty()){
                            Toast.makeText(context, "Algunos campos están vacíos", Toast.LENGTH_SHORT).show();
                        } else {
                            // Actualizamos
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.EDIT_PRODUCTO_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                    //Recargamos
                                    //Recargamos
                                    Navigation.findNavController(view).navigate(R.id.action_productosListFragment_self);
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
        return productosList.size();
    }

    public class ProductosHolder extends RecyclerView.ViewHolder{

        TextView id,arena_06,grava_612,grava_1220,grava_2023,rechazo,zahorra,escollera,mamposteria,voladura_fecha,id_empleado;
        Button btnEditarProducto,btnMostrarProducto;

        public ProductosHolder(@NonNull View itemView) {
            super(itemView);
            // id de los view del xml
            id = itemView.findViewById(R.id.id_producto);
            arena_06 = itemView.findViewById(R.id.arena_06);
            grava_612 = itemView.findViewById(R.id.grava_612);
            grava_1220 = itemView.findViewById(R.id.grava_1220);
            grava_2023 = itemView.findViewById(R.id.grava_2023);
            rechazo = itemView.findViewById(R.id.rechazo);
            zahorra = itemView.findViewById(R.id.zahorra);
            escollera = itemView.findViewById(R.id.escollera);
            mamposteria = itemView.findViewById(R.id.mamposteria);
            voladura_fecha = itemView.findViewById(R.id.voladura_fecha);
            id_empleado = itemView.findViewById(R.id.producto_id_empleado);
            btnMostrarProducto = itemView.findViewById(R.id.btnMostrarProducto);
            btnEditarProducto = itemView.findViewById(R.id.btnEditarProducto);
        }
    }

    // Borramos el item del layout
    public void BorrarProducto (int item){
        productosList.remove(item);
        notifyItemRemoved(item);
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

                new TimePickerDialog(context,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();

            }
        };

        new DatePickerDialog(context,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
