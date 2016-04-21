import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * Created by 46988172t on 13/04/16.
 */
public class SecureClient {
    public static void main(String[] args) throws IOException{
        double num1;
        double num2;
        int operacion = 0;
        char opr;

        SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();

        SSLSocket cliente = (SSLSocket) ssf.createSocket();
        InetSocketAddress address = new InetSocketAddress("localhost", 5556);
        cliente.connect(address);


// aqui empieza la calculadora, parte cliente.

        ObjectInputStream i = new ObjectInputStream(cliente.getInputStream());
        ObjectOutputStream o = new ObjectOutputStream(cliente.getOutputStream());


        num1 = Double.parseDouble(JOptionPane.showInputDialog("Indica primer número"));
        num2 = Double.parseDouble(JOptionPane.showInputDialog("Indica segundo número"));
        while (!((operacion >= 1) && (operacion <= 4))) {
            operacion = Integer.parseInt(JOptionPane.showInputDialog("Indica la operación:\n1 ---> + Suma\n2 ---> - Resta\n3 ---> x Multiplicación\n4 ---> ÷ División"));
            if (!((operacion >= 1) && (operacion <= 4))) {
                System.out.println("Operación inválida");
            }
        }
        o.writeInt(operacion);
        o.writeDouble(num1);
        o.writeDouble(num2);
        o.flush();

        double total = i.readDouble();
        opr = i.readChar();
        System.out.println("Resultado: \n"+ num1+" "+opr+" "+num2+" = "+total);

        i.close();
        o.close();

//final de calculadora, parte cliente

        cliente.close();
        System.out.println("Acabat!");

    }
}
