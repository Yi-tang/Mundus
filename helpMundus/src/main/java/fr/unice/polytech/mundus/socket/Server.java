package fr.unice.polytech.mundus.socket;

import fr.unice.polytech.mundus.data.Student;
import fr.unice.polytech.mundus.data.helper.ActionXML;
import fr.unice.polytech.mundus.protocol.Request;
import fr.unice.polytech.mundus.protocol.Response;
import org.dom4j.DocumentException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author TANG Yi
 */
public class Server {
    public static final int PORT = 8888;

    public static void main(String[] args) {

        Server server = new Server();
        server.init();
    }
    public void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server on port:"+PORT+",waiting for the client...\n");
            while (true) {
                new ServerThread(serverSocket.accept()).start();//handle the client
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private class ServerThread extends Thread {
        private Socket socket;
        private ObjectInputStream ois = null;
        private ObjectOutputStream oos = null;
        private Request request;
        private Response response;
        public ServerThread(Socket client) {
            super("ServerThread");
            socket = client;
        }
        @Override
        public void run() {
            try {
                ois = new ObjectInputStream(socket.getInputStream());//read client data
                oos = new ObjectOutputStream(socket.getOutputStream());
                //handle the request
                request = (Request)ois.readObject();

                response =  excute(request);
                //send to the client
                oos.writeObject(response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private Response excute(Request request) throws DocumentException, IOException {
            ActionXML actionXML = null;
            Response response = new Response();
            Request.Command cmd = request.getCmd();//get the command of the request
            if(cmd.equals(Request.Command.signUp)){//registeration of user
                Student student = (Student)request.getDetail();
                actionXML = new ActionXML(new File("helpMundus/src/main/resources/user.xml"));
                boolean flag = actionXML.addStudent(student);
                response.setFlag(flag);
                if(flag){
                    response.setContent("Registered successfully");
                }else{
                    response.setContent("fail to register, try again please.");
                }
            }
            System.out.println("executed a request.");
            return response;
        }

    }

}

