package myServer.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLHandler {

    private static final SQLHandler instance=new SQLHandler();

    private List<Contact> contacts;
    
    private static final String CONTACT_TABLE = "Contact";

    private SQLHandler() {
        this.contacts=new ArrayList<>();
        try (Statement stmt = DbConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + CONTACT_TABLE);) {
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setLogin(rs.getString("login"));
                contact.setPassword(rs.getString("password"));
                this.contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SQLHandler getInstance(){
        return  new SQLHandler();
    }

    public boolean addContact(Contact c) {
        try (PreparedStatement stmt = DbConnection.getConnection().prepareStatement("INSERT INTO " + CONTACT_TABLE + "(login,password) values (?,?)");) {
            stmt.setString(1, c.getLogin());
            stmt.setString(2, MdHash.md5Custom(c.getPassword()));

            if (stmt.executeUpdate() == 1) { // если была добавлена 1 запись
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Контакт существует");
        }
        return false;
    }

    public boolean deleteContact(String login) {

        try (PreparedStatement stmt = DbConnection.getConnection().prepareStatement("delete from " + CONTACT_TABLE + " where login=?");) {

            stmt.setString(1, login);

            if (stmt.executeUpdate() == 1) {  // если была обновлена 1 запись
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Контакт отсутствует");
        }

        return false;
    }

    public boolean updateContact(String newLogin, String oldLogin) {

        try (PreparedStatement stmt = DbConnection.getConnection().prepareStatement("update " + CONTACT_TABLE + " SET login=?  where login=?");) {

            stmt.setString(1, newLogin);
            stmt.setString(2, oldLogin);

            if (stmt.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(String login, String newPassword) {
        try (PreparedStatement stmt = DbConnection.getConnection().prepareStatement("UPDATE " + CONTACT_TABLE + " SET password=? where login=?")) {
            stmt.setString(1, MdHash.md5Custom(newPassword));
            stmt.setString(2, login);

            if (stmt.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Contact> getContacts() {
    return contacts;
    }

    public boolean hasContact(Contact c){
        for (Contact o:contacts) {
            if(c.equals(o))return true;
        }
        return false;
    }
}

