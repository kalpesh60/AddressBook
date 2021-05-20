import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBookMain {

    Scanner sc = new Scanner(System.in);
    private static List<Contact> addressList = new ArrayList<Contact>();
    HashMap<String, List<Contact>> addressBookMap = new HashMap<String, List<Contact>>();
    HashMap<Contact, String> personCityMap = new HashMap<Contact, String>();
    HashMap<Contact, String> personStateMap = new HashMap<Contact, String>();

    public boolean addContact(Contact contact) {
        addressList.add(contact);
        System.out.println("Contact Added.\n" + contact);
        return true;
    }

    public boolean editDetails(String firstName) {
        Contact edit;
        boolean contactFound = false;
        for (Contact contact : addressList) {
            edit = contact;
            if ((edit.getFirstName().equals(firstName))) {
                System.out.println("Enter new Address:");
                edit.setAddress(sc.nextLine());
                System.out.println("Enter new City");
                edit.setCity(sc.nextLine());
                System.out.println("Enter new State");
                edit.setState(sc.nextLine());
                System.out.println("Enter new Zip");
                edit.setZip(sc.nextLine());
                System.out.println("Enter new Phone no");
                edit.setPhoneNo(sc.nextLine());
                System.out.println("Enter new Email");
                edit.setEmail(sc.nextLine());
                contactFound = true;
                break;
            }
        }
        return contactFound;
    }

    public boolean removeDetails(String firstName) {
        Contact remove;
        boolean contactFound = false;
        for (int i = 0; i < addressList.size(); i++) {
            remove = addressList.get(i);
            if ((remove.getFirstName().equals(firstName))) {
                addressList.remove(i);
                contactFound = true;
                break;
            }
        }
        return contactFound;
    }

    public void addAddressBook(String listName) {
        List<Contact> newAddressList = new LinkedList<>();
        addressBookMap.put(listName, newAddressList);
        System.out.println("Address Book added");
    }

    public static boolean checkForDuplicate(String first) {
        if (addressList.stream().anyMatch(obj -> obj.getFirstName().equals(first))) {
            System.out.println("This contact already exists, try again!!");
            return true;
        } else
            return false;
    }

    private void searchPersonAcrossCityState(int searchChoice, String cityOrState) {
        for (Map.Entry<String, List<Contact>> entry : addressBookMap.entrySet()) {
            List<Contact> list = entry.getValue();
            if (searchChoice == 1)
                list.stream().filter(obj -> ((obj.getCity().equals(cityOrState)))).forEach(System.out::println);
            else if (searchChoice == 2)
                list.stream().filter(obj -> ((obj.getState().equals(cityOrState)))).forEach(System.out::println);
        }
    }

    private void addToDictionary(boolean contactIsAdded, Contact contactObj) {
        if (contactIsAdded) {
            personCityMap.put(contactObj, contactObj.getCity());
            personStateMap.put(contactObj, contactObj.getState());
        }
    }

    private void viewPersonsByCityState(String cityOrState, int searchChoice) {
        for (Map.Entry<String, List<Contact>> entry : addressBookMap.entrySet()) {
            List<Contact> list = entry.getValue();
            if (searchChoice == 1)
                list.stream().filter(obj -> obj.getCity().equals(cityOrState)).forEach(System.out::println);
            else if (searchChoice == 2)
                list.stream().filter(obj -> obj.getState().equals(cityOrState)).forEach(System.out::println);
        }
    }

    private long getCountByCityState(String cityOrState, int searchChoice) {
        long count = 0;
        for (Map.Entry<String, List<Contact>> entry : addressBookMap.entrySet()) {
            List<Contact> list = entry.getValue();
            if (searchChoice == 1)
                count += list.stream().filter(obj -> obj.getCity().equals(cityOrState)).count();
            else if (searchChoice == 2)
                count += list.stream().filter(obj -> obj.getState().equals(cityOrState)).count();
        }
        return count;
    }

    private List<Contact> sortAddressBookByName(List<Contact> sortList) {
        sortList.sort(new Contact());
        return sortList;
    }

    private void sortByCity() {
        for (Map.Entry<String, List<Contact>>entry:addressBookMap.entrySet()) {
            List<Contact> list = entry.getValue();
            List<Contact> sortCity = list.stream().sorted(Comparator.comparing(Contact::getCity)).collect(Collectors.toList());

            for(Contact contact:sortCity) {
                System.out.println("City: "+contact.getCity());
                System.out.println("First Name: "+contact.getFirstName());
                System.out.println("Last Name: "+contact.getLastName());
            }
        }
    }

    private void sortByState() {
        for (Map.Entry<String, List<Contact>>entry:addressBookMap.entrySet()) {
            List<Contact> list = entry.getValue();
            List<Contact> sortState = list.stream().sorted(Comparator.comparing(Contact::getState)).collect(Collectors.toList());

            for(Contact contact:sortState) {
                System.out.println("State: "+contact.getState());
                System.out.println("First Name: "+contact.getFirstName());
                System.out.println("Last Name: "+contact.getLastName());
            }
        }
    }

    private void sortByZip() {
        for (Map.Entry<String, List<Contact>>entry:addressBookMap.entrySet()) {
            List<Contact> list = entry.getValue();
            List<Contact> sortZip = list.stream().sorted(Comparator.comparing(Contact::getZip)).collect(Collectors.toList());

            for(Contact contact:sortZip) {
                System.out.println("Zip code: "+contact.getZip());
                System.out.println("First Name: "+contact.getFirstName());
                System.out.println("Last Name: "+contact.getLastName());
            }
        }
    }

    public static void writeData(AddressBookMain addressObj) {
        StringBuffer personBuffer = new StringBuffer();
        addressList.forEach(person -> {
            String personDataString = person.toString().concat("\n");
            personBuffer.append(personDataString);
        });
        try {
            Files.write(Paths.get("AddressBook.txt"), personBuffer.toString().getBytes(StandardCharsets.UTF_8) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readData(AddressBookMain addressObj) {
        try{
            Files.lines(new File("AddressBook.txt").toPath()).map(String::trim).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        AddressBookMain.menu();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        AddressBookMain addressObj = new AddressBookMain();
        int choice = 0;

        while (choice != 10) {
            if (addressObj.addressBookMap.isEmpty()) {
                System.out.println("Welcome to the AddressBook Program-");
                System.out.println("Enter the name of address book to add:");
                String listName = sc.nextLine();
                addressObj.addAddressBook(listName);
            }

            System.out.println("Enter the name of the address book you want to access");
            String listName = sc.nextLine();
            if (addressObj.addressBookMap.containsKey(listName)) {
                addressList = addressObj.addressBookMap.get(listName);
            } else {
                System.out.println("Address list with name " + listName + " not present. Please add it first.");
            }

            System.out.println(
                    "Enter a choice: \n 1)Add new contact \n 2)Edit contact \n 3)Delete Contact \n 4)Add Address Book \n " +
                            "5)View Address Book Contacts \n 6)Search person in a city or state across the multiple Address Books \n 7)View persons by city or state \n 8)Get count of contact persons by city or state \n " +
                            "9)Sort entries by name \n 10)Sort by City \n 11)Sort by State \n 12)Sort by Zip \n 13)Exit");
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1: {
                    System.out.println("Add Person Details:");
                    System.out.println("First Name:");
                    String firstName = sc.nextLine();
                    System.out.println("Last Name:");
                    String lastName = sc.nextLine();
                    if (checkForDuplicate(firstName))
                        continue;
                    System.out.println("Address:");
                    String address = sc.nextLine();
                    System.out.println("City:");
                    String city = sc.nextLine();
                    System.out.println("State:");
                    String state = sc.nextLine();
                    System.out.println("Zip:");
                    String zip = sc.nextLine();
                    System.out.println("Phone no:");
                    String phoneNo = sc.nextLine();
                    System.out.println("Email");
                    String email = sc.nextLine();
                    Contact contactObj = new Contact(firstName, lastName, address, city, state, zip, phoneNo, email);
                    boolean contactIsAdded = addressObj.addContact(contactObj);
                    addressObj.addToDictionary(contactIsAdded, contactObj);
                    break;
                }
                case 2: {
                    System.out.println(
                            "Enter first name of person to edit details:");
                    String firstName = sc.nextLine();
                    boolean contactFound = addressObj.editDetails(firstName);
                    if (contactFound)
                        System.out.println("Details successfully edit");
                    else
                        System.out.println("Contact not found");
                    break;
                }
                case 3: {
                    System.out.println(
                            "Enter first name of person to delete data");
                    String firstName = sc.nextLine();
                    boolean contactFound = addressObj.removeDetails(firstName);
                    if (contactFound)
                        System.out.println("Details successfully deleted");
                    else
                        System.out.println("Contact not found");
                    break;
                }
                case 4: {
                    System.out.println("Enter the name of address book that u want to add:");
                    listName = sc.nextLine();
                    addressObj.addAddressBook(listName);
                    break;
                }
                case 5: {
                    System.out.println(" " + addressList);
                    break;
                }
                case 6: {
                    System.out.println("Enter the name of city or state");
                    String cityOrState = sc.nextLine();
                    System.out.println("Enter 1 if you entered name of a city \nEnter 2 if you entered name of a state");
                    int searchChoice = Integer.parseInt(sc.nextLine());
                    addressObj.searchPersonAcrossCityState(searchChoice, cityOrState);
                }
                case 7: {
                    System.out.println("Enter the name of city or state");
                    String cityOrState = sc.nextLine();
                    System.out.println("Enter 1 if you entered name of a city \nEnter 2 if you entered name of a state");
                    int searchChoice = Integer.parseInt(sc.nextLine());
                    addressObj.viewPersonsByCityState(cityOrState, searchChoice);
                    break;
                }
                case 8: {
                    System.out.println("Enter the name of city or state");
                    String cityOrState = sc.nextLine();
                    System.out.println("Enter 1 if you entered name of a city \nEnter 2 if you entered name of a state");
                    int searchChoice = Integer.parseInt(sc.nextLine());
                    System.out.println("Total persons in " + cityOrState + " = "
                            + addressObj.getCountByCityState(cityOrState, searchChoice));
                    break;
                }
                case 9: {
                    List<Contact> sortedEntriesList = addressObj.sortAddressBookByName(addressList);
                    System.out.println("Entries sorted in current address book. Sorted Address Book Entries:");
                    System.out.println(sortedEntriesList);
                    break;
                }
                case 10: addressObj.sortByCity();
                         break;
                case 11: addressObj.sortByState();
                         break;
                case 12: addressObj.sortByZip();
                         break;
                case 13: AddressBookMain.writeData(addressObj);
                         break;
                case 14: AddressBookMain.readData(addressObj);
                         break;
                case 15: System.exit(0);
                         break;
            }
        }
    }
}