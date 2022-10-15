package com.example.tp4.connection;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.tp4.entity.EArticulo;
import com.example.tp4.entity.ECategoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class DataMainActivity extends AsyncTask<String, Void, String> {


    private ListView lvArticulos;
    private Context context;

    private static String result2;
    private static ArrayList<EArticulo> listaArticulos = new ArrayList<>();

    //Recibe por constructor el textview
    //Constructor
    public DataMainActivity(ListView lv, Context ct)
    {
        lvArticulos = lv;
        context = ct;

    }

    @Override
    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
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
     //   ClienteAdapter adapter = new ClienteAdapter(context, listaArticulos);
       // lvArticulos.setAdapter(adapter);
    }
}