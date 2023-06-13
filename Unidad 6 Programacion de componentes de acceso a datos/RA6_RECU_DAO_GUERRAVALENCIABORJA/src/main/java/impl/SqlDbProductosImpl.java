package impl;

import java.util.ArrayList;

import dao.ProductoDAO;
import datos.PlatosMenus;
import datos.Productos;

public class SqlDbProductosImpl implements ProductoDAO{

    @Override
    public int InsertarProducto(Productos p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'InsertarProducto'");
    }

    @Override
    public boolean ActualizarDatos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ActualizarDatos'");
    }

    @Override
    public boolean InsertarMenu(int id_menu, int id_plato) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'InsertarMenu'");
    }

    @Override
    public ArrayList<PlatosMenus> ConsultarMenu(int id_menu) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ConsultarMenu'");
    }

    @Override
    public Productos ConsultarProducto(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ConsultarProducto'");
    }

    @Override
    public ArrayList<Productos> TodosLosProductos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'TodosLosProductos'");
    }

}
