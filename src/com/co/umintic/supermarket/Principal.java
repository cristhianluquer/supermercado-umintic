/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.co.umintic.supermarket;
import com.co.umintic.supermarket.controllers.ClienteController;
import com.co.umintic.supermarket.controllers.ProductoController;
import com.co.umintic.supermarket.controllers.ProveedorController;
import com.co.umintic.supermarket.controllers.VentaController;
import com.co.umintic.supermarket.model.Cliente;
import com.co.umintic.supermarket.model.Pago;
import com.co.umintic.supermarket.model.Producto;
import com.co.umintic.supermarket.model.Proveedor;
import com.co.umintic.supermarket.model.Telefono;
import com.co.umintic.supermarket.model.Venta;
import java.util.ArrayList;
import javax.crypto.AEADBadTagException;

public class Principal {

    /**
     * @param args the command line arguments
     
    public static void main(String[] args) {
        try {
            ClienteController clienteController = new ClienteController();
            Cliente cliente = clienteController.obtenerClientePorId(1);
            
            System.out.println("Compras: " + cliente.getCompras().size());
        }
        catch (Exception ex) {
            System.err.println("Error" + ex.getMessage());
        }
    }
*/
    private static ArrayList<Producto> filtrarPorId(ArrayList<Producto> productos) {
        ArrayList<Producto> definitivos = new ArrayList<>();
        
        for (Producto producto : productos) {
            int idProducto = producto.getId();
            
            if (idProducto == 1) {
                definitivos.add(producto);
                continue;
            }
            if (idProducto == 2) {
                definitivos.add(producto);
                definitivos.add(producto);
            }
        }
        
        return definitivos;
    }
    
}
