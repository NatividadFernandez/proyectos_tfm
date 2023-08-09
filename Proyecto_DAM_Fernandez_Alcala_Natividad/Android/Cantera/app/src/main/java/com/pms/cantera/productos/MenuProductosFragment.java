package com.pms.cantera.productos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pms.cantera.R;

public class MenuProductosFragment extends Fragment {

    // Declaracion de variables
    private CardView cvProductos, cvStockProductos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_productos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cvProductos = (CardView) view.findViewById(R.id.cvProductos);
        cvStockProductos = (CardView) view.findViewById(R.id.cvStockProductos);

        // Objeto navigation para navegar por los fragment
        final NavController navController = Navigation.findNavController(view);

        // Botón Productos
        cvProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_menuProductosFragment_to_productosListFragment);
            }
        });

        // Botón Stock Productos
        cvStockProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_menuProductosFragment_to_stockProductosListFragment);
            }
        });
    }
}