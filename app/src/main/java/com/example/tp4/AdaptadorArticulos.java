package com.example.tp4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.tp4.entity.EArticulo;

import java.util.List;

public class AdaptadorArticulos extends BaseAdapter {
    private Context context;
    private List<EArticulo> articulos ;


    public AdaptadorArticulos(Context context, List<EArticulo> arts) {

        this.context = context;
        articulos = arts;
    }

    @Override
    public int getCount() {

        return articulos != null ? articulos.size() : 0;
    }

    @Override
    public EArticulo getItem(int position) {
        return articulos != null ?  articulos.get(position): null;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getIds();

    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view== null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =inflater.inflate(R.layout.grid_item, viewGroup, false);

        }

        TextView Id = ((TextView) view.findViewById(R.id.tvIdListar));
        TextView Nombre = ((TextView) view.findViewById(R.id.tv_nombre));

        if(articulos != null){
            EArticulo item = (EArticulo) getItem(position);

            Id.setText( "ID: " + String.valueOf(item.getId()) );
            Nombre.setText("Nombre : " + item.getNombre());
        }



        return view;


    }
}
