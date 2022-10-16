package com.example.tp4.connection;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tp4.AdaptadorArticulos;
import com.example.tp4.R;
import com.example.tp4.entity.EArticulo;
import com.example.tp4.entity.ECategoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataListarFragment extends AsyncTask<String, Void, String> {


    private GridView gridView;
    private Context context;
    private String Pantalla;
    private String Idbuscar;
    private EArticulo articulobuscado;
    private EArticulo articulo;
    private String mensajeAgregar;
    private String mensajeModificar;
    private View View;
    private static String result2;
    private static ArrayList<EArticulo> listaArticulos ;
    private static ArrayList<ECategoria> listaCategorias;
    ArrayAdapter<ECategoria> adaptador;
    AdaptadorArticulos adapter;

    //Recibe por constructor el textview
    //Constructor
    public DataListarFragment(@Nullable GridView gv, @Nullable Context ct,
                              String pantalla, @Nullable EArticulo art, @Nullable String idbuscar , @Nullable View view)
    {
        gridView = gv;
        context = ct;
        Pantalla = pantalla;
        Idbuscar = idbuscar;
        articulo = art;
        View = view;
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
                case "buscarid": BuscarId (st,Idbuscar);
                break;
                case "agregar": AgregarArticulo(st);
                break;
                case "llenarspinner": LlenaSpinner(st);
                    break;
                case "modificar": ModificarArticulo(st, articulo, Idbuscar);
                break;
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
            case "listar":
                gridView.setAdapter(adapter);
                break;
            case "agregar": Toast.makeText(context,mensajeAgregar,Toast.LENGTH_LONG).show();
                break;
            case "buscarid" :
                TextView nombre = (TextView)View.findViewById(R.id.et_NombreProducto);
                TextView stock = (TextView)View.findViewById(R.id.et_Stock);
                Spinner categoria = (Spinner)View.findViewById(R.id.sp_Categoria);

                adaptador= new ArrayAdapter<ECategoria>(context, android.R.layout.simple_spinner_item, listaCategorias);
                categoria.setAdapter(adaptador);

                int aux = adaptador.getPosition(articulobuscado.getCategoria());
                categoria.setSelection(aux);
                nombre.setText(articulobuscado.getNombre());
                stock.setText( String.valueOf( articulobuscado.getStock()) );

                break;
            case "llenarspinner":
                adaptador = new ArrayAdapter<ECategoria>(context, android.R.layout.simple_spinner_item, listaCategorias);
                Spinner spinneragregar = View.findViewById(R.id.spCategoriaAgregar);
                spinneragregar.setAdapter(adaptador);
                break;
            case "modificar":
                adapter.notifyDataSetChanged();
                Toast.makeText(context,mensajeModificar,Toast.LENGTH_LONG).show();
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
            listaArticulos= new ArrayList<>();
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
            adapter = new AdaptadorArticulos(context, listaArticulos);
        } catch (Exception ex ){
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
                }
            rs.close();
            LlenaSpinner(st);
        }


        catch (Exception ex ){
            ex.printStackTrace();
            result2 = "Conexion no exitosa";
        }
    }

    public void LlenaSpinner(Statement st){
        try{
            ResultSet rs = st.executeQuery("SELECT id, descripcion FROM categoria ");
            result2 = " ";
            listaCategorias = new ArrayList<>();
            ECategoria categoria;
            while(rs.next()) {
                categoria=new ECategoria();
                categoria.setId(rs.getInt("id"));
                categoria.setDescripcion(rs.getString("descripcion"));
                listaCategorias.add(categoria);
            }}
        catch (Exception ex ){
            ex.printStackTrace();
            result2 = "Conexion no exitosa";
        }
    }

    public void ModificarArticulo(Statement st, EArticulo art, String id){
        try{

            String query= String.format("update articulo set nombre = '" + art.getNombre()+ "' ,stock = " +art.getStock()+ ", idcategoria = "+art.getCategoria().getId()+" where id = "+id);
            int rs = st.executeUpdate(query);
            if (rs > 0)
                mensajeModificar = "Artículo modificado con éxito!";
            else
                mensajeModificar = "Error al modificado el artículo!";

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
