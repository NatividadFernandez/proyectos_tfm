package com.pms.cantera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class  MainActivity extends AppCompatActivity {

    // Declaracion de variables
    private Button btnLogin;
    private EditText edtEmail, edtPassword;
    private String email, password;
    private String idUser,nombreUser,emailUser;

    // Método que ejecuta la lógica de arranque básica de la aplicación que debe ocurrir una sola vez en toda la vida de la activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // A los id de la view les asignamos su variable
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        // Recuperamos el email y la contraseña del ultimo usuario que se logueo
        recuperarPreferencias();

        // Cuando se pulse el botón de Iniciar Sesión se hara´n una serie de operaciones
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtenemos los valores introducidos
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();

                //Comprobamos que los campos no estén vacíos
                if(!email.isEmpty() && !password.isEmpty()){
                    // Llamamos al método validar usuario y pasamos por parámetro la url
                    // donde deberá de hacer la comrobación del usuario
                    validarUsuario(Urls.LOGIN_USER_URL);
                } else {
                    // Si están vaciós se lo notificamos al usuario
                    Toast.makeText(MainActivity.this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    // Método que comprueba si el usuario existe
    private void validarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Si el resultando de la consulta no está vacío, significa que el usurio existe
                if(!response.isEmpty()){
                    try {
                        // Creo un objeto jsonobject para obtener
                        // los valores de la consulta devuelta
                        JSONObject object = new JSONObject(response);

                        // Le asigno a las variables los valores que quiero obtener
                        idUser = object.getString("id");
                        nombreUser = object.getString("name");
                        emailUser = object.getString("email");

                        /*Log.i("ID USUARIO",idUser);*/

                        // Guardamos los datos del usuario logueado
                        guardarPreferencias();

                        // Redirigimos a otra activity
                        Intent intent = new Intent(MainActivity.this,DrawerActivity.class);
                        // Enviamos los datos obtenidos a esta activity para que haga uso de ellos
                        intent.putExtra("idUsuario", idUser);
                        intent.putExtra("nombreUsuario", nombreUser);
                        intent.putExtra("emailUsuario", emailUser);
                        startActivity(intent);
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    // Mensaje de error
                    Toast.makeText(MainActivity.this, "El email o la contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Detectar posibles errores cambiar por mensaje escrito manual
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Creamos una instacia el objeto Map
                Map<String,String> parametros = new HashMap<String,String>();
                // Con put ingresamos los datos a enviar
                parametros.put("email", email);
                parametros.put("password", password);
                return parametros;
            }
        };

        // Creamos instancia de la clase RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Agregamos la instancia de nuestro objeto StringRequest, ayuda a procesar las peticiones
        requestQueue.add(stringRequest);

    }

    // Método que guarda los datos del usuario
    private void guardarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email",email);
        editor.putString("password",password);
        editor.putString("idUsuario",idUser);
        editor.putString("nombreUser",nombreUser);
        editor.commit();
    }

    //Método que permite recuperar los datos del usuario
    private void recuperarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        edtEmail.setText(preferences.getString("email","nati@gmail.com"));
        edtPassword.setText(preferences.getString("password","12345678"));
    }


    // Método que oculta el teclado
    public void ocultarTeclado(View view){
        UIUtil.hideKeyboard(this);
    }

}