package fr.unice.polytech.mundus.socket;

import fr.unice.polytech.mundus.protocol.Request;
import fr.unice.polytech.mundus.data.Student;
import fr.unice.polytech.mundus.data.User;
import fr.unice.polytech.mundus.protocol.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author TANG Yi
 */
public class Client {
    public static final int PORT = 8888;
    private static Socket socket = null;
    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;
    private static Scanner input = new Scanner(System.in);
    private static Request request = new Request();
    private static Response response = new Response();

    public static void main(String[] args) {
        showMainMenu();
    }

    private static void showMainMenu() {
        System.out.println("***** Welcome to Help-Mundus! *****");
        System.out.println("1.sign in\n2.sign up\n3.quit");
        System.out.println("*************************");
        System.out.println("please enter your choice");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                signIn();
                break;
            case 2:
                signUp();
                break;
            case 3:
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
        while(true){
            System.out.print("Please choose your speciality: \n 1.SI\n 2.MAM\n 3.eau\n 4.électronique\n 5.Bio\n");
            int choice = input.nextInt();
            Student.Speciality speciality = null;
            switch (choice){
                case 1:
                    speciality= Student.Speciality.SI;
                    break;
                case 2:
                    speciality= Student.Speciality.MAM;
                    break;
                case 3:
                    speciality= Student.Speciality.eau;
                    break;
                case 4:
                    speciality= Student.Speciality.électronique;
                    break;
                case 5:
                    speciality= Student.Speciality.Bio;
                    break;
                default:
                    System.out.println("Not a number from 1 to 5.");
                    System.out.println("**********************");
                    continue;
            }
            System.out.print("Please enter the login: ");
            String username = input.next();
            System.out.print("Please enter the password: ");
            String password = input.next();
            System.out.print("Please enter the password again: ");
            String password2 = input.next();
            if(!password.equals(password2)){
                System.out.println("The two passwords aren't the same.");
                System.out.println("**********************");
                continue;
            }
            System.out.print("Please enter your e-mail: ");
            String mail = input.next();

            Student user = new Student(username,password,mail,speciality);
            request.setCmd(Request.Command.signUp);
            request.setDetail(user);

            try {
                socket = new Socket("localhost",8888);
                sendData(request);//将数据发送到服务器
                response = getData();
                System.out.println(response.getContent());
                if(response.isFlag()==true) {//success in sign-up
                    System.out.println("*****************");
                    break;//not go into the loop to sign-up again
                }
            }catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        signIn();//sign in after sign-up
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

    private static void signIn() {
        System.out.println("Sign-in");
    }

}
