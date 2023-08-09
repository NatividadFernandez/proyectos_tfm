package com.pms.cantera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PrincipalFragment extends Fragment {

    // Declaracion de variables
    private CardView cvRegistro, cvVoladuras, cvProductos, cvConsumos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_principal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // A los id de la view les asignamos su variable
        cvRegistro = (CardView) view.findViewById(R.id.cvCheckIn);
        cvVoladuras = (CardView) view.findViewById(R.id.cvVoladuras);
        cvProductos = (CardView) view.findViewById(R.id.cvProductos);
        cvConsumos = (CardView) view.findViewById(R.id.cvConsumos);

        // Creamos un objeto navController para navegar entre view
        final NavController navController = Navigation.findNavController(view);

        // Si pulsamos los cardview nos redirigirán a su view correspondiente

        cvRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_principalFragment_to_registro_Entradas_Salidas);
            }
        });

        cvVoladuras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_principalFragment_to_voladurasListFragment);
            }
        });

        cvProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_principalFragment_to_menuProductosFragment);
            }
        });

        cvConsumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_principalFragment_to_menuConsumosFragment);
            }
        });


    }
}