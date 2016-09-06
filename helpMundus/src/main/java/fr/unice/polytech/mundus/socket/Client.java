package fr.unice.polytech.mundus.socket;

import fr.unice.polytech.mundus.protocol.Request;
import fr.unice.polytech.mundus.data.Student;
import fr.unice.polytech.mundus.data.User;
import fr.unice.polytech.mundus.protocol.Response;

import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author TANG Yi
 */
public class Client {
   // public static final int PORT = 8888;
    private final String IP = "127.0.0.1";
    private static Socket socket;
    private  ObjectInputStream ois;
    private  ObjectOutputStream oos ;
    private static Scanner input = new Scanner(System.in);
    private static Request request = new Request();
    private static Response response = new Response();

    public Client(){
        try{
            this.socket = new Socket(IP,8888);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void clos() throws IOException{
        this.socket.close();
        this.oos.close();
        this.ois.close();
    }




    private static void showMainMenu(int number) {

        int choice = number;
        switch (choice) {
            case 1:
                signUp();
                break;
            case 2:
                ConsultInfo();
                break;
            case 3:
                ConsultSpe();
                break;
            case 4:
                ConsultSta();
                break;
            case 5:
                ChangeInfo();
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
            System.out.print("Please choose your speciality: \n 1.SI\n 2.MAM\n 3.eau\n 4.electronique\n 5.Bio\n");
            int choice = input.nextInt();
            Student.Speciality speciality = null;
            switch (choice) {
                case 1:
                    speciality = Student.Speciality.SI;
                    break;
                case 2:
                    speciality = Student.Speciality.MAM;
                    break;
                case 3:
                    speciality = Student.Speciality.eau;
                    break;
                case 4:
                    speciality = Student.Speciality.electronique;
                    break;
                case 5:
                    speciality = Student.Speciality.Bio;
                    break;
                default:
                    System.out.println("Not a number from 1 to 5.");
                    System.out.println("**********************");
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
                System.out.println("**********************");
                continue;
            }
            System.out.print("Please enter your e-mail: ");
            String mail = input.next();

            Student user = new Student(username, password, mail, speciality);
            request.setCmd(Request.Command.signUp);
            request.setDetail(user);
            sendData(request);
            response = getData();
            System.out.println("*****************");
            System.out.println(response.getContent());
            if (response.isFlag() == true) {//success in sign-up
                System.out.println("*****************");
                break;

            }
        }
        //signIn();//sign in after sign-up
    }

    private static void ConsultInfo() {
        request.setCmd(Request.Command.consult);
        System.out.println("You can choose:houseRenting, contactAli,transport");
        System.out.println("Please enter your choice");
        String choice;
        while(!(choice=input.nextLine()).equals("fin")) {
            String detail = "practical" + "+" + choice;
            request.setDetail(detail);
            sendData(request);

        }

    }

    private static void ConsultSta() {
        request.setCmd(Request.Command.consult);
        System.out.println("You can choose:houseRenting, contactAli,transport");
        System.out.println("Please enter your choice");
        String detail="internship"+"+"+input.nextLine();
        request.setDetail(detail);
        sendData(request);
    }

    private static void ConsultSpe() {
        request.setCmd(Request.Command.consult);
        System.out.println("You can choose:houseRenting, contactAli,transport");
        System.out.println("Please enter your choice");
        String detail="speciality"+"+"+input.nextLine();
        request.setDetail(detail);
        sendData(request);
    }

    private static void ChangeInfo(){
        System.out.println("please print the name and the content you want to change");
        request.setCmd(Request.Command.change);
        String detail=input.nextLine();
        request.setDetail(detail);
        sendData(request);


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

    /*private static void signIn() {
        System.out.println("Sign-in");
    }
    */



    public static void main(String[] args)throws IOException{


        Client cl= new Client();

        System.out.println("***** Welcome to Help-Mundus! *****");
        System.out.println("1.Sign up(for future Mundus)\n2.Practical information\n3.Speciality\n4.Stage\n5.Change info(for responser)\n6.Quit");
        System.out.println("*************************");
        System.out.println("please enter your choice:");
        int choice = input.nextInt();

            while (true) {
                showMainMenu(choice);

                //socket = new Socket("localhost",8888);
                sendData(request);//send data to the server
                response = getData();
                System.out.println("*****************");
                System.out.println(response.getContent());
                if (response.isFlag() == true) {//success in sign-up
                    System.out.println("*****************");
                    break;
                    //not go into the loop to sign-up again
                }
            }//catch (IOException e) {
            // e.printStackTrace();
            // }
            cl.clos();
        }


    }
