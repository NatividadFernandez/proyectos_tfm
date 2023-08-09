package com.pms.cantera.consumos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pms.cantera.R;

import org.jetbrains.annotations.NotNull;

public class MenuConsumosFragment extends Fragment {

    // Declaracion de variables
    private CardView cvConsumos, cvStockConsumos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_consumos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cvConsumos = (CardView) view.findViewById(R.id.cvConsumos);
        cvStockConsumos = (CardView) view.findViewById(R.id.cvStockConsumos);

        // Objeto navigation para navegar por los fragment
        final NavController navController = Navigation.findNavController(view);

        // Boton Consumos
        cvConsumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_menuConsumosFragment_to_consumosListFragment);
            }
        });

        // Boton Stock Consumos
        cvStockConsumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_menuConsumosFragment_to_stockConsumosList);
            }
        });
    }
}