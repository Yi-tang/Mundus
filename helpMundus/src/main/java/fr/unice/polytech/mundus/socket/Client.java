package fr.unice.polytech.mundus.socket;

import fr.unice.polytech.mundus.protocol.Request;
import fr.unice.polytech.mundus.data.Student;
import fr.unice.polytech.mundus.protocol.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author TANG Yi
 * @author WANG Yijie
 */
public class Client {
    private static final String cut = "*************************";
    private static Socket socket;
    private static Scanner input = new Scanner(System.in);
    private static Request request = new Request();
    private static Response response = new Response();

    public Client(){
        try{
            socket = new Socket("localhost",8888);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void clos() throws IOException{
        socket.close();

    }




    private static void showMainMenu() {
        System.out.println("***** Welcome to Help-Mundus! *****");
        System.out.println("1.Sign up(for future Mundus)\n2.Practical INFORMATION\n3.Speciality\n4.Stage\n5.Change info(for responser)\n6.Quit");
        System.out.println(cut);
        System.out.println("please enter your choice:");
        int choice = input.nextInt();

        switch (choice) {
            case 1:
                signUp();
                break;
            case 2:
                consultInfo();
                break;
            case 3:
                consultSpe();
                break;
            case 4:
                consultSta();
                break;
            case 5:
                changeInfo();
                break;
            case 6:
                System.out.println("See you.");
                System.exit(0);
                break;

            default:
                System.out.println("Sorry!Error!");
                System.exit(0);
                break;
        }
    }



    /**
     * Registration of the user
     */
    private static void signUp(){
        while(true) {
            System.out.print("Please choose your speciality: \n 1.SI\n 2.MAM\n 3.EAU\n 4.ELECTRONIQUE\n 5.BIO\n");
            int choice = input.nextInt();
            Student.Speciality speciality ;
            switch (choice) {
                case 1:
                    speciality = Student.Speciality.SI;
                    break;
                case 2:
                    speciality = Student.Speciality.MAM;
                    break;
                case 3:
                    speciality = Student.Speciality.EAU;
                    break;
                case 4:
                    speciality = Student.Speciality.ELECTRONIQUE;
                    break;
                case 5:
                    speciality = Student.Speciality.BIO;
                    break;
                default:
                    System.out.println("Not a number from 1 to 5.");
                    System.out.println();
                    continue;
            }
            System.out.print("Please enter your name as the login: ");
            String username = input.next();
            System.out.print("Please enter the password: ");
            String password = input.next();
            System.out.print("Please enter the password again: ");
            String password2 = input.next();
            if (!password.equals(password2)) {
                System.out.println("The two passwords aren't the same.");
                System.out.println(cut);
                continue;
            }
            System.out.print("Please enter your e-mail: ");
            String mail = input.next();

            Student user = new Student(username, password, mail, speciality);
            request.setCmd(Request.Command.SIGN_UP);
            request.setDetail(user);
            sendData(request);
            response = getData();
            System.out.println(cut);
            System.out.println(response.getContent());
            if (response.isFlag()) {//success in sign-up
                System.out.println(cut);
                break;

            }
        }
        showMainMenu();
    }

    private static void consultInfo() {
        request.setCmd(Request.Command.CONSULT);
        System.out.println("You can choose:houseRenting, contactAli,transport");
        System.out.println("Please enter your choice");
        String choice=input.next();
        String detail = "practical" + "+" + choice;
        request.setDetail(detail);
        sendData(request);
        response = getData();
        System.out.println(cut);
        System.out.println(response.getContent());
        showMainMenu();

    }

    private static void consultSta() {
        request.setCmd(Request.Command.CONSULT);
        System.out.println("You can choose:SI,MAM,EAU,ELECTRONIQUE,BIO");
        System.out.println("Please enter your choice");
        String detail="internship"+"+"+input.next();
        request.setDetail(detail);
        sendData(request);
        response = getData();
        System.out.println(cut);
        System.out.println(response.getContent());
        showMainMenu();
    }

    private static void consultSpe() {
        request.setCmd(Request.Command.CONSULT);
        System.out.println("You can choose:SI,MAM,EAU,ELECTRONIQUE,BIO;");
        System.out.println("Please enter your choice");
        String detail="speciality"+"+"+input.next();
        request.setDetail(detail);
        sendData(request);
        response = getData();
        System.out.println(cut);
        System.out.println(response.getContent());
        showMainMenu();
    }

    private static void changeInfo(){
        System.out.println("please print the name and the content you want to CHANGE");
        request.setCmd(Request.Command.CHANGE);
        String detail=input.next();
        request.setDetail(detail);
        sendData(request);
        response = getData();
        System.out.println(cut);
        System.out.println(response.getContent());
        showMainMenu();


    }

    /**
     * send data to server as a request
     * @param request
     */
    private static void sendData(Request request) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(request);
        } catch (IOException e) {
            e.printStackTrace();
        }/*finally{
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * receive the response of the server
     */
    private static Response getData() {
        Response response = new Response();
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            response = (Response) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    public static void main(String[] args)throws IOException{


        Client cl= new Client();

        showMainMenu();

        cl.clos();
        }
    }
