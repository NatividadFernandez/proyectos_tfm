package com.pms.cantera.consumos.stockconsumos;

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
import com.pms.cantera.consumos.Consumos;

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

public class StockConsumosAdapter extends RecyclerView.Adapter<StockConsumosAdapter.StockConsumosHolder> {

    // Declaración de variables
    Context context;
    List<StockConsumos> stockConsumosList;
    // Formaterar los números reales y enteros
    NumberFormat nf = new DecimalFormat("#.####");

    public StockConsumosAdapter(Context context, List<StockConsumos> stockConsumosList) {
        this.context = context;
        this.stockConsumosList = stockConsumosList;
    }

    @NotNull
    @Override
    public StockConsumosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View stockconsumosLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_consumos_list,parent,false);
        return new StockConsumosAdapter.StockConsumosHolder(stockconsumosLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull StockConsumosHolder holder, int position) {
        StockConsumos stockconsumos = stockConsumosList.get(position);
        holder.id.setText(nf.format(stockconsumos.getId()));
        holder.stock_gasoleo.setText(nf.format(stockconsumos.getGasoleo()));
        holder.stock_aceite_motor.setText(nf.format(stockconsumos.getAceite_motor()));
        holder.stock_aceite_hidraulico.setText(nf.format(stockconsumos.getAceite_hidraulico()));
        holder.stock_aceite_transmisiones.setText(nf.format(stockconsumos.getAceite_transmisiones()));
        holder.stock_valvulina.setText(nf.format(stockconsumos.getValvulina()));
        holder.stock_grasas.setText(nf.format(stockconsumos.getGrasas()));
        holder.stock_fecha_consumo.setText(stockconsumos.getFecha_consumo());
        holder.stock_id_empleado.setText(nf.format(stockconsumos.getId_empleado()));

        // Obtener datos de las preferencias
        SharedPreferences preferencesss = context.getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        String idUsuario = preferencesss.getString("idUsuario","holo");

        // Boton Mostrar
        holder.btnMostrarStockConsumo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                View showLayout = LayoutInflater.from(context).inflate(R.layout.stock_consumos_show, null);
                TextView tvId = showLayout.findViewById(R.id.show_id_stock_consumo);
                TextView tvGasoleo = showLayout.findViewById(R.id.show_stock_gasoleo);
                TextView tvAceite_Motor = showLayout.findViewById(R.id.show_stock_aceite_motor);
                TextView tvAceite_Hidraulico = showLayout.findViewById(R.id.show_stock_aceite_hidraulico);
                TextView tvAceite_Transmisiones = showLayout.findViewById(R.id.show_stock_aceite_transmisiones);
                TextView tvValvulina = showLayout.findViewById(R.id.show_stock_valvulina);
                TextView tvGrasas = showLayout.findViewById(R.id.show_stock_grasas);
                TextView tvFecha_consumo = showLayout.findViewById(R.id.show_stock_fecha_consumo);
                TextView tvId_empleado = showLayout.findViewById(R.id.show_stock_consumo_id_empleado);
                //Log.i("ID CONSUMO ADAPTER",nf.format(stockconsumos.getId()));

                tvId.setText(nf.format(stockconsumos.getId()));
                tvGasoleo.setText(nf.format(stockconsumos.getGasoleo()));
                tvAceite_Motor.setText(nf.format(stockconsumos.getAceite_motor()));
                tvAceite_Hidraulico.setText(nf.format(stockconsumos.getAceite_hidraulico()));
                tvAceite_Transmisiones.setText(nf.format(stockconsumos.getAceite_transmisiones()));
                tvValvulina.setText(nf.format(stockconsumos.getValvulina()));
                tvGrasas.setText(nf.format(stockconsumos.getGrasas()));
                tvFecha_consumo.setText(stockconsumos.getFecha_consumo());
                tvId_empleado.setText(idUsuario);

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
                        builder.setTitle("¿Borrar Stock del Consumo?");
                        builder.setMessage("¿Seguro que quieres borrar el Stock con fecha: "+stockconsumos.getFecha_consumo()+" ?");
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
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.DELETE_STOCK_CONSUMO_URL, new Response.Listener<String>() {
                                    // Método que elimina una voladura en caso de que los datos obtenidos sean correctos
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject object = new JSONObject(response);
                                            // Obtenemos el estado que nos devuelve el json
                                            String check = object.getString("estado");

                                            // Si el estado es borrar, borramos la posicion del item
                                            if(check.equals("borrar")){
                                                BorrarConsumo(position);
                                                Toast.makeText(context, "¡Stock del Consumo borrado con éxito!", Toast.LENGTH_SHORT).show();
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
                                        params.put("id",stockconsumos.getId().toString());
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
        holder.btnEditarStockConsumo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                View editLayout = LayoutInflater.from(context).inflate(R.layout.stock_consumo_edit, null);
                TextView tvId = editLayout.findViewById(R.id.edt_id_stock_consumo);
                EditText etGasoleo = editLayout.findViewById(R.id.edt_stock_gasoleo);
                EditText etAceite_Motor = editLayout.findViewById(R.id.edt_stock_aceite_motor);
                EditText etAceite_Hidraulico = editLayout.findViewById(R.id.edt_stock_aceite_hidraulico);
                EditText etAceite_Transmisiones = editLayout.findViewById(R.id.edt_stock_aceite_transmisiones);
                EditText etValvulina = editLayout.findViewById(R.id.edt_stock_valvulina);
                EditText etGrasas = editLayout.findViewById(R.id.edt_stock_grasas);
                EditText etFecha_consumo = editLayout.findViewById(R.id.edt_stock_fecha_consumo);
                TextView tvId_empleado = editLayout.findViewById(R.id.edt_stock_consumo_id_empleado);
                // No se puede acceder a la fecha
                etFecha_consumo.setEnabled(false);

                tvId.setText(nf.format(stockconsumos.getId()));
                etGasoleo.setText(nf.format(stockconsumos.getGasoleo()));
                etAceite_Motor.setText(nf.format(stockconsumos.getAceite_motor()));
                etAceite_Hidraulico.setText(nf.format(stockconsumos.getAceite_hidraulico()));
                etAceite_Transmisiones.setText(nf.format(stockconsumos.getAceite_transmisiones()));
                etValvulina.setText(nf.format(stockconsumos.getValvulina()));
                etGrasas.setText(nf.format(stockconsumos.getGrasas()));
                etFecha_consumo.setText(stockconsumos.getFecha_consumo());
                tvId_empleado.setText(idUsuario);

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setBackground(context.getResources().getDrawable(R.drawable.alert_dialog,null));
                builder.setView(editLayout);
                builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String id = tvId.getText().toString();
                        final String gasoleo = etGasoleo.getText().toString();
                        final String aceite_motor = etAceite_Motor.getText().toString();
                        final String aceite_hidraulico = etAceite_Hidraulico.getText().toString();
                        final String aceite_transmisiones = etAceite_Transmisiones.getText().toString();
                        final String valvulina = etValvulina.getText().toString();
                        final String grasas = etGrasas.getText().toString();
                        final String fecha_consumo = etFecha_consumo.getText().toString();
                        final String id_empleado = tvId_empleado.getText().toString();

                        // Comprobamos que los campos no están vacíos
                        if(gasoleo.isEmpty() || aceite_motor.isEmpty() || aceite_hidraulico.isEmpty() ||
                                aceite_transmisiones.isEmpty() || valvulina.isEmpty() || grasas.isEmpty() ||
                                fecha_consumo.isEmpty()){
                            Toast.makeText(context, "Algunos campos están vacíos", Toast.LENGTH_SHORT).show();
                        } else {
                            // Actualizamos
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.EDIT_STOCK_CONSUMO_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                    //Recargamos
                                    Navigation.findNavController(view).navigate(R.id.action_stockConsumosList_self);
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
                                    params.put("fecha_consumo",fecha_consumo);
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
        return stockConsumosList.size();
    }

    public class StockConsumosHolder extends RecyclerView.ViewHolder{

        TextView id,stock_gasoleo,stock_aceite_motor,stock_aceite_hidraulico,stock_aceite_transmisiones,stock_valvulina,stock_grasas,stock_fecha_consumo,stock_id_empleado;
        Button btnEditarStockConsumo,btnMostrarStockConsumo;

        public StockConsumosHolder(@NonNull View itemView) {
            super(itemView);
            // id de los view del xml
            id = itemView.findViewById(R.id.id_stock_consumo);
            stock_gasoleo = itemView.findViewById(R.id.stock_gasoleo);
            stock_aceite_motor = itemView.findViewById(R.id.stock_aceite_motor);
            stock_aceite_hidraulico = itemView.findViewById(R.id.stock_aceite_hidraulico);
            stock_aceite_transmisiones = itemView.findViewById(R.id.stock_aceite_transmisiones);
            stock_valvulina = itemView.findViewById(R.id.stock_valvulina);
            stock_grasas = itemView.findViewById(R.id.stock_grasas);
            stock_fecha_consumo = itemView.findViewById(R.id.stock_fecha_consumo);
            stock_id_empleado = itemView.findViewById(R.id.stock_consumo_id_empleado);
            btnMostrarStockConsumo = itemView.findViewById(R.id.btnMostrarStockConsumo);
            btnEditarStockConsumo = itemView.findViewById(R.id.btnEditarStockConsumo);
        }
    }

    // Borramos el item del layout
    public void BorrarConsumo (int item){
        stockConsumosList.remove(item);
        notifyItemRemoved(item);
    }

    // Dialogo de fecha y hora
    private void showDateTimeDialog(EditText fecha) {
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
                        fecha.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(context,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();

            }
        };

        new DatePickerDialog(context,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
