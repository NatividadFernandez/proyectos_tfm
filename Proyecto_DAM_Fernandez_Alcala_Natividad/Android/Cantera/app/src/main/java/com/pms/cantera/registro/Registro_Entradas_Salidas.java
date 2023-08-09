package com.pms.cantera.registro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.pms.cantera.R;
import com.pms.cantera.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Registro_Entradas_Salidas extends Fragment {

    // Declaración de variables
    private CardView cvCheckIn, cvCheckOut;
    private TextView tvCheckInL,tvCheckOutL,tvCheckInS,tvCheckOutS;
    private RelativeLayout rlCheckIn, rlCheckOut;
    private String idUsuario, msgRegistro;
    private TextView tvRegistro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro__entradas__salidas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences preferencesss = getContext().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        // Recogemos los datos de las preferencias
        idUsuario = preferencesss.getString("idUsuario","holo");
        msgRegistro = preferencesss.getString("nombreUser","nombreUsuario");

        // Id del view
        tvRegistro = (TextView) view.findViewById(R.id.tvRegistro);
        cvCheckIn = (CardView) view.findViewById(R.id.cvCheckIn);
        cvCheckOut = (CardView) view.findViewById(R.id.cvCheckOut);
        rlCheckIn = (RelativeLayout) view.findViewById(R.id.checkInRelative);
        rlCheckOut = (RelativeLayout) view.findViewById(R.id.checkOutRelative);
        tvCheckInL = (TextView) view.findViewById(R.id.t3);
        tvCheckOutL = (TextView) view.findViewById(R.id.t4);
        tvCheckInS = (TextView) view.findViewById(R.id.tvCheckIn);
        tvCheckOutS = (TextView) view.findViewById(R.id.tvCheckOut);

        cvCheckOut.setEnabled(false);
        rlCheckOut.setBackgroundColor(Color.GRAY);

        // Cambiar tonos de color
        tvCheckOutL.setTextColor(getResources().getColor(R.color.mensaje));
        tvCheckOutS.setTextColor(getResources().getColor(R.color.mensaje));

        // Mensaje de bienvenida
        tvRegistro.setText("¡Hola "+msgRegistro+"!");

        // Consulta a la base de datos
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.REGISTRO_DATA_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);

                    String check = object.getString("estado");

                    // Cambiamos la tonalidad de los botones si el usuario ha realizado el check in o check out
                    switch (check){
                        case "inicio":
                            cvCheckOut.setEnabled(false);
                            rlCheckOut.setBackgroundColor(Color.GRAY);
                            tvCheckOutL.setTextColor(getResources().getColor(R.color.mensaje));
                            tvCheckOutS.setTextColor(getResources().getColor(R.color.mensaje));
                            break;
                        case "checkout":
                            cvCheckOut.setEnabled(false);
                            rlCheckOut.setBackgroundColor(Color.GRAY);
                            tvCheckOutL.setTextColor(getResources().getColor(R.color.mensaje));
                            tvCheckOutS.setTextColor(getResources().getColor(R.color.mensaje));
                            cvCheckIn.setEnabled(false);
                            rlCheckIn.setBackgroundColor(Color.GRAY);
                            tvCheckInL.setTextColor(getResources().getColor(R.color.mensaje));
                            tvCheckInS.setTextColor(getResources().getColor(R.color.mensaje));
                            Snackbar.make(view, "!Tú registro está completo por hoy¡", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;
                        case "checkin":
                            cvCheckOut.setEnabled(true);
                            rlCheckOut.setBackgroundColor(Color.WHITE);
                            tvCheckOutL.setTextColor(Color.BLACK);
                            tvCheckOutS.setTextColor(Color.BLACK);
                            cvCheckIn.setEnabled(false);
                            rlCheckIn.setBackgroundColor(Color.GRAY);
                            tvCheckInL.setTextColor(getResources().getColor(R.color.mensaje));
                            tvCheckInS.setTextColor(getResources().getColor(R.color.mensaje));
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                params.put("id_empleado",idUsuario);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        // Se ha reliazado el Check In, por lo que añadimos el registro a la base de datos
        cvCheckIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                // Obtenemos la fecha y hora del dia
                Date fechaEntrada = new Date();
                DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                // Dialogo
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                builder.setBackground(getContext().getResources().getDrawable(R.drawable.alert_dialog,null));
                builder.setTitle("¿Realizar Check In?");
                builder.setMessage("¿Registro de Entrada en esta fecha y hora "+hourdateFormat.format(fechaEntrada)+"?");
                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.ADD_REGISTRO_DATA_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);

                                    String check = object.getString("estado");

                                    if(check.equals("checkin")){
                                        Snackbar.make(view, "!Registro de entrada realizado¡", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        cvCheckOut.setEnabled(true);
                                        rlCheckOut.setBackgroundColor(Color.WHITE);
                                        tvCheckOutL.setTextColor(Color.BLACK);
                                        tvCheckOutS.setTextColor(Color.BLACK);
                                        cvCheckIn.setEnabled(false);
                                        rlCheckIn.setBackgroundColor(Color.GRAY);
                                        tvCheckInL.setTextColor(getResources().getColor(R.color.mensaje));
                                        tvCheckInS.setTextColor(getResources().getColor(R.color.mensaje));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
                                params.put("fecha_check_in",hourdateFormat.format(fechaEntrada));
                                params.put("id_empleado",idUsuario);

                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        requestQueue.add(stringRequest);

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

        cvCheckOut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                Date fechaSalida = new Date();
                DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                builder.setBackground(getContext().getResources().getDrawable(R.drawable.alert_dialog,null));
                builder.setTitle("¿Realizar Check Out?");
                builder.setMessage("¿Registro de Salida en esta fecha y hora "+hourdateFormat.format(fechaSalida)+"?");
                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.UPDATE_REGISTRO_DATA_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            String check = object.getString("estado");

                            if(check.equals("checkout")){
                                Snackbar.make(view, "!Registro de salida realizado¡", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                cvCheckOut.setEnabled(false);
                                rlCheckOut.setBackgroundColor(Color.GRAY);
                                tvCheckOutL.setTextColor(getResources().getColor(R.color.mensaje));
                                tvCheckOutS.setTextColor(getResources().getColor(R.color.mensaje));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                        params.put("fecha_check_out",hourdateFormat.format(fechaSalida));
                        params.put("id_empleado",idUsuario);

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);

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
}