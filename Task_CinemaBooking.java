import java.util.*;

class Task_CinemaBooking {

    static class Seat {
        final char row;
        final int number;

        Seat(char row, int number) {
            this.row = row;
            this.number = number;
        }

        @Override
        public String toString() {
            return "" + row + number;
        }

        // TODO 1: equals()
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof Seat)) return false;

            Seat seat = (Seat) o;

            return row == seat.row && number == seat.number;
        }

        // TODO 2: hashCode()
        @Override
        public int hashCode() {
            return Objects.hash(row, number);
        }
    }

    public static void main(String[] args) {

        // Key = Seat, Value = Customer name
        HashMap<Seat, String> bookings = new HashMap<>();
        HashSet<Seat> broken = new HashSet<>();

        // Part A: Book seats
        bookings.put(new Seat('A', 1), "Ali");
        bookings.put(new Seat('A', 2), "Sara");
        bookings.put(new Seat('B', 5), "Omar");
        bookings.put(new Seat('C', 3), "Mona");

        System.out.println("Bookings: " + bookings);

        // Part B: Get customer who booked C3
        System.out.println("Who booked C3: " +
                bookings.get(new Seat('C', 3)));

        // Part C: Check if seats are taken
        System.out.println("Is A2 taken: " +
                bookings.containsKey(new Seat('A', 2)));

        System.out.println("Is E6 taken: " +
                bookings.containsKey(new Seat('E', 6)));

        // Part D: Youssef wants B5
        String previous = bookings.putIfAbsent(
                new Seat('B', 5), "Youssef");

        if (previous == null) {
            System.out.println("Youssef -> B5: booked");
        } else {
            System.out.println("Youssef -> B5: refused, taken by "
                    + previous);
        }

        // Part E: Ali moves from A1 to D2
        bookings.remove(new Seat('A', 1));
        bookings.put(new Seat('D', 2), "Ali");

        System.out.println("After Ali moved: " + bookings);

        // Part F: Broken seats
        broken.add(new Seat('E', 1));
        broken.add(new Seat('E', 2));

        if (broken.contains(new Seat('E', 1))) {
            System.out.println("Hana -> E1: refused, seat is broken");
        } else {
            bookings.put(new Seat('E', 1), "Hana");
            System.out.println("Hana -> E1: booked");
        }

        // Part G: Number of booked seats
        System.out.println("Seats booked: " + bookings.size());

        // Seating chart
        System.out.println();
        printChart(bookings, broken);
    }

    // GIVEN - Do not change
    static void printChart(HashMap<Seat, String> bookings,
                           HashSet<Seat> broken) {

        System.out.println("     1  2  3  4  5  6");

        for (char row = 'A'; row <= 'E'; row++) {

            System.out.print(row + "  ");

            for (int number = 1; number <= 6; number++) {

                Seat seat = new Seat(row, number);

                if (bookings.containsKey(seat)) {
                    System.out.print("  X");
                } else if (broken.contains(seat)) {
                    System.out.print("  #");
                } else {
                    System.out.print("  .");
                }
            }

            System.out.println();
        }
    }
}

/*
QUESTIONS

Q1. Why did we have to override hashCode() and equals()?

Because HashMap uses hashCode() to find the bucket
and equals() to compare keys.
They allow different Seat objects representing
the same seat to be treated as equal.

Q2. Delete hashCode() but keep equals(). What happens?

HashMap may fail to find equal Seat objects because
the default hashCode() is based on object identity.
Equal objects must have the same hashCode().

Q3. Why are row and number final?

Because changing a key after inserting it into HashMap
can cause lookup problems. final prevents changing
the seat information.

Q4. Change equals(Object o) to equals(Seat o).
What happens?

With @Override, compilation fails because this method
does not override Object.equals(Object).

If @Override is removed, it becomes an overloaded method.
HashMap still uses Object.equals(), so equal seats
are not recognized correctly.

BONUS

Replace Seat class with:

record Seat(char row, int number) {}

A record automatically provides equals(), hashCode()
and toString() based on its components.
*/