package com.example.tp4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.tp4.connection.DataListarFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarFragment extends Fragment {

    public static final String TITLE = "Listar";
    private GridView gridView;
    private AdaptadorArticulos adaptador;
    Context contexto ;


    public static ListarFragment newInstance() {

        return new ListarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar, container, false);
        contexto = this.getContext();
        gridView = view.findViewById(R.id.gv_listar);
        DataListarFragment datatask = new DataListarFragment(gridView,contexto, "listar", null,null, null);
        datatask.execute();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.gv_listar);
        final SwipeRefreshLayout pullToRefresh = getView().findViewById(R.id.refreshLayoutListar);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataListarFragment datatask = new DataListarFragment(gridView,contexto, "listar", null,null, null);
                datatask.execute();
                pullToRefresh.setRefreshing(false);
            }
        });

        DataListarFragment datatask = new DataListarFragment(gridView,contexto, "listar", null,null, null);
        datatask.execute();
    }

}