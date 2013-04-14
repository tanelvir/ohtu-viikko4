package olutopas;

import com.avaje.ebean.EbeanServer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.OptimisticLockException;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.User;
import olutopas.model.Rating;

public class Application {

    private EbeanServer server;
    private Scanner scanner = new Scanner(System.in);

    public Application(EbeanServer server) {
        this.server = server;
    }

    public void run(boolean newDatabase) {
        if (newDatabase) {
            seedDatabase();
        }
        boolean access = false;
        while (!access) {
            System.out.println("Login (give ? to register a new user)");
            System.out.println("username:");
            String task = scanner.nextLine();
            if (task.equals("?")) {
                access = createNewUser();
            } else {
                access = checkUser(task);
            }
        }

        while (true) {
            menu();
            System.out.print("> ");
            String command = scanner.nextLine();

            if (command.equals("0")) {
                break;
            } else if (command.equals("1")) {
                findBrewery();
            } else if (command.equals("2")) {
                findBeer();
            } else if (command.equals("3")) {
                addBeer();
            } else if (command.equals("4")) {
                listBreweries();
            } else if (command.equals("5")) {
                deleteBeer();
            } else if (command.equals("6")) {
                listBeers();
            } else if (command.equals("7")) {
                addBrewery();
            } else if (command.equals("8")) {
                deleteBrewery();
            } else {
                System.out.println("unknown command");
            }

            System.out.print("\npress enter to continue");
            scanner.nextLine();
        }

        System.out.println("bye");
    }

    private void menu() {
        System.out.println("");
        System.out.println("1   find brewery");
        System.out.println("2   find beer");
        System.out.println("3   add beer");
        System.out.println("4   list breweries");
        System.out.println("5   delete beer");
        System.out.println("6   list beers");
        System.out.println("7   add brewery");
        System.out.println("8   delete brewery");
        System.out.println("0   quit");
        System.out.println("");
    }

    // jos kanta on luotu uudelleen, suoritetaan tämä ja laitetaan kantaan hiukan dataa
    private void seedDatabase() throws OptimisticLockException {
        Brewery brewery = new Brewery("Schlenkerla");
        brewery.addBeer(new Beer("Urbock"));
        brewery.addBeer(new Beer("Lager"));
        // tallettaa myös luodut oluet, sillä Brewery:n OneToMany-mappingiin on määritelty
        // CascadeType.all
        server.save(brewery);

        // luodaan olut ilman panimon asettamista
        Beer b = new Beer("Märzen");
        server.save(b);

        // jotta saamme panimon asetettua, tulee olot lukea uudelleen kannasta
        b = server.find(Beer.class, b.getId());
        brewery = server.find(Brewery.class, brewery.getId());
        brewery.addBeer(b);
        server.save(brewery);

        server.save(new Brewery("Paulaner"));
    }

    private boolean createNewUser() {
        System.out.println("Register a new user");
        System.out.println("give username: ");
        String name = scanner.nextLine();
        User user = new User(name);
        User test = server.find(User.class).where().like("name", name).findUnique();
        if (test == null) {
            server.save(user);
            System.out.println("user created!");
            return true;
        } else {
            System.out.println("user already exists!");
            return false;
        }
    }

    private boolean checkUser(String name) {
        User test = server.find(User.class).where().like("name", name).findUnique();
        if (test == null) {
            System.out.println("user not exists!");
            return false;
        } else {
            System.out.println("Welcome to RateBeer " + name);
            return true;
        }
    }

    private Beer findBeer() {
        System.out.print("beer to find: ");
        String n = scanner.nextLine();
        Beer foundBeer = server.find(Beer.class).where().like("name", n).findUnique();

        if (foundBeer == null) {
            System.out.println(n + " not found");
            return null;
        }

        System.out.println("found: " + foundBeer);
        return foundBeer;
    }

    private void findBrewery() {
        System.out.print("brewery to find: ");
        String n = scanner.nextLine();
        Brewery foundBrewery = server.find(Brewery.class).where().like("name", n).findUnique();

        if (foundBrewery == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundBrewery);
        for (Beer bier : foundBrewery.getBeers()) {
            System.out.println("   " + bier.getName());
        }
    }

    private void listBreweries() {
        List<Brewery> breweries = server.find(Brewery.class).findList();
        for (Brewery brewery : breweries) {
            System.out.println(brewery);
        }
    }

    private void listBeers() {
        List<Brewery> breweries = server.find(Brewery.class).findList();
        for (Brewery brewery : breweries) {
            for (Beer beer : brewery.getBeers()) {
                System.out.println(beer);
            }
        }
    }

    private void getBeerRatings(Beer beer) {
        List<Rating> ratings = server.find(Rating.class).where().findList();
        ArrayList<Integer> averagelist = new ArrayList<Integer>();
        int sum = 0;
        for (Rating rating : ratings) {
            if (rating.getBeer().getName().equals(beer.getName())) {
                averagelist.add(rating.getValue());
                sum += rating.getValue();
            }
        }
        System.out.println("number of ratings: " + averagelist.size() + " average " + sum/averagelist.size());
    }

    private void addBrewery() {
        System.out.print("name of brewery: ");
        String name = scanner.nextLine();
        Brewery brewery;
        if (name.isEmpty()) {
            brewery = new Brewery();
        } else {
            brewery = new Brewery(name);
        }
        server.save(brewery);
    }

    private void deleteBrewery() {
        System.out.print("Which brewery? ");
        listBreweries();
        String name = scanner.nextLine();
        Brewery brewery = server.find(Brewery.class).where().like("name", name).findUnique();

        if (brewery == null) {
            System.out.println(name + " does not exist");
            return;
        }

        server.delete(brewery);
        System.out.println(name + " deleted");
    }

    private void addBeer() {
        System.out.print("to which brewery: ");
        String name = scanner.nextLine();
        Brewery brewery = server.find(Brewery.class).where().like("name", name).findUnique();

        if (brewery == null) {
            System.out.println(name + " does not exist");
            return;
        }

        System.out.print("beer to add: ");

        name = scanner.nextLine();

        Beer exists = server.find(Beer.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        brewery.addBeer(new Beer(name));
        server.save(brewery);
        System.out.println(name + " added to " + brewery.getName());
    }

    private void deleteBeer() {
        System.out.print("beer to delete: ");
        String n = scanner.nextLine();
        Beer beerToDelete = server.find(Beer.class).where().like("name", n).findUnique();

        if (beerToDelete == null) {
            System.out.println(n + " not found");
            return;
        }

        server.delete(beerToDelete);
        System.out.println("deleted: " + beerToDelete);

    }
}
