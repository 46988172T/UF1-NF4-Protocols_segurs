import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;

/**
 * Created by 46988172t on 13/04/16.
 */
public class SecureServer {
    public static void main(String[] args) throws IOException{
        double num1, num2, total = 0.0;
        int operacion;
        char opr = '\n';

        System.out.println("Obtenint factoria servidor");
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        System.out.println("Creant socket servidor");
        SSLServerSocket sss = (SSLServerSocket) ssf.createServerSocket();

        System.out.println("Binding");
        InetSocketAddress address = new InetSocketAddress("0.0.0.0", 5556);
        sss.bind(address);

        SSLSocket cliente = (SSLSocket) sss.accept();
        System.out.println("Rebut");

// aqui empieza la calculadora, parte server.

        ObjectOutputStream o = new ObjectOutputStream(cliente.getOutputStream());
        ObjectInputStream i = new ObjectInputStream(cliente.getInputStream());

        operacion = i.readInt();
        num1 = i.readDouble();
        num2 = i.readDouble();

        if (operacion == 1) {

            opr = '+';
            total = (num1 + num2);

        } else if (operacion == 2) {

            opr = '-';
            total = (num1 - num2);

        } else if (operacion == 3) {

            opr = 'x';
            total = (num1 * num2);

        } else {

            opr = '/';
            total = (num1 / num2);

        }

        o.writeDouble(total);
        o.writeChar(opr);
        o.flush();

        o.close();
        i.close();

//final de calculadora, parte server

        sss.close();
        System.out.println("Acabat");
    }
}


//java -Djavax.net.ssl.keyStore=servidorPropio -Djavax.net.ssl.trustStore=servidorPropio -Djavax.net.ssl.keyStorePassword=leo240586 SecureServer

