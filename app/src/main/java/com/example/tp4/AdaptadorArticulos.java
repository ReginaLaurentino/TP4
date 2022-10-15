package com.example.tp4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdaptadorArticulos extends BaseAdapter {
    private Context context;
    private List<EArticulo> articulos ;

    private EArticulo[] items = {
            new EArticulo(1, "123"),
            new EArticulo(2, "123"),
            new EArticulo(3, "123"),
            new EArticulo(4, "123"),
            new EArticulo(5, "123"),
            new EArticulo(6, "123"),
            new EArticulo(7, "123"),
    };


    public AdaptadorArticulos(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public EArticulo getItem(int position) {
        return items[position];
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

        TextView Id = ((TextView) view.findViewById(R.id.tv_Id));
        TextView Nombre = ((TextView) view.findViewById(R.id.tv_nombre));

        if(articulos != null){
            final EArticulo item = (EArticulo) getItem(position);

            Id.setText(item.getId());
            Nombre.setText(item.getNombre());
        }



        return view;


    }
}
