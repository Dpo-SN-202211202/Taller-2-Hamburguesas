package model;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Pedido
{
	private static int numeroPedidos;
	
	private int idPedido;

	private String nombreCliente;

	private String direccionCliente;
	
	private float precioNeto;
	
	private int calorias;
	
	private ArrayList<Producto> productos;
	private Hashtable<Integer,String> tabla_id;

	public Pedido(String nombreCliente, String direccionCliente)
	{
		this.productos=new ArrayList<Producto>();
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.precioNeto=0;
		tabla_id=new Hashtable<Integer,String>();

	}

	public int getIdPedido()
	{
		return this.idPedido;
	} 
	
	
	public void setId(int id)
	{
		this.idPedido=id;
	}

	public void agregarProducto(Producto nuevoItem)
	{
		this.productos.add(nuevoItem);
		this.precioNeto+=nuevoItem.getPrecio();
		this.calorias+=nuevoItem.getCal();
	}
	
	public float getPrecioNetoPedido()
	{
		return this.precioNeto;
	}
	
	private double getPrecioTotalPedido()
	{
		return this.precioNeto+getPrecioIVAPedido();
	}
	
	private int getTotalCalorias()
	{
		
		return this.calorias;
	}

	
	private double getPrecioIVAPedido()
	{
		return this.precioNeto*0.19;
	}
	
	private String generarTextoFacura()
	{
		
		String msj="Pedido #"+this.idPedido;
		msj+="\n";
		msj+="Nombre: "+this.nombreCliente;
		msj+="\n";
		msj+="Dirección: "+this.direccionCliente;
		msj+="\n";
		msj+="\n";
		msj+="PRODUCTOS";
		msj+="\n";
		int j=0;
		for (Producto i:productos)
		{
			msj+=(j+1)+". "+i.getNombre()+" || $"+i.getPrecio()+" || cal:"+i.getCal();
			msj+="\n";
			j++;
		}
		msj+="\n";
		msj+="Total: $"+getPrecioTotalPedido();
		msj+="\n";
		msj+="Total calorias: "+getTotalCalorias();
		msj+="\n";
		return msj;
	}
	

	public void guardarFactura(File archivo)
	{
		tabla_id.put(this.idPedido,generarTextoFacura());
		try
		{
			FileWriter bw =new FileWriter(archivo);
			bw.write(generarTextoFacura());
			bw.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
}
