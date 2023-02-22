package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Restaurante
{
	private ArrayList<Ingrediente> ingredientes;
	private ArrayList<Producto> productos;
	private ArrayList<Producto> bebidas;
	public Hashtable<String, Integer> ingresPrecio;
	private Hashtable<String, Producto> produNombre;
	private ArrayList<Combo> combos;
	private ArrayList<Pedido> pedidos;
	private ArrayList<Producto> itemsCombo;
	private Pedido pedidoAhora;
	private Hashtable<Integer, Hashtable<String, Integer>> infoFacturas;

	public Restaurante()
	{
		this.ingredientes = new ArrayList<Ingrediente>();
		this.productos = new ArrayList<Producto>();
		this.combos = new ArrayList<Combo>();
		this.pedidos = new ArrayList<Pedido>();
		this.bebidas = new ArrayList<Producto>();
		this.infoFacturas = new Hashtable<Integer, Hashtable<String, Integer>>();
	}

	public void iniciarPedido(String nombreCliente, String direccionCliente)
	{
		this.pedidoAhora = new Pedido(nombreCliente, direccionCliente);
		int id_ahora = pedidos.size();
		this.pedidoAhora.setId(id_ahora + 1);
		this.infoFacturas.put(pedidoAhora.getIdPedido(), new Hashtable<String, Integer>());

	}

	public void agregarProducto(Producto producto, boolean combo)
	{
		if (combo)
		{
			itemsCombo = ((Combo) producto).getItems();
			for (Producto i : itemsCombo)
			{

				verificarIngredientes(i, producto.getPrecio());
			}
		} else
		{
			verificarIngredientes(producto, 0);
		}

	}

	public void cerrarYGuardarPedido()
	{
		File archivito = new File("./Data/" + this.pedidoAhora.getIdPedido() + ".txt");
		pedidoAhora.guardarFactura(archivito);

		pedidos.add(pedidoAhora);
		if (pedidoAhora.getIdPedido() > 1)
		{
			boolean respuesta = compararPedidos(pedidoAhora.getIdPedido());
			if (respuesta)
			{
				System.out.println("Ya existia un pedido igual");
			} else
			{
				System.out.println("No existía un pedido igual");
			}
		}

	}

	public Hashtable<String, Integer> getingresPrecio()
	{
		return ingresPrecio;

	}

	public Pedido getPedidoEnCurso()
	{
		return this.pedidoAhora;

	}

	public void getPedidoId(int id)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("./Data/" + id + ".txt"));
			String linea;
			linea = br.readLine();
			while (linea != null)
			{

				System.out.println(linea);
				linea = br.readLine();
			}
			br.close();
		} catch (Exception e)
		{
			System.out.println("El id de ese pedido no existe");
			;
		}

	}

	public ArrayList<Producto> getMenuBase()
	{
		return this.productos;

	}

	public ArrayList<Producto> getBebidas()
	{
		return this.bebidas;

	}

	public ArrayList<Ingrediente> getIngredientes()
	{
		return this.ingredientes;
	}

	public ArrayList<Combo> getCombos()
	{
		return this.combos;
	}

	private boolean compararPedidos(int pedidoAhora2)
	{
		Hashtable<String, Integer> pedidoYa = infoFacturas.get(pedidoAhora2);
		boolean respuesta = false;
		for (int i = 1; i < pedidoAhora2; i++)
		{

			int cont = 0;
			Hashtable<String, Integer> pedidoviejo = infoFacturas.get(i);
			Set<String> set = pedidoviejo.keySet();
			if (set.size() == pedidoYa.keySet().size())
			{

				for (String j : set)
				{

					if (pedidoYa.containsKey(j))
					{

						if (pedidoYa.get(j).equals(pedidoviejo.get(j)))
						{
							cont++;
						}
						if (cont == pedidoYa.size())
						{
							respuesta = true;
						}
					}
				}

			}

		}
		return respuesta;

	}

	public void verificarIngredientes(Producto prod, float descuento)
	{
		Hashtable<String, Integer> ingresPrecio = getingresPrecio();
		ArrayList<Ingrediente> lista_ingres = getIngredientes();
		int k = 0;
		for (Ingrediente i : lista_ingres)
		{
			System.out.println(k + 1 + ". " + i.getNombre() + ": " + i.getCostoAdicional());
			k++;
		}
		System.out.println(k + 1 + ": Ninguno");
		float precio_nuevo;
		String nombre_nuevo;
		if (descuento != 0)
		{
			precio_nuevo = prod.getPrecio() - (prod.getPrecio() * descuento);
			nombre_nuevo = "Combo " + prod.getNombre();
		} else
		{
			precio_nuevo = prod.getPrecio();
			nombre_nuevo = prod.getNombre();
		}

		while (true)
		{
			int ingre_escogido = Integer.parseInt(input("\nQue Ingrediente desea eliminar o adicionar (1 - " + (k + 1)
					+ ") para el producto " + nombre_nuevo));
			if (0 <= ingre_escogido && ingre_escogido <= k)
			{
				int adi_eli = Integer.parseInt(input("\nDesea \n1) Adicionar\n2) Eliminar\nEscoja"));
				if (adi_eli == 1)
				{
					nombre_nuevo = nombre_nuevo + "con" + lista_ingres.get(ingre_escogido - 1).getNombre();
					precio_nuevo = precio_nuevo + ingresPrecio.get(lista_ingres.get(k - 1).getNombre());

				} else if (adi_eli == 2)
				{
					nombre_nuevo = nombre_nuevo + "sin" + lista_ingres.get(ingre_escogido - 1).getNombre();

				} else
				{
					System.out.println("Opcion Errónea");
				}
			} else if (ingre_escogido == k + 1)
			{
				if (nombre_nuevo != prod.getNombre())
				{
					ProductoAjustado produ_nuevo = new ProductoAjustado(nombre_nuevo, precio_nuevo, prod.getCal());
					pedidoAhora.agregarProducto(produ_nuevo);

				} else
				{
					pedidoAhora.agregarProducto(prod);
				}

				break;
			} else
			{
				System.out.println("Opcion errónea");
			}

		}
		if (infoFacturas.get(pedidoAhora.getIdPedido()).contains(nombre_nuevo))
		{
			int cantidad = infoFacturas.get(pedidoAhora.getIdPedido()).get(nombre_nuevo);
			infoFacturas.get(pedidoAhora.getIdPedido()).replace(nombre_nuevo, cantidad + 1);
		} else
		{
			infoFacturas.get(pedidoAhora.getIdPedido()).put(nombre_nuevo, 1);
		}

	}

	public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos,
			File archivoBebidas)
	{
		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
		cargarBebidas(archivoBebidas);
		cargarCombos(archivoCombos);

	}

	private void cargarIngredientes(File archivoIngredientes)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));
			String linea;
			linea = br.readLine();
			ingresPrecio = new Hashtable<String, Integer>();
			while (linea != null)
			{
				String[] partes = linea.split(";");
				int valor = Integer.valueOf(partes[1]);
				ingredientes.add(new Ingrediente(partes[0], valor));
				ingresPrecio.put(partes[0], Integer.parseInt(partes[1]));
				linea = br.readLine();
			}
			br.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void cargarMenu(File archivoMenu)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(archivoMenu));
			String linea;
			linea = br.readLine();
			System.out.println("\nMenu por separado: ");
			produNombre = new Hashtable<String, Producto>();
			while (linea != null)
			{

				String[] partes = linea.split(";");
				int valor = Integer.valueOf(partes[1]);
				int cal = Integer.valueOf(partes[2]);
				ProductoMenu produ = new ProductoMenu(partes[0], valor, cal);
				productos.add(produ);
				produNombre.put(produ.getNombre(), produ);
				System.out.println(partes[0] + " : $" + partes[1]);
				linea = br.readLine();
			}
			br.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void cargarBebidas(File archivoMenu)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(archivoMenu));
			String linea;
			linea = br.readLine();
			System.out.println("\nBebidas: ");
			while (linea != null)
			{

				String[] partes = linea.split(";");
				int valor = Integer.valueOf(partes[1]);
				int cal = Integer.valueOf(partes[2]);
				ProductoBebida produ = new ProductoBebida(partes[0], valor, cal);
				bebidas.add(produ);
				produNombre.put(produ.getNombre(), produ);
				System.out.println(partes[0] + " : $" + partes[1]);
				linea = br.readLine();
			}
			br.close();
			System.out.println("\n");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void cargarCombos(File archivoCombos)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(archivoCombos));
			String linea;
			linea = br.readLine();
			System.out.println("\nCombos: ");
			while (linea != null)
			{

				String[] partes = linea.split(";");
				float descuento = Float.valueOf(partes[1].substring(0, partes[1].length() - 1));

				Combo combo = new Combo(partes[0], descuento / 100);

				for (int i = 2; i < partes.length; i++)
				{
					String nombre = partes[i];
					combo.agregarItemACombo(produNombre.get(nombre));

				}

				combos.add(combo);
				System.out.println(partes[0]);

				linea = br.readLine();
			}
			br.close();
			System.out.println("\n");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		} catch (IOException e)
		{
			return null;
		}
	}
}
