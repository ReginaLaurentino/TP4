package com.example.tp4.connection;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.tp4.AdaptadorArticulos;
import com.example.tp4.entity.EArticulo;
import com.example.tp4.entity.ECategoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataListarFragment extends AsyncTask<String, Void, String> {


    private GridView gridView;
    private Context context;
    private String Pantalla;
    private String Idbuscar;
    private EArticulo articulobuscado;

    private static String result2;
    private static ArrayList<EArticulo> listaArticulos = new ArrayList<>();

    //Recibe por constructor el textview
    //Constructor
    public DataListarFragment(@Nullable GridView gv, @Nullable Context ct,
                              String pantalla, @Nullable EArticulo art, @Nullable String idbuscar )
    {
        gridView = gv;
        context = ct;
        Pantalla = pantalla;
        Idbuscar = idbuscar;

    }

    @Override
    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();

            switch (Pantalla){
                case "listar": Listar(st);
                case "buscarid": BuscarId (st,Idbuscar);
            }


            response = "Conexion exitosa";
        }
        catch(Exception e) {
            e.printStackTrace();
            result2 = "Conexion no exitosa";
        }
        return response;

    }

    @Override
    protected void onPostExecute(String response) {
        AdaptadorArticulos adapter = new AdaptadorArticulos(context, listaArticulos);
        gridView.setAdapter(adapter);
    }


    private void Listar(Statement st){

        try{
            ResultSet rs = st.executeQuery("SELECT a.id,a.stock,a.nombre,a.idCategoria,c.descripcion FROM articulo a inner join categoria c on c.id=a.idCategoria");
            result2 = " ";

            EArticulo articulo;
            ECategoria categoria;
            while(rs.next()) {
                articulo = new EArticulo();
                articulo.setId(rs.getInt("id"));
                articulo.setStock(rs.getInt("stock"));
                articulo.setNombre(rs.getString("nombre"));
                categoria=new ECategoria();
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setDescripcion(rs.getString("descripcion"));
                articulo.setCategoria(categoria);
                listaArticulos.add(articulo);
            }} catch (Exception ex ){
                   ex.printStackTrace();
                   result2 = "Conexion no exitosa";
            }


    }

    private void BuscarId (Statement st,String id){
        try{
            ResultSet rs = st.executeQuery("SELECT a.id,a.stock,a.nombre,a.idCategoria,c.descripcion FROM articulo a inner join categoria c on c.id=a.idCategoria where a.id = "+id);
            result2 = " ";

            ECategoria categoria;
            while(rs.next()) {
                articulobuscado = new EArticulo();
                articulobuscado.setId(rs.getInt("id"));
                articulobuscado.setStock(rs.getInt("stock"));
                articulobuscado.setNombre(rs.getString("nombre"));
                categoria=new ECategoria();
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setDescripcion(rs.getString("descripcion"));
                articulobuscado.setCategoria(categoria);
                }}
        catch (Exception ex ){
            ex.printStackTrace();
            result2 = "Conexion no exitosa";
        }
    }

    public EArticulo getArticulobuscado() {
        return articulobuscado;
    }
}
