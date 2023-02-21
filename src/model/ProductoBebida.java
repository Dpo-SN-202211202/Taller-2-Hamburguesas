package model;

public class ProductoBebida implements Producto
{

	private String nombre;
	private int cal;
	private float precioBase;
	
	public ProductoBebida(String nombre, int precioBase, int cal)
	{
		this.nombre=nombre;
		this.precioBase=precioBase;
		this.cal=cal;
	}

	@Override
	public float getPrecio()
	{
		return this.precioBase;
	}

	@Override
	public String getNombre()
	{
		return this.nombre;
	}

	@Override
	public String generarTextoFactura()
	{
		return null;
	}

	@Override
	public int getCal()
	{
		return this.cal;
	}
}
