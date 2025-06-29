package datamodel;

import java.util.*;

/**
 * Class of entity type <i>Customer</i>.
 * <p>
 * Customer is a person who creates and holds (owns) orders in the system.
 * </p>
 * 
 * @version {@value application.package_info#Version}
 * @author Luanne
 */
public class Customer {

    private long id = -1L; // Unique id, can be set only once
    private String lastName = ""; // Never null
    private String firstName = ""; // Never null
    private final List<String> contacts = new ArrayList<>();

    public Customer() {
        // Default constructor
    }

    /**
     * Constructor with single-String name argument.
     * @param name single-String Customer name, e.g. "Eric Meyer"
     * @throws IllegalArgumentException if name argument is null
     */
    public Customer(String name) {
        setName(name);
    }

    public long getId() {
        return id;
    }

    public Customer setId(long id) {
        if (this.id >= 0 || id < 0)
            throw new IllegalArgumentException("Invalid or already set ID");
        this.id = id;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer setName(String first, String last) {
        if (first != null) this.firstName = trim(first);
        if (last != null) this.lastName = trim(last);
        return this;
    }

    public Customer setName(String name) {
        splitName(name);
        return this;
    }

    public int contactsCount() {
        return contacts.size();
    }

    public Iterable<String> getContacts() {
        return contacts;
    }

    public Customer addContact(String contact) {
        if (contact == null || contact.isBlank())
            throw new IllegalArgumentException("Invalid contact");
        contact = trim(contact);
        if (!contacts.contains(contact))
            contacts.add(contact);
        return this;
    }

    public void deleteContact(int i) {
        if (i >= 0 && i < contacts.size())
            contacts.remove(i);
    }

    public void deleteAllContacts() {
        contacts.clear();
    }

    /**
     * Split single-String name into last- and first name parts according to
     * rules:
     * <ul>
     * <li> if a name contains no seperators (comma or semicolon {@code [,;]}),
     *      the trailing consecutive part is the last name, all prior parts
     *      are first name parts, e.g. {@code "Tim Anton Schulz-Müller"}, splits
     *      into <i>first name:</i> {@code "Tim Anton"} and <i>last name</i>
     *      {@code "Schulz-Müller"}.
     * <li> names with seperators (comma or semicolon {@code [,;]}) split into
     *      a last name part before the seperator and a first name part after
     *      the seperator, e.g. {@code "Schulz-Müller, Tim Anton"} splits into
     *      <i>first name:</i> {@code "Tim Anton"} and <i>last name</i>
     *      {@code "Schulz-Müller"}.
     * <li> leading and trailing white spaces {@code [\s]}, commata {@code [,;]}
     *      and quotes {@code ["']} must be trimmed from names, e.g.
     *      {@code "  'Schulz-Müller, Tim Anton'    "}.
     * <li> interim white spaces between name parts must be trimmed, e.g.
     *      {@code "Schulz-Müller, <white-spaces> Tim <white-spaces> Anton <white-spaces> "}.
     * </ul>
     * <pre>
     * Examples:
     * +------------------------------------+-----------------------+-----------------------+
     * |Single-String name                  |first name parts       |last name parts        |
     * +------------------------------------+-----------------------+-----------------------+
     * |"Eric Meyer"                        |"Eric"                 |"Meyer"                |
     * |"Meyer, Anne"                       |"Anne"                 |"Meyer"                |
     * |"Meyer; Anne"                       |"Anne"                 |"Meyer"                |
     * |"Tim Schulz‐Mueller"                |"Tim"                  |"Schulz‐Mueller"       |
     * |"Nadine Ulla Blumenfeld"            |"Nadine Ulla"          |"Blumenfeld"           |
     * |"Nadine‐Ulla Blumenfeld"            |"Nadine‐Ulla"          |"Blumenfeld"           |
     * |"Khaled Saad Mohamed Abdelalim"     |"Khaled Saad Mohamed"  |"Abdelalim"            |
     * +------------------------------------+-----------------------+-----------------------+
     * 
     * Trim leading, trailing and interim white spaces and quotes:
     * +------------------------------------+-----------------------+-----------------------+
     * |" 'Eric Meyer'  "                   |"Eric"                 |"Meyer"                |
     * |"Nadine     Ulla     Blumenfeld"    |"Nadine Ulla"          |"Blumenfeld"           |
     * +------------------------------------+-----------------------+-----------------------+
     * </pre>
     * @param name single-String name to split into first- and last name parts
     * @throws IllegalArgumentException if name argument is null or empty
     */
     public void splitName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");

        name = trim(name);
        String[] parts;
        if (name.contains(",") || name.contains(";")) {
            parts = name.split("[,;]", 2);
            lastName = trim(parts[0]);
            firstName = trim(parts[1]);
        } else {
            parts = name.trim().split("\\s+");
            if (parts.length == 1) {
                firstName = parts[0];
                lastName = "";
            } else {
                lastName = parts[parts.length - 1];
                firstName = String.join(" ", Arrays.copyOf(parts, parts.length - 1));
            }
        }
    }

    /**
     * Trim leading and trailing white spaces, commata and quotes from a String.
     * @param s String to trim
     * @return trimmed String
     */
    private String trim(String s) {
        s = s.replaceAll("^[\\s\"',;]*", "");
        s = s.replaceAll("[\\s\"',;]*$", "");
        return s;
    }
}