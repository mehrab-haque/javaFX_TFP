package client.admin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Constants;
import util.NetworkUtil;

import java.io.IOException;
import java.util.Scanner;

public class Admin {

    private NetworkUtil networkUtil;

    private Admin() throws IOException {
        networkUtil = new NetworkUtil(Constants.SERVER_ADDRESS, Constants.SERVER_PORT);
    }

    private void mainMenu(){
        System.out.println("Enter a menu : ");
        System.out.println("(1) List of manufacturers.");
        System.out.println("(2) Add a manufacturer.");
        System.out.println("(3) Delete a manufacturer.");
        Scanner scanner=new Scanner(System.in);
        try{
            int input=scanner.nextInt();
            switch (input){
                case 1:
                    listManufacturers();
                    break;
                case 2:
                    addManufacturer();
                    break;
                case 3:
                    deleteManufacturer();
                    break;
                default:
                    System.out.println("Invalid input");
                    mainMenu();
            }
        }catch(Exception e){
            System.out.println("Invalid input");
            mainMenu();
        }
    }

    private void listManufacturers() throws JSONException, IOException, ClassNotFoundException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("type",Constants.TYPE_MANUFACTURER_LIST_REQUEST);
        networkUtil.write(jsonObject.toString());
        JSONArray manufacturerArray=new JSONObject((String)networkUtil.read()).getJSONArray("manufacturers");
        System.out.println(manufacturerArray.length()+" Manufacturers+\n");
        for(int i=0;i<manufacturerArray.length();i++){
            JSONObject manufacturer=manufacturerArray.getJSONObject(i);
            System.out.println("id => "+manufacturer.getInt("id"));
            System.out.println("displayName => "+manufacturer.getString("displayName"));
            System.out.println("userName => "+manufacturer.getString("userName"));
            System.out.println("password => "+manufacturer.getString("password")+"\n");
        }
        System.out.println("Press enter to return to main menu");
        Scanner scanner=new Scanner(System.in);
        scanner.nextLine();
        mainMenu();
    }

    private void addManufacturer() throws JSONException, IOException, ClassNotFoundException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter manufacturer display name : ");
        String displayName=scanner.nextLine().trim();
        System.out.println("Enter manufacturer user name : ");
        String userName=scanner.nextLine().trim();
        System.out.println("Enter manufacturer password : ");
        String password=scanner.nextLine().trim();

        JSONObject request=new JSONObject();
        request.put("type",Constants.TYPE_MANUFACTURER_ADD_REQUEST);
        request.put("displayName",displayName);
        request.put("userName",userName);
        request.put("password",password);
        networkUtil.write(request.toString());

        JSONObject response=new JSONObject((String)networkUtil.read());
        if(response.getBoolean("status")){
            System.out.println("manufacturer added successfully");
            System.out.println("Press enter to return to main menu");
            scanner.nextLine();
            mainMenu();
        }else{
            System.out.println("error occurred");
            System.out.println("Press enter to return to main menu");
            scanner.nextLine();
            mainMenu();
        }
    }

    private void deleteManufacturer(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter manufacturer id : ");
        try{
            int id=scanner.nextInt();
            JSONObject request=new JSONObject();
            request.put("type",Constants.TYPE_MANUFACTURER_DELETE_RREQUEST);
            request.put("id",id);
            networkUtil.write(request.toString());

            JSONObject response=new JSONObject((String)networkUtil.read());
            if(response.getBoolean("status")){
                System.out.println("manufacturer deleted successfully");
                System.out.println("Press enter to return to main menu");
                scanner.nextLine();
                mainMenu();
            }else{
                System.out.println("manufacturer not found");
                mainMenu();
            }
        }catch(Exception e){
            System.out.println("invalid input");
            mainMenu();
        }
    }

    public static void main(String[] args) throws IOException {
        Admin admin= new Admin();
        admin.mainMenu();
    }
}
