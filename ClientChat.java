package CltServer;



import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientChat{
    public static void main(String[] args) {
        Socket soc;
        try {
            soc = new Socket("localhost", 1003);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
                 Scanner scanner = new Scanner(System.in)) {

        
                System.out.println(reader.readLine());
                System.out.print("nom : ");
                String clientName = scanner.nextLine();
                writer.write(clientName + "\n");
                writer.flush();

                
                System.out.println("Serveur: " + reader.readLine());
     
                String message;
                while (true) {
                    System.out.print("message: ");
                    message = scanner.nextLine();

                    writer.write(message + "\n");
                    writer.flush();

                    if ("quit".equalsIgnoreCase(message)) {
                        break;
                    }

                 
                    System.out.println("Serveur: " + reader.readLine());
                }
            }

            soc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
