import java.io.*;
import java.util.*;

class Room {
    int no; String type; boolean available = true;
    Room(int no, String type) { this.no = no; this.type = type; }
}

class Booking implements Serializable {
    String name; int roomNo;
    Booking(String name, int roomNo) { this.name = name; this.roomNo = roomNo; }
}

public class HotelReservationSystem {
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static final String FILE = "bookings.dat";

    static void save() throws Exception {
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(FILE));
        o.writeObject(bookings); o.close();
    }

    static void load() {
        try {
            ObjectInputStream i = new ObjectInputStream(new FileInputStream(FILE));
            bookings = (ArrayList<Booking>) i.readObject();
            i.close();
            for (Booking b : bookings)
                for (Room r : rooms)
                    if (r.no == b.roomNo) r.available = false;
        } catch (Exception ignored) {}
    }

    public static void main(String[] args) throws Exception {
        rooms.add(new Room(101,"Standard"));
        rooms.add(new Room(102,"Deluxe"));
        rooms.add(new Room(103,"Suite"));
        load();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1.View 2.Book 3.Cancel 4.Bookings 5.Exit");
            int ch = sc.nextInt(); sc.nextLine();

            if (ch == 1)
                for (Room r : rooms)
                    System.out.println(r.no+" "+r.type+" "+(r.available?"Available":"Booked"));

            else if (ch == 2) {
                System.out.print("Name: "); String n = sc.nextLine();
                System.out.print("Room No: "); int rn = sc.nextInt();
                for (Room r : rooms)
                    if (r.no == rn && r.available) {
                        System.out.println("Payment Successful!");
                        r.available = false;
                        bookings.add(new Booking(n, rn));
                        save();
                        System.out.println("Booked!");
                    }
            }

            else if (ch == 3) {
                System.out.print("Room No: "); int rn = sc.nextInt();
                bookings.removeIf(b -> b.roomNo == rn);
                for (Room r : rooms) if (r.no == rn) r.available = true;
                save();
                System.out.println("Reservation Cancelled!");
            }

            else if (ch == 4)
                for (Booking b : bookings)
                    System.out.println("Name: "+b.name+", Room: "+b.roomNo);

            else break;
        }
        sc.close();
    }
}
