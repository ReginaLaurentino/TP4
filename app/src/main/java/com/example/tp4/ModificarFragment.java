package com.example.tp4;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tp4.connection.DataListarFragment;
import com.example.tp4.entity.EArticulo;
import com.example.tp4.entity.ECategoria;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarFragment extends Fragment {

    public static final String TITLE = "Modificar";

    public TextView idBuscar;
    public EArticulo artamodificar;
    private Button BTNbuscarid;
    private Button BTNmodificar;
    private String id;
    Context context;

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
        context = getContext();
        BTNbuscarid = getView().findViewById(R.id.btn_Buscar);
        BTNbuscarid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idBuscar = (TextView)getView().findViewById(R.id.et_Id);
                id= idBuscar.getText().toString();
                buscarporid(id, getView());
            }
        });
        BTNmodificar = getView().findViewById(R.id.btn_Agregar);
        BTNmodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView nombrenuevo = (TextView)getView().findViewById(R.id.et_NombreProducto);
                TextView stocknuevo = (TextView)getView().findViewById(R.id.et_Stock);
                Spinner spcatnuevo = getView().findViewById(R.id.sp_Categoria);
                ECategoria categoria = (ECategoria)spcatnuevo.getSelectedItem();
                artamodificar= new EArticulo();
                artamodificar.setNombre(nombrenuevo.getText().toString());
                artamodificar.setStock(parseInt(stocknuevo.getText().toString()) );
                artamodificar.setCategoria(categoria);
                Modificar (artamodificar, id);
            }
        });

    }

    public void buscarporid (String id, View v){
        DataListarFragment datatask = new DataListarFragment(null,context, "buscarid", null,id, v);
        datatask.execute();
        return ;
    }

    public void Modificar (EArticulo art, String id){
        DataListarFragment datatask = new DataListarFragment(null,context, "modificar", artamodificar,id, null);
        datatask.execute();
        return ;
    }


}