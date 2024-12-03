package CltServer;

import java.io.*;
import java.net.*;
import java.util.*;

      
        public class ServeurChat {
     

            public static void main(String[] args) {
                ServerSocket s;
                try {
                    s = new ServerSocket(1003);
                    Thread t = new Thread(new AccepterClients(s));
                    t.start();
                    System.out.println("Serveur de chat en attente de connexions..");
                   
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        class AccepterClients implements Runnable {
            private ServerSocket so;
            private Socket sock;
            private int nbrclt = 1;

            public AccepterClients(ServerSocket s1) {
                so = s1;
            }

            public void run() {
                try {
                    while (true) {
                        sock = so.accept();
                        System.out.println("Connexion détectée !");
                        Thread t = new Thread(new ClientHandler(sock, nbrclt));
                        t.start();
                        nbrclt++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        class ClientHandler implements Runnable {
            private Socket sock;
            private int cltNumber;
            private String userName;

            public ClientHandler(Socket sock, int clientNumber) {
                this.sock = sock;
                this.cltNumber = clientNumber;
            }

            @Override
            public void run() {
            	
                try (OutputStreamWriter out = new OutputStreamWriter(sock.getOutputStream());
                		BufferedWriter writer = new BufferedWriter(out);
                		InputStreamReader input =	new InputStreamReader(sock.getInputStream());
                     BufferedReader reader = new BufferedReader(input)) {


                    writer.write("entrer votre nom :\n");
                    writer.flush();

                    userName = reader.readLine(); 
                    System.out.println("Client " +  userName + " (ID: " + cltNumber + ") est connecté !");
                    writer.write("Bonjour " +  userName + "! Vous êtes maintenant connecté.\n");
                    writer.flush();

           
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println("Message de " + userName + " : " + message);

                       
                        writer.write("Echo: " + message + "\n");
                        writer.flush();

                        if ("quit".equalsIgnoreCase(message)) {
                            System.out.println("Le client " + userName + " s'est déconnecté.");
                            break;
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        sock.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }