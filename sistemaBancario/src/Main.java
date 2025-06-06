import java.util.Date;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        cliente c1 = new cliente();
        System.out.print("Ingrese el nombre ");
        c1.setNombre(scanner.next());
        System.out.print("Ingrese el apellido ");
        c1.setApellido(scanner.next());
        System.out.print("Ingrese la direccion ");
        c1.setDireccion(scanner.next());
        System.out.print("Ingrese el numero de telefono ");
        c1.setTelefono(scanner.next());
        System.out.print("Ingrese el numero de cedula");
        c1.setCedula(scanner.next());

        CuentaBancaria cuenta1 = new CuentaBancaria("Ahorro", c1);

        System.out.print("Ingrese el monto del depósito inicial: ");
        double monto = scanner.nextDouble();

        cuenta1.depositar(monto);

        Transaccion t = new Transaccion("T001", "Depósito", monto, new Date(), cuenta1);

        System.out.println(t);



        }
    }
