package model;

public class ProductoAjustado implements Producto
{
	private String nombreNuevo;
	private float precioNuevo;
	private int cal;

	public ProductoAjustado(String nombre, float precio_nuevo, int cal)
	{

		this.nombreNuevo = nombre;
		this.precioNuevo = precio_nuevo;
		this.cal = cal;
	}

	@Override
	public float getPrecio()
	{
		return this.precioNuevo;
	}

	@Override
	public String getNombre()
	{
		return this.nombreNuevo;
	}

	@Override
	public String generarTextoFactura()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCal()
	{
		return this.cal;
	}

}
