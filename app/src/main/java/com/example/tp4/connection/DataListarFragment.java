package com.example.tp4.connection;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tp4.AdaptadorArticulos;
import com.example.tp4.entity.EArticulo;
import com.example.tp4.entity.ECategoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataListarFragment extends AsyncTask<String, Void, String> {


    private GridView gridView;
    private Context context;
    private String Pantalla;
    private String Idbuscar;
    private EArticulo articulo;
    private String mensajeAgregar;

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
        articulo = art;
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
                break;
                case "agregar": AgregarArticulo(st);
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
        switch (Pantalla){
            case "listar": AdaptadorArticulos adapter = new AdaptadorArticulos(context, listaArticulos);
                gridView.setAdapter(adapter);
                break;
            case "agregar": Toast.makeText(context,mensajeAgregar,Toast.LENGTH_LONG).show();
                break;
        }

    }

    public void AgregarArticulo(Statement st){
        try{

            String query= String.format("insert into articulo (id,nombre,stock,idCategoria) values (%1$s,'%2$s',%3$s,%4$s) ", articulo.getId(),articulo.getNombre(),articulo.getStock(),articulo.getCategoria().getId());
            int rs = st.executeUpdate(query);
            if (rs > 0)
                mensajeAgregar = "Artículo agregado con éxito!";
            else
                mensajeAgregar = "Error al agregar el artículo!";
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
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
}
