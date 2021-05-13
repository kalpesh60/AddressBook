import java.util.*;

public class AddressBookMain {
    Scanner sc = new Scanner(System.in);
    private static List<Contact> addressList = new ArrayList<Contact>();
    HashMap<String, List<Contact>> addressBookMap = new HashMap<String, List<Contact>>();
    HashMap<Contact, String> personCityMap = new HashMap<Contact, String>();
    HashMap<Contact, String> personStateMap = new HashMap<Contact, String>();

    public void addContact(Contact contact) {
        addressList.add(contact);
        System.out.println("Contact Added.\n" + contact);
    }

    public boolean editDetails(String firstName) {
        Contact edit;
        boolean contactFound = false;
        for (int i = 0; i < addressList.size(); i++) {
            edit = addressList.get(i);
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
            remove = (Contact) addressList.get(i);
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
        if (contactIsAdded == true) {
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
        Collections.sort(sortList, new Contact());
        return sortList;
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
                addressObj.addressList = addressObj.addressBookMap.get(listName);
            } else {
                System.out.println("Address list with name " + listName + " not present. Please add it first.");
            }

            System.out.println(
                    "Enter a choice: \n 1)Add a new contact \n 2)Edit a contact \n 3)Delete Contact \n 4)Add Address Book \n 5)View Address Book Contacts \n 6)Search person in a city or state across the multiple Address Books \n 7)View persons by city or state \n 8)Get count of contact persons by city or state \n 9)Sort entries by name in current address book \n 10)Exit");
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                case 2: {
                    System.out.println(
                            "Enter first name of person to edit details:");
                    String firstName = sc.nextLine();
                    boolean contactFound = addressObj.editDetails(firstName);
                    if (contactFound == true)
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
                    if (contactFound == true)
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
                    System.out.println(" " + addressObj.addressList);
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
                    List<Contact> sortedEntriesList = addressObj.sortAddressBookByName(addressObj.addressList);
                    System.out.println("Entries sorted in current address book. Sorted Address Book Entries:");
                    System.out.println(sortedEntriesList);
                    break;
                }
                case 10:
                    System.exit(0);
                    break;
            }
        }
    }
}