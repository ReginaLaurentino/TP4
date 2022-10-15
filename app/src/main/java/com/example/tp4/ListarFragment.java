package com.example.tp4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarFragment extends Fragment {

    public static final String TITLE = "Listar";
    private GridView gridView;
    private AdaptadorArticulos adaptador;

    public static ListarFragment newInstance() {

        return new ListarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar, container, false);
        Context contexto = this.getContext();
        gridView = view.findViewById(R.id.gv_listar);
        adaptador = new AdaptadorArticulos(contexto);
        gridView.setAdapter(adaptador);

        return view;

    }
}