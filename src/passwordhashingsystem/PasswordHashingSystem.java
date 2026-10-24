
package passwordhashingsystem;

import java.util.Scanner;
import java.util.HexFormat;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.util.List;

public class PasswordHashingSystem {

    private static final String FILE_NAME = "users.txt";
    private static final Scanner input = new Scanner(System.in);

    // Generate a random salt
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);

        return HexFormat.of().formatHex(saltBytes);
    }

    // Hash password with salt using SHA-256
    public static String hashPassword(String password, String salt)
            throws Exception {

        String combined = password + salt;

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hashBytes = md.digest(
                combined.getBytes(StandardCharsets.UTF_8)
        );

        return HexFormat.of().formatHex(hashBytes);
    }

    // Register a new user
    public static void register() throws Exception {

        System.out.println("\n=== User Registration ===");

        System.out.print("Enter username: ");
        String username = input.nextLine().trim();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        if (username.isEmpty() || password.isEmpty()
                || username.contains(":")
                || username.contains("\n")
                || username.contains("\r")) {
            System.out.println("Invalid username or password!");
            return;
        }

        Path file = Path.of(FILE_NAME);

        // Check if username already exists
        if (Files.exists(file)) {
            List<String> users = Files.readAllLines(file);

            for (String user : users) {
                String[] data = user.split(":", 3);

                if (data.length == 3 && data[0].equals(username)) {
                    System.out.println("Username already exists!");
                    return;
                }
            }
        }

        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        String userData = username + ":" + salt + ":"
                + hashedPassword + System.lineSeparator();

        Files.writeString(
                file,
                userData,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );

        System.out.println("Registration successful!");
        System.out.println("Username: " + username);
        System.out.println("Generated Salt: " + salt);
        System.out.println("Hashed Password: " + hashedPassword);
    }

    // Login an existing user
    public static void login() throws Exception {

        System.out.println("\n=== User Login ===");

        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        Path file = Path.of(FILE_NAME);

        if (!Files.exists(file)) {
            System.out.println("No registered users found!");
            return;
        }

        List<String> users = Files.readAllLines(file);

        for (String user : users) {
            String[] data = user.split(":", 3);

            if (data.length != 3) {
                continue;
            }

            String storedUsername = data[0];
            String storedSalt = data[1];
            String storedHash = data[2];

            if (storedUsername.equals(username)) {

                String enteredHash = hashPassword(
                        password, storedSalt
                );

                if (MessageDigest.isEqual(
                        enteredHash.getBytes(StandardCharsets.UTF_8),
                        storedHash.getBytes(StandardCharsets.UTF_8))) {

                    System.out.println("Login successful!");
                    System.out.println("Welcome, " + username + "!");
                } else {
                    System.out.println("Invalid username or password!");
                }

                return;
            }
        }

        System.out.println("Invalid username or password!");
    }

    // Main menu
    public static void main(String[] args) {

        while (true) {

            System.out.println("\n=== Password Hashing System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Choose an option: ");
            String choice = input.nextLine();

            try {
                switch (choice) {

                    case "1":
                        register();
                        break;

                    case "2":
                        login();
                        break;

                    case "3":
                        System.out.println("Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid option!");
                }

            } catch (Exception e) {
                System.out.println("Operation failed: "
                        + e.getMessage());
            }
        }
    }
}
