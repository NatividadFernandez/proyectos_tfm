package com.pms.cantera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.pms.cantera.configuracion.ConfiguracionFragment;
import com.pms.cantera.databinding.ActivityDrawerBinding;

import org.jetbrains.annotations.NotNull;

public class DrawerActivity extends AppCompatActivity {

    // Declaración de variables
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDrawerBinding binding;
    private TextView tvNombre, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtenemos los datos que se nos envian desde la anterioa activity
        String idUsuario = getIntent().getStringExtra("idUsuario");
        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        String emailUsuario = getIntent().getStringExtra("emailUsuario");

        // Este código es eL encargado de la navegación entre fragment y el menú lateral
        binding = ActivityDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarDrawer.toolbar);
        binding.appBarDrawer.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Cuando Cerrar Sesión del menú sea pulsado, llamará al método logout
        navigationView.getMenu().findItem(R.id.logout).setOnMenuItemClickListener(menuItem -> {logout(); return true;});

        mAppBarConfiguration = new AppBarConfiguration.Builder( R.id.action_config,
                R.id.principalFragment,R.id.registro_Entradas_Salidas,R.id.voladurasListFragment,R.id.menuProductosFragment,R.id.menuConsumosFragment,R.id.configuracionFragment)
                .setOpenableLayout(drawer)
                //.setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_drawer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Quitamos que los iconos aparezcan en gris
        navigationView.setItemIconTintList(null);

        // Obtengo el id de la cabecera
        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);

        // Asigno a las variables el id de los textview
        tvNombre = (TextView) header.findViewById(R.id.tvNombre);
        tvEmail = (TextView) header.findViewById(R.id.tvEmail);

        // Cambiamos el texto de estas text view por los valores obtenidos
        // en la activity anterior
        tvNombre.setText(nombreUsuario);
        tvEmail.setText(emailUsuario);

    }

    // Creación de las opciones de menu (esquina superior derecha)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    // Opciones de menu superior derecho
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_config:
                Toast.makeText(DrawerActivity.this, "Próximamente", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Navegavilidad entre los fragment del menú lateral izquierdo
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Método que cierra sesión
    public void logout(){
        // Limpiamos las pregerencias que anteriormente creamos
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        preferences.edit().clear().commit();

        // Redirigimos a la activity de loguin
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }


}