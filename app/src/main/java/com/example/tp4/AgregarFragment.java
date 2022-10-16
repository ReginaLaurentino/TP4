package com.example.tp4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.tp4.connection.DataListarFragment;
import com.example.tp4.entity.EArticulo;
import com.example.tp4.entity.ECategoria;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgregarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarFragment extends Fragment {

    public static final String TITLE = "Agregar";
    private Spinner spinnerCategorias;
    private Button btnAgregar;

    public static AgregarFragment newInstance() {

        return new AgregarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_agregar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargarCategorias();
        btnAgregar = getView().findViewById(R.id.btnAgregarProducto);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAgregarProducto();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void cargarCategorias(){
        ArrayList<ECategoria> categorias = new ArrayList<ECategoria>();
        categorias.add(new ECategoria(1,"Limpieza"));
        categorias.add(new ECategoria(2,"Lacteos"));
        categorias.add(new ECategoria(3,"Carnes"));
        categorias.add(new ECategoria(4,"Bebidas"));
        ArrayAdapter <ECategoria> adapter = new ArrayAdapter<ECategoria>(this.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,categorias);

        spinnerCategorias = getView().findViewById(R.id.spCategoriaAgregar);
        spinnerCategorias.setAdapter(adapter);
    }

    public void btnAgregarProducto(){
        try {
            if (validarCampos()){

                int id = Integer.valueOf(((EditText)getView().findViewById(R.id.etIdProdAgregar)).getText().toString());
                String NombreProducto = String.valueOf(((EditText)getView().findViewById(R.id.etNombreProductoAgregar)).getText());
                int Stock = Integer.valueOf(((EditText)getView().findViewById(R.id.etStockAgregar)).getText().toString());
                ECategoria categoria = new ECategoria(1,"A");
                EArticulo articulo = new EArticulo(id,NombreProducto,Stock,categoria);
                DataListarFragment datatask = new DataListarFragment(null,this.getContext(), "agregar", articulo, null);
                datatask.execute();
            }

        }catch (Exception ex){

        }
    }

    public boolean validarCampos(){
        boolean bnd = true;
        RelativeLayout llyParent = getView().findViewById(R.id.relativeLayoutAgregar);
        int count = llyParent.getChildCount();
        for (int i=0;i<count;i++){
            if (llyParent.getChildAt(i) instanceof TextInputEditText){
                TextInputEditText layout = (TextInputEditText) llyParent.getChildAt(i);
                if (layout.length() == 0){
                    layout.setError("Complete este campo!");
                    bnd = false;
                }
            }
        }

        return bnd;
    }

}