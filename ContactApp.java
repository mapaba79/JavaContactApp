import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;


class Contact implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String phoneNumber;
  private String email;
  private String birthDate;
  private String address;

  public Contact(String name, String phoneNumber, String email, String birthDate, String address) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.birthDate = birthDate;
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public String getAddress() {
    return address;
  }

  public boolean isValid() {
    // Implement logic to validate contact attributes here
    // For example, check that the phone number is the correct format, the email is valid, etc.
    return true;  // For now, we return true for simplicity
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
    if (contact.isValid()){
      contacts.add(contact);
      saveContacts();
      System.out.println("Contact added successfully!");
    } else {
      System.out.println("Invalid contact! Pleas check your input.");
    }
  }

  public void listContacts() {
    for (Contact contact : contacts) {
      System.out.println("Name: " + contact.getName() + ", Phone: " + contact.getPhoneNumber() +
                         ", Email: " + contact.getEmail() + ", Birth Date: " + contact.getBirthDate() +
                         ", Address: " + contact.getAddress());
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

  public void editContact(String name, Contact newContact) {
    Contact existingContact = searchContact(name);
    if (existingContact != null) {
      int index = contacts.indexOf(existingContact);
      contacts.set(index, newContact);
      saveContacts();
      System.out.println("Contact edited successfully!");
    } else {
      System.out.println("Contact not found!");
    }
  }

  public void deleteContact(String name) {
    Contact contactToRemove = searchContact(name);
    if (contactToRemove != null) {
      contacts.remove(contactToRemove);
      saveContacts();
      System.out.println("Contact deleted successfully!");
    } else {
      System.out.println("Contact not found!");
    }
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
      // If an exception occurs while loading contacts, 
      // simply ignore it and keep the list empty
    }
  }
}



public class ContactApp {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    ContactManager contactManager = new ContactManager();

    while (true) {
      System.out.println("\n1. Add Contact\n2. List Contacts\n3. Search Contacts\n4. Edit Contact\n5. Delete Contact\n6. Exit");
      System.out.print("Enter your choice: ");
      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline

      switch (choice) {
        case 1:
          System.out.print("Enter name: ");
          String name = scanner.nextLine();

          System.out.print("Enter phone number: ");
          String phoneNumber = scanner.nextLine();

          System.out.print("Enter email: ");
          String email = scanner.nextLine();

          System.out.print("Enter birt date: ");
          String birthDate = scanner.nextLine();

          System.out.print("Enter address: ");
          String address = scanner.nextLine();

          Contact newContact = new Contact(name, phoneNumber, email, birthDate, address);
          contactManager.addContact(newContact);
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
            System.out.println("Contact found - Name: " + searchedContact.getName() +
                               ", Phone: " + searchedContact.getPhoneNumber() +
                               ", Email: " + searchedContact.getEmail() +
                               ", Birth Date: " + searchedContact.getBirthDate() +
                               ", Address: " + searchedContact.getAddress());
          } else {
            System.out.println("Contact not found!");
          }
          break;

        case 4:
          System.out.println("Enter name to edit: ");
          String editName = scanner.nextLine();
          Contact existingContact = contactManager.searchContact(editName);

          if (existingContact != null) {
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();
            
            System.out.print("Enter new phone number: ");
            String newPhoneNumber = scanner.nextLine();
            
            System.out.print("Enter new email: ");
            String newEmail = scanner.nextLine();
            
            System.out.print("Enter new birth date: ");
            String newBirthDate = scanner.nextLine();
            
            System.out.print("Enter new address: ");
            String newAddress = scanner.nextLine();

            Contact editedContact = new Contact(newName, newPhoneNumber, newEmail, newBirthDate, newAddress);
            contactManager.editContact(editName, editedContact);
          } else {
            System.out.println("Contact not found!");
          }
          break;

        case 5:
          System.out.print("Enter name to delete: ");
          String deleteName = scanner.nextLine();
          contactManager.deleteContact(deleteName);
          break;

        case 6:
          System.out.println("Exiting...");
          System.exit(0);

        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }
}
