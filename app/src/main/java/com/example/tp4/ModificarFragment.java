package com.example.tp4;

import static java.lang.Integer.parseInt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tp4.connection.DataListarFragment;
import com.example.tp4.entity.EArticulo;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarFragment extends Fragment {

    public static final String TITLE = "Modificar";

    public TextView idBuscar;
    public EArticulo art;
    private Button modificar;
    private String id;

    public static ModificarFragment newInstance() {

        return new ModificarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_modificar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        modificar = getView().findViewById(R.id.btn_Buscar);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idBuscar = (TextView)getView().findViewById(R.id.et_Id);
                id= idBuscar.getText().toString();
                buscarporid(id);
            }
        });

    }

    public void buscarporid (String id){
        DataListarFragment datatask = new DataListarFragment(null,null, "buscarid", null,id);
        datatask.execute();
        art= datatask.getArticulobuscado();
        return ;
    }



}