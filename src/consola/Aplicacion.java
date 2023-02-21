package consola;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

import model.Combo;
import model.Ingrediente;
import model.Pedido;
import model.Producto;
import model.ProductoAjustado;
import model.ProductoMenu;
import model.Restaurante;

public class Aplicacion
{
	Restaurante restaurante;

	public void mostrarMenu()
	{
		System.out.println("1.) Cargar y Mostrar Menu");
		System.out.println("2.) Iniciar nuevo pedido ");
		System.out.println("3.) Agregar elemento a pedido ");
		System.out.println("4.) Cerrar pedido y guardar factura ");
		System.out.println("5.) Consultar informacion segun id ");
		System.out.println("6.) Salir");

	}

	public void ejecutarAplicacion()
	{
		mostrarMenu();
		int opcion = Integer.parseInt(input("\nSeleccione una opcion"));
		while (true)
		{
			if (opcion == 1)
			{
				ejecutarOpcion1();
			} else if (opcion == 2)
			{
				ejecutarOpcion2();
			} else if (opcion == 3)
			{
				ejecutarOpcion3();
			} else if (opcion == 4)
			{
				ejecutarOpcion4();
			} else if (opcion == 5)
			{
				ejecutarOpcion5();
			} else if (opcion == 6)
			{
				System.out.println("Saliendo de la app...");
				break;
			} else
			{
				System.out.println("Seleccione una opcion valida, por favor");

			}
			mostrarMenu();
			opcion = Integer.parseInt(input("\nSeleccione una opcion"));
		}
	}

	private void ejecutarCargarDatos()
	{
		System.out.println("Cargando información de la base de datos...");
		restaurante = new Restaurante();
		File archivoIngredientes = new File("./Data/ingredientes.txt");
		File archivoMenu = new File("./Data/menu.txt");
		File archivoCombos = new File("./Data/combos.txt");
		File archivoBebidas = new File("./Data/bebidas.txt");
		restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos,archivoBebidas);

	}

	private void ejecutarOpcion1()
	{
		ejecutarCargarDatos();
	}

	private void ejecutarOpcion2()
	{
		String nombreCliente = input("Ingrese su nombre, porfavor: ");
		String direccionCliente = input("Ingrese su direccion, por favor");
		restaurante.iniciarPedido(nombreCliente, direccionCliente);
	}

	private void ejecutarOpcion3()
	{

		System.out.println("Agregar un producto al pedido...\n ");
		System.out.println("1.) Combo ");
		System.out.println("2.) Producto Menu ");
		System.out.println("3.) Bebidas");
		int opcion = Integer.parseInt(input("\nSeleccione una opcion"));
		System.out.println("\n");
		if (opcion == 1)
		{
			ArrayList<Combo> lista_combos = restaurante.getCombos();

			int j = 0;
			for (Combo i : lista_combos)
			{
				System.out.println((j + 1) + ". " + i.getNombre());
				j++;
			}

			int producto_escogido = Integer.parseInt(input("Que combo desea (1 - " + j + ")"));
			if (0 <= producto_escogido && producto_escogido <= j)
			{
				restaurante.agregarProducto(lista_combos.get(producto_escogido - 1), true);

			} else
			{
				System.out.println("Opcion errónea");
			}
		} else if (opcion == 2)
		{
			ArrayList<Producto> lista_productos = restaurante.getMenuBase();
			int j = 0;
			for (Producto i : lista_productos)
			{
				System.out.println((j + 1) + ". " + i.getNombre() + "|| $" + i.getPrecio()+" || cal: "+i.getCal());
				j++;
			}
			int producto_escogido = Integer.parseInt(input("Que producto desea (0 - " + j + ")"));
			if (0 <= producto_escogido && producto_escogido <= j)
			{
				restaurante.agregarProducto(lista_productos.get(producto_escogido - 1), false);

			} else
			{
				System.out.println("Opcion errónea");
			}
		}else if (opcion == 3)
		{
			ArrayList<Producto> lista_productos = restaurante.getBebidas();
			int j = 0;
			for (Producto i : lista_productos)
			{
				System.out.println((j + 1) + ". " + i.getNombre() + "|| $" + i.getPrecio()+" || cal: "+i.getCal());
				j++;
			}
			int producto_escogido = Integer.parseInt(input("Que producto desea (0 - " + j + ")"));
			if (0 <= producto_escogido && producto_escogido <= j)
			{
				restaurante.agregarProducto(lista_productos.get(producto_escogido - 1), false);

			} else
			{
				System.out.println("Opcion errónea");
			}
		}

	}

	private void ejecutarOpcion4()
	{
		Pedido pedido_ahora = restaurante.getPedidoEnCurso();
		int id_pedido = pedido_ahora.getIdPedido();
		restaurante.cerrarYGuardarPedido();
		System.out.println("Pedido Cerrado #" + id_pedido);
	}

	private void ejecutarOpcion5()
	{
		int id_pedido = Integer.parseInt(input("Ingrese un id para consultar un pedido: "));
		restaurante.getPedidoId(id_pedido);

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

	public static void main(String[] args)
	{
		{
			Aplicacion consola = new Aplicacion();
			consola.ejecutarAplicacion();
		}
	}
}