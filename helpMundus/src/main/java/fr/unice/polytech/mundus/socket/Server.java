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
            System.out.println("Server on port:" + PORT + ",waiting for the client...\n");
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
                request = (Request) ois.readObject();

                response = excute(request);
                //send to the client
                oos.writeObject(response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Response excute(Request request) throws DocumentException, IOException {

            Request re = request;
            Response response = new Response();
            Request.Command cmd = re.getCmd();//get the command of the request

            if (cmd.equals(Request.Command.signUp)) {//registeration of user
                response = signup(re);
            }
            if (cmd.equals(Request.Command.consult)) {
                response=consult(re);
            }
            if(cmd.equals(Request.Command.change))
            System.out.println("executed a request.");
            return response;
        }

        private Response signup(Request request) throws DocumentException, IOException {
            Request req = request;
            Response res = new Response();
            Student student = (Student) req.getDetail();
            ActionXML actionXML = new ActionXML(new File("../helpMundus/src/main/resources/user.xml"));
            boolean flag = actionXML.addStudent(student);
            res.setFlag(flag);
            if (flag) {
                res.setContent("Registered successfully");
            } else {
                res.setContent("fail to register, try again please.");
            }
            return res;
        }

        private Response consult(Request request) throws DocumentException {
            Request req = request;
            Response res = new Response();
            String detail = (String) req.getDetail();
            System.out.println(detail);
            ActionXML actionXML = new ActionXML(new File("../helpMundus/src/main/resources/information.xml"));


            String[] d = detail.split("\\+");
            String root = d[0];
            String element = d[1];
            if (root.equals("practical")) {
                String answer = actionXML.getInformation("practical", element);
                res.setContent(answer);
            }
            if (root.equals("stage")) {
                String answer = actionXML.getInformation("internship", element);
                res.setContent(answer);
            }
            if (root.equals("speciality")) {
                String answer = actionXML.getInformation("speciality", element);
                res.setContent(answer);

            }
            return res;

        }

        private Response change(Request request) throws DocumentException, IOException {
            Request req = request;
            Response res = new Response();
            ActionXML actionXML = new ActionXML(new File("../helpMundus/src/main/resources/information.xml"));
            String detail = (String) req.getDetail();
            String[] d = detail.split("\\+");
            String root = d[0];
            String element = d[1];
            String content = d[2];
            if (root.equals("practical")) {
                actionXML.modifyInformation("practical",element,content);
                res.setContent("change successfully");
            }
            if (root.equals("internship")) {
                actionXML.modifyInformation("internship",element,content);
                res.setContent("change successfully");
            }
            if (root.equals("speciality")) {
                actionXML.modifyInformation("speciality",element,content);
                res.setContent("change successfully");

            }
            return res;
        }


    }
}

