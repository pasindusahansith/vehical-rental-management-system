
import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Vehicle{  //define Class for vehicle to declare its attributes
    String vehicle_id;
    String vehicle_brand;
    String vehicle_model;
    String vehicle_type;
    boolean available;
    double chargePerDay;
    String customerId;
    LocalDate rentDate;
    LocalDate returnDate;

    public Vehicle(String vehicle_id,String vehicle_brand,String vehicle_model,String vehicle_type,double chargePerDay){  //Parameterized constructor for pass the values
        this.vehicle_id=vehicle_id;
        this.vehicle_brand=vehicle_brand;
        this.vehicle_model=vehicle_model;
        this.vehicle_type=vehicle_type;
        this.chargePerDay=chargePerDay;
        this.available=true;
    }

}

class Customer{ //define Class for Customer to declare its attributes
    String customerId;
    String cus_name;
    String cus_email;
    String telephone;

    public Customer(String cus_id, String cus_name,String cus_email,String cus_tp){ //Parameterized constructor for pass the values
        this.customerId=cus_id;
        this.cus_name=cus_name;
        this.cus_email=cus_email;
        this.telephone=cus_tp;
    }
}

class CustomerNode{ //Define class to contain Customer attributes into a node
    Customer cus;
    CustomerNode next;

    public CustomerNode(Customer cus){ //Paramiterized constructor to pass Customer Object
        this.cus=cus;
        this.next=null;
    }

}
class VehicleNode{     //Define class to contain Vehicle attributes into a node
    Vehicle vehicle;
    VehicleNode prev;
    VehicleNode next;

    public VehicleNode(Vehicle vehicle) { //Paramiterized constructor to pass Vehicle Object
        this.vehicle=vehicle;
        this.prev=null;
        this.next=null;
    }

}
class RentalManagement {
    VehicleNode head = null;
    VehicleNode back = null;
    CustomerNode cus_head = null;
    int totalVehicles = 0;
    int totalCusomers = 0;
    Scanner sc = new Scanner(System.in);

    public RentalManagement() {
        menu();
    }

    public boolean isVehicleExisist(String vehicleID) { // Method to check whether the vehicle id is exist or not
        VehicleNode current = head;
        while (current != null) {
            if (current.vehicle.vehicle_id.equals(vehicleID)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean isCustomerExsist(String customerID) {
        CustomerNode current = cus_head;
        while (current != null) {
            if (current.cus.customerId.equals(customerID)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void addVehicle(String vehicle_id, String brand, String model, String type, double chargePerDay) {
        Vehicle newVehicle = new Vehicle(vehicle_id, brand, model, type, chargePerDay);
        VehicleNode newVehicleNode = new VehicleNode(newVehicle);



        if (head == null) {
            head = newVehicleNode;
            back = newVehicleNode;
        } else {
            back.next = newVehicleNode;
            newVehicleNode.prev = back;
            back = newVehicleNode;
        }
        totalVehicles++;
    }

    public void deleteVehicle(String vehicleID) {
        VehicleNode current = head;


        while (current != null) {
            if (current.vehicle.vehicle_id.equals(vehicleID)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    back = current.prev;
                }
                totalVehicles--;
                return;
            }
            current = current.next;
        }
    }

    public void editVehicle(String vehicleId, String brand, String model, String type, double chargePerDay) {
        VehicleNode current = head;



        while (current != null) {
            if (current.vehicle.vehicle_id.equals(vehicleId)) {
                current.vehicle.vehicle_brand = brand;
                current.vehicle.vehicle_model = model;
                current.vehicle.vehicle_type = type;
                current.vehicle.chargePerDay = chargePerDay;
                System.out.println("Vehicle Edited Successfully!");
                System.out.println("------------------------------");
                break;
            }
            current = current.next;
        }
    }

    public void add_customer(String cusID, String cusName, String email, String phone) {


        Customer newCus = new Customer(cusID, cusName, email, phone);
        CustomerNode cusNode = new CustomerNode(newCus);
        if (cus_head == null) {
            cus_head = cusNode;
        } else {
            CustomerNode current = cus_head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = cusNode;
        }
        totalCusomers++;
    }

    public void rent_to_customer(String customerId, String vehicleId, String bookingId, LocalDate bookingDate){
        VehicleNode current_vehicle=head;
        boolean vehicle_found=false;
        while(current_vehicle != null){
            if(current_vehicle.vehicle.vehicle_id.equals(vehicleId)){
                vehicle_found=true;
                if(!current_vehicle.vehicle.available){
                    System.err.println("This vehicle is already Rented");
                    return;
                }
                current_vehicle.vehicle.available=false;
                current_vehicle.vehicle.rentDate=bookingDate;
                break;
            }
            current_vehicle=current_vehicle.next;
        }



        current_vehicle.vehicle.customerId=customerId;

        System.out.println("Vehicle " + vehicleId + " rented to customer " + customerId + " with booking ID  "+ bookingId);
    }
    public void show_available_vehicle(){
        System.out.println("Available vehicles: \n");
        VehicleNode current = head;
        boolean availables = false;
        while(current != null){
            if(current.vehicle.available){
                availables = true;
                System.out.println("Vehicle ID: " + current.vehicle.vehicle_id);
                System.out.println("Model: "+current.vehicle.vehicle_brand+" "+ current.vehicle.vehicle_model);
                System.out.println("Type: " + current.vehicle.vehicle_type);
                System.out.println("---------------------");
            }
            current = current.next;
        }if(!availables){
            System.err.println("All cars are rented.");
        }
    }
    public String get_cusName(String customerId){
        CustomerNode current = cus_head;
        while(current != null){
            if(current.cus.customerId.equals(customerId)){
                return current.cus.cus_name;
            }
            current = current.next;
        }
        return "No Name Found";
    }

    public String get_cusPhone(String customerId){
        CustomerNode current = cus_head;
        while(current != null){
            if(current.cus.customerId.equals(customerId)){
                return current.cus.telephone;
            }
            current = current.next;
        }
        return "No Phone number Found";
    }
    public void show_rented_vehicle(){
        System.out.println("Rented vehicles: \n");
        VehicleNode current = head;
        while(current != null){
            if (!current.vehicle.available) {
                System.out.println("Vehicle ID: " + current.vehicle.vehicle_id);
                System.out.println("Model: " + current.vehicle.vehicle_model);
                System.out.println("Type: " + current.vehicle.vehicle_type);
                System.out.println("Customer Name: " + get_cusName(current.vehicle.customerId));
                System.out.println("Customer Phone: " + get_cusPhone(current.vehicle.customerId));
                System.out.println("Booking Date: " + current.vehicle.rentDate);
                System.out.println("---------------------");
            }
            current = current.next;
        }
    }
    public void return_vehicle(String vehi_id){

        VehicleNode current = head;
        while(current != null){
            if(current.vehicle.vehicle_id.equals(vehi_id)){
                if(current.vehicle.available){
                    System.err.println("vehicle id" +vehi_id+" is already exist.");
                    return;
                }
                System.out.print("Enter Return date (YYYY-MM-DD): ");
                LocalDate return_date = LocalDate.parse(sc.next());
                long days = ChronoUnit.DAYS.between(current.vehicle.rentDate, return_date);
                System.out.println("Days after rent: "+days);
                double price = days*current.vehicle.chargePerDay;
                System.out.println("Whole price for the rent : " + price);
                current.vehicle.available = true;
                current.vehicle.customerId = null;
                current.vehicle.rentDate = null;
                current.vehicle.returnDate = null;
                System.out.println("Vehicle "+vehi_id+" returned successfully.");
                return;
            }
            current = current.next;
        }
        System.out.println("Vehicle "+vehi_id+" not returned yet." );
    }
    public void show_customers(){
        System.out.println("All Customers:\n");
        CustomerNode current = cus_head;
        while(current != null){
            System.out.println("Customer ID: " + current.cus.customerId);
            System.out.println("Name: " + current.cus.cus_name);
            System.out.println("Email: " + current.cus.cus_email);
            System.out.println("Phone: " + current.cus.telephone);
            System.out.println("---------------------");
            current = current.next;
        }
        if (totalCusomers == 0) {
            System.err.println("No customers available.");
        }
    }
    public void getChoices(){
        System.out.println("\n---------------------");
        System.out.println("Vehicle Rental Management System");
        System.out.println("1. Add Vehicle");
        System.out.println("2. Delete Vehicle");
        System.out.println("3. Edit Vehicle");
        System.out.println("4. Add Customer");
        System.out.println("5. Rent Vehicle to Customer");
        System.out.println("6. Show Available Vehicles");
        System.out.println("7. Show Rented Vehicles");
        System.out.println("8. Show Customers");
        System.out.println("9. Enter Return Vehicle");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    public void menu(){
        int choice;
        do{
            getChoices();
            if (sc.hasNextInt()){
            choice=sc.nextInt();
            System.out.println("---------------------");

            switch(choice){
                case 1:
                    try {
                        System.out.println("Enter Vehicle ID : ");
                        String vehicleId = sc.next();

                        if (isVehicleExisist(vehicleId)) {
                            System.err.println("This Vehicle is Already Exist");
                            break;
                        }

                        System.out.println("Enter Vehicle Brand  ");
                        String brand = sc.next();
                        System.out.println("Enter Vehicle Model : ");
                        String model = sc.next();
                        System.out.println("Enter Vehicle Type : ");
                        String type = sc.next();
                        System.out.println("Enter charge per day : ");
                        double charge = sc.nextDouble();
                        addVehicle(vehicleId, brand, model, type, charge);
                        break;
                    }catch(Exception e){
                        System.err.println(e);
                        break;
                    }
                case 2:
                    try {
                        System.out.println("Enter Vehicle ID : ");
                        String vehicleId = sc.next();
                        if (!isVehicleExisist(vehicleId)) {
                            System.err.println("Vehicle does not exisist");
                            break;
                        }
                        deleteVehicle(vehicleId);
                        break;
                    }catch(Exception e){
                        System.err.println(e);
                        break;
                    }

                case 3:
                    try {
                        System.out.println("Enter Vehicle ID : ");
                        String newVehicleId = sc.next();

                        if (!isVehicleExisist(newVehicleId)) {
                            System.err.println("Vehicle does not exist");
                            break;
                        }

                        System.out.println("Enter Vehicle Brand  ");
                        String newBrand = sc.next();
                        System.out.println("Enter Vehicle Model : ");
                        String newModel = sc.next();
                        System.out.println("Enter Vehicle Type : ");
                        String newType = sc.next();
                        System.out.println("Enter charge per day : ");
                        double newCharge = sc.nextDouble();
                        editVehicle(newVehicleId, newBrand, newModel, newType, newCharge);
                        break;
                    }catch(Exception e){
                        System.err.println(e);
                        break;
                    }
                case 4:

                    System.out.println("Enter Customer ID  : ");
                    String cusID=sc.next();

                    if (isCustomerExsist(cusID)) {
                        System.err.println("This ID : " + cusID + " already exist.");
                        break;
                    }
                    System.out.println("Enter Customer Name  : ");
                    String cusName=sc.next();
                    System.out.println("Enter Customer Email  : ");
                    String email=sc.next();
                    System.out.println("Enter Customer Contact Number  : ");
                    String phone=sc.next();
                    add_customer(cusID, cusName, email, phone);
                    break;

                case 5:
                    try {
                        System.out.println("Enter Customer ID : ");
                        String rent_cusID = sc.next();

                        if(!isCustomerExsist(rent_cusID)){
                            System.err.println("Customer not found!!!");
                            break;
                        }

                        System.out.println("Enter Vehicle ID : ");
                        String rent_vehicleId = sc.next();
                        System.out.println("Enter Booking ID : ");
                        String bookingId = sc.next();
                        System.out.println("Enter Booking date (YYYY-MM-DD): ");
                        LocalDate bookingDate = LocalDate.parse(sc.next());
                        rent_to_customer(rent_cusID, rent_vehicleId, bookingId, bookingDate);
                        break;
                    }catch(Exception e){
                        System.err.println(e);
                        break;
                    }
                case 6:
                    show_available_vehicle();
                    break;
                case 7:
                    show_rented_vehicle();
                    break;
                case 8:
                    show_customers();
                    break;
                case 9:
                    System.out.println("Enter Vehicle ID : ");
                    String vehicleId=sc.next();
                    return_vehicle(vehicleId);
                    break;
                case 0:
                    System.out.println("Exiting...........");
                    break;
                default:
                    System.err.println("Invalid Choice!!!");
                    break;
            }
        }else{
            System.err.println("Invalid input!");
            sc.next();
            choice = -1;
          }

        }while(choice!=0);

    }
}

public class Main {
    public static void main(String[] args) {
        RentalManagement rental=new RentalManagement();

    }
}