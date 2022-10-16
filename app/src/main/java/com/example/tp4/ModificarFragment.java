package com.example.tp4;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tp4.entity.EArticulo;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarFragment extends Fragment {

    public static final String TITLE = "Modificar";

    public Text idBuscar;
    public EArticulo art;

    public static ModificarFragment newInstance() {

        return new ModificarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_modificar, container, false);
    }

    public void buscarporid (){
        idBuscar = getView().findViewById(R.id.et_Id);

        return ;
    }

}