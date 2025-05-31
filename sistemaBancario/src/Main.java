import java.util.Date;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        cliente c1 = new cliente();
        c1.setNombre("Luis");
        c1.setApellido("Picado");
        c1.setDireccion("Desamparados");
        c1.setTelefono("63357614");
        c1.setCedula("702230055");

        CuentaBancaria cuenta1 = new CuentaBancaria("Ahorro", c1);

        System.out.print("Ingrese el monto del depósito: ");
        double monto = scanner.nextDouble();

        cuenta1.depositar(monto);

        Transaccion t = new Transaccion("T001", "Depósito", monto, new Date(), cuenta1);

        System.out.println(t);



        }
    }
