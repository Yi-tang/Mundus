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
 * @author WANG Yijie
 */
public class Server {
    public static final int PORT = 8888;
    public static final String password = "123";

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
            response = new Response();
            Request.Command cmd = re.getCmd();//get the command of the request

            if (cmd.equals(Request.Command.SIGN_UP)) {//registeration of user
                System.out.println("executed a request.");
                return signup(re);
            }
            if (cmd.equals(Request.Command.CONSULT)) {
                System.out.println("executed a request.");
                return consult(re);
            }
            if(cmd.equals(Request.Command.CHANGE)){
                System.out.println("executed a request.");
                return change(re);
            }else{
                return null;
            }
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
            ActionXML actionXML = new ActionXML(new File("../helpMundus/src/main/resources/INFORMATION.xml"));


            String[] d = detail.split("\\+");
            String root = d[0];
            String element = d[1];
            if (root.equals("practical")) {
                String answer = actionXML.getInformation("practical", element);
                res.setContent(answer);
            }
            if (root.equals("internship")) {
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
            final String suc = "CHANGE successfully";
            Request req = request;
            Response res = new Response();
            ActionXML actionXML = new ActionXML(new File("../helpMundus/src/main/resources/INFORMATION.xml"));
            String detail = (String) req.getDetail();
            String[] d = detail.split("\\+");
            String pass = d[0];
            String root = d[1];
            String element = d[2];
            String content = d[3];
            if(!password.equals(pass)){
                res.setFlag(false);
                res.setContent("password wrong");
            }
            res.setFlag(true);
            if ("practical".equals(root)) {
                actionXML.modifyInformation("practical",element,content);
                res.setContent(suc);
            }else if ("internship".equals(root)) {
                actionXML.modifyInformation("internship",element,content);
                res.setContent(suc);
            }else if ("speciality".equals(root)) {
                actionXML.modifyInformation("speciality",element,content);
                res.setContent(suc);
            }else{
                res.setFlag(false);
                res.setContent("No such a directory");
            }
            return res;
        }


    }
}

