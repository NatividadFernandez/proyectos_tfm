package com.pms.cantera.configuracion;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.pms.cantera.R;
import com.pms.cantera.Urls;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ConfiguracionFragment extends Fragment {

    // Declaracion de variables
    private String idUsuario;
    private String nombreUsuario;
    private String emailUsuario;
    private TextView tvNombre,tvEmail;
    private Button btnPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuracion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences preferencesss = getContext().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        // Recoger datos de las preferencias
        idUsuario = preferencesss.getString("idUsuario","1");
        nombreUsuario = preferencesss.getString("nombreUser","nombre");
        emailUsuario = preferencesss.getString("email","email");

        tvNombre = (TextView) view.findViewById(R.id.nombre_usuario);
        tvEmail = (TextView) view.findViewById(R.id.email_usuario);
        btnPass = (Button) view.findViewById(R.id.change_password);

        // Cambiar texto de los textview por los datos obtenidos de dichas preferencias
        tvNombre.setText(nombreUsuario);
        tvEmail.setText(emailUsuario);

        // Boton cambiar contraseña
        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword(view);
            }
        });
    }

    // Método que cambia la contraseña del usuario
    private void changePassword(View view){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.LOAD_CONFIGURATION_DATA_URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(String response) {
                View addLayout = LayoutInflater.from(getContext()).inflate(R.layout.change_pass, null);
                // Obtener id de la vista
                EditText etActuPass = addLayout.findViewById(R.id.actual_pass);
                EditText etNewPass = addLayout.findViewById(R.id.new_pass);
                EditText etVeryPass = addLayout.findViewById(R.id.verificar_pass);

                // Dialogo
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                builder.setBackground(getContext().getResources().getDrawable(R.drawable.alert_dialog,null));
                builder.setTitle("¿Quieres cambiar tu contraseña?");
                builder.setView(addLayout);
                builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String actual_pass = etActuPass.getText().toString();
                        final String new_pass = etNewPass.getText().toString();
                        final String verificar_pass = etVeryPass.getText().toString();
                        final String nombre_user = nombreUsuario;
                        final String email_user = emailUsuario;
                        final String id_empleado = idUsuario;

                        // Comprobar si los campos están vacíos
                        if(actual_pass.isEmpty() || new_pass.isEmpty() || verificar_pass.isEmpty()){
                            Toast.makeText(getContext(), "Los campos no pueden estár vacíos", Toast.LENGTH_SHORT).show();
                        } else if(!new_pass.equals(verificar_pass)){
                            Toast.makeText(getContext(), "La contraseña verificada no es igual a la nueva.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Consulta a la base de datos
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.LOAD_CONFIGURATION_DATA_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                    //Recargamos
                                    Navigation.findNavController(view).navigate(R.id.action_configuracionFragment_self);
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
                                    // Enviamos datos
                                    params.put("nombre",nombre_user);
                                    params.put("email",email_user);
                                    params.put("password",actual_pass);
                                    params.put("new_password",new_pass);
                                    params.put("verifity_password",verificar_pass);
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

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Instancia objeto
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        // Agregamos la instancia de nuestro objeto StringRequest, ayuda a procesar las peticiones
        requestQueue.add(stringRequest);
    }
}