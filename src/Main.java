import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
interface Fixable {
    void fixed();
}

abstract class Vehicle implements Fixable {
    private String name;

    public Vehicle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Motorcycle extends Vehicle {
    public Motorcycle(String name) {
        super(name);
    }

    @Override
    public void fixed() {
        System.out.println(getName() + " – both wheels are fixed");
    }
}

class Car extends Vehicle {
    public Car(String name) {
        super(name);
    }

    @Override
    public void fixed() {
        System.out.println(getName() + " – all four wheels are fixed");
    }
}

class Truck extends Vehicle {
    public Truck(String name) {
        super(name);
    }

    @Override
    public void fixed() {
        System.out.println(getName() + " – all six wheels are fixed");
    }
}

interface ProtocolGarage {
    void fixed(Vehicle vehicle);
}

class Database {
    private List<Vehicle> vehicles = new ArrayList<>();

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}

class Garage {
    private Database database;
    private ProtocolGarage protocol;

    public Garage(Database database, ProtocolGarage protocol) {
        this.database = database;
        this.protocol = protocol;
    }

    public void startRepair() {
        List<Vehicle> vehicles = database.getVehicles();

        for (Vehicle vehicle : vehicles) {
            try {
                System.out.println("Repairing vehicle: " + vehicle.getName());
                Thread.sleep(1000);
                vehicle.fixed();
                protocol.fixed(vehicle);
            } catch (InterruptedException e) {
                System.out.println("Error during repair");
            }
        }

        System.out.println("All vehicles are fixed!");
    }
}

class MainScreen implements ProtocolGarage {
    public static void main(String[] args) {
        Database database = new Database();
        MainScreen mainScreen = new MainScreen();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter 1 for Motorcycle, 2 for Car, 3 for Truck, or 0 to exit:");
            int choice = scanner.nextInt();

            if (choice == 0) break;

            System.out.println("Enter the vehicle name:");
            String name = scanner.next();

            switch (choice) {
                case 1 -> database.addVehicle(new Motorcycle(name));
                case 2 -> database.addVehicle(new Car(name));
                case 3 -> database.addVehicle(new Truck(name));
                default -> System.out.println("Invalid choice");
            }
        }

        Garage garage = new Garage(database, mainScreen);
        garage.startRepair();
    }

    @Override
    public void fixed(Vehicle vehicle) {
        System.out.println("The vehicle " + vehicle.getName() + " has finished its repair.");
    }
}
