package BL;

import java.util.ArrayList;

public class GestorCliente {

    private ArrayList<Cliente> clientes = new ArrayList<>();

    public boolean registrarCliente(Cliente c) throws Exception {
        if (clientes.contains(c)) throw new Exception("Cliente ya registrado.");
        return clientes.add(c);
    }

    public boolean modificarCliente(String cedula, Cliente nuevo) throws Exception {
        Cliente existente = buscarCliente(cedula);
        if (existente == null) throw new Exception("Cliente no encontrado.");

        existente.setNombre(nuevo.getNombre());
        existente.setApellido(nuevo.getApellido());
        existente.setDireccion(nuevo.getDireccion());
        existente.setCorreoElectronico(nuevo.getCorreoElectronico());
        return true;
    }

    public boolean eliminarCliente(String cedula) throws Exception {
        Cliente c = buscarCliente(cedula);
        if (c == null) throw new Exception("Cliente no encontrado.");
        return clientes.remove(c);
    }

    public Cliente buscarCliente(String cedula) {
        for (Cliente c : clientes) {
            if (c.getCedula().equals(cedula)) return c;
        }
        return null;
    }

    public ArrayList<Cliente> listarClientes() {
        return clientes;
    }
}