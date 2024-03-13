import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Contact implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String phoneNumber;

  public Contact(String name, String phoneNumber) {
    this.name = name;
    this.phoneNumber = phoneNumber;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}

class ContactManager {
  private ArrayList<Contact> contacts;
  private static final String FILENAME = "contacts.dat";

  public ContactManager() {
    contacts = new ArrayList<>();
    loadContacts();
  }

  public void addContact(Contact contact) {
    contacts.add(contact);
    saveContacts();
  }

  public void listContacts() {
    for (Contact contact : contacts) {
      System.out.println("Name: " + contact.getName() + ", Phone: " + contact.getPhoneNumber());
    }
  }

  public Contact searchContact(String name) {
    for (Contact contact : contacts) {
      if (contact.getName().equalsIgnoreCase(name)) {
        return contact;
      }
    }
    return null;
  }

  private void saveContacts() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
      oos.writeObject(contacts);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadContacts() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
      contacts = (ArrayList<Contact>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      // Se ocorrer uma exceção a carregar os contatos, simplesmente ignora e mantém a lista vazia
    }
  }
}

public class ContactApp {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    ContactManager contactManager = new ContactManager();

    while (true) {
      System.out.println("\n1. Add Contact\n2. List Contacts\n3. Search Contacts\n4. Exit");
      System.out.print("Enter your choice: ");
      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline

      switch (choice) {
        case 1:
          System.out.print("Enter name: ");
          String name = scanner.nextLine();
          System.out.print("Enter phone number: ");
          String phoneNumber = scanner.nextLine();
          Contact contact = new Contact(name, phoneNumber);
          contactManager.addContact(contact);
          System.out.println("Contact added successfully!");
          break;

        case 2:
          System.out.println("Contacts:");
          contactManager.listContacts();
          break;

        case 3:
          System.out.print("Enter name to search: ");
          String searchName = scanner.nextLine();
          Contact searchedContact = contactManager.searchContact(searchName);
          if (searchedContact != null) {
            System.out.println("Contact found - Name: " + searchedContact.getName() + ", Phone: " + searchedContact.getPhoneNumber());
          } else {
            System.out.println("Contact not found!");
          }
          break;

        case 4:
          System.out.println("Exiting...");
          System.exit(0);

        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }
}
