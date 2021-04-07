import java.util.*;
import java.net.*;
import java.text.*;
import java.lang.*;
import java.io.*;
import java.sql.*;
import pgpass.*;

// Add Purchase Into the Relation
public class AddPurchase {
    private Connection conDB;        // Connection to the database system.
    private String url;              // URL: Which database?

    // Fields for Purchase Tuple
    private short cid;
    private String club;
    private String title;
    private short year;
    private java.sql.Timestamp whenp;
    private short qnty;
    private String user; // Database user account

    // User Flags
    private final String cid_flag = "-c";
    private final String club_flag = "-b";
    private final String title_flag = "-t";
    private final String year_flag = "-y";
    private final String whenp_flag = "-w";
    private final String qnty_flag = "-q";
    private final String user_flag = "-u";

    /* ERROR LOG 
     * 
     * ERROR #1
     * The customer (cid), the club, or the book (title & year) does not exist: 
     * if it does not exist in the corresponding table, 
     * the app should state this and not make any changes to the database.
     * 
     * ERROR #2
     * The customer (cid) doesn't belong to that club: if the customer is not a member of the given club, 
     * the app should state this and not make any changes to the database.
     * 
     * ERROR #3
     * The club doesn't offer the book (title & year): if the club does not offer the book, 
     * the app should state this and not make any changes to the database.
     * 
     * ERROR #4
     * whenp is not today: if the new purchase is not made in today (the day performing your app),
     * the app should state this and not make any changes to the database.
     * 
     * ERROR #5
     * qnty value is improper: if the qnty is not a positive integer, 
     * the app should state this and not make any changes to the database. 
     *
     */

    // Constructor
    public AddPurchase (String[] args) {

        // Set Up DB Connection
        try {
            // Register the driver with DriverManager.
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // Parse the user to see if an arguement was given, otherwise use the default user
        user = "ericn576"; // Default
        for(int i = 0; i < args.length; i++) {
            if(user_flag.equals(args[i]) && i + 1 < args.length) {
                try {
                    user = args[i + 1];
                    break;
                }
                catch (Exception e) {
                    System.out.println("Incorrect Type Used for user");
                    System.exit(0);
                }
            }
        }

        // URL: What database?
        url = "jdbc:postgresql://db:5432/" + user;

        // set up acct info from pgpass file
        Properties props = new Properties();
        try {
            String passwd = PgPass.get("db", "5432", user, user);
            props.setProperty("user", user);
            props.setProperty("password", passwd);

        } catch(PgPassException e) {
            System.out.print("\nCould not obtain PASSWD from <.pgpass>.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Initialize the connection.
        try {
            // Connect with a fall-thru id & password
            conDB = DriverManager.getConnection(url, props);
        } catch(SQLException e) {
            System.out.print("\nSQL: database connection error.\n");
            System.out.println(e.toString());
            System.exit(0);
        }    

        // Turn off autocommit
        try {
            conDB.setAutoCommit(false);
        } catch(SQLException e) {
            System.out.print("\nFailed trying to turn autocommit off.\n");
            e.printStackTrace();
            System.exit(0);
        }    

        // Parse the rest of the Parameters
        boolean hasRequiredParam = false;

        // Parse the cid
        for(int i = 0; i < args.length; i++) {
            if(cid_flag.equals(args[i]) && i + 1 < args.length) {
                try {
                    cid = new Short(args[i + 1]);
                    hasRequiredParam = true;
                    break;
                }
                catch (NumberFormatException e) {
                    System.out.println("Incorrect Type Used for cid");
                    System.exit(0);
                }
            }
            else {
                hasRequiredParam = false;
            }
        }
        
        // Check if violated ERROR#1
        if(hasRequiredParam == false) {
            System.out.println("\nERROR#1 : The customer (cid), the club, or the book (title & year) does not exist");
            System.exit(0);
        }

        // Parse the club
        for(int i = 0; i < args.length; i++) {
            if(club_flag.equals(args[i]) && i + 1 < args.length) {
                try {
                    club = args[i + 1];
                    hasRequiredParam = true;
                    break;
                }
                catch (Exception e) {
                    System.out.println("Incorrect Type Used for club");
                    System.exit(0);
                }
            }
            else {
                hasRequiredParam = false;
            }
        }

        // Check if violated ERROR#1
        if(hasRequiredParam == false) {
            System.out.println("\nERROR#1 : The customer (cid), the club, or the book (title & year) does not exist");
            System.exit(0);
        }

        // Parse the title
        for(int i = 0; i < args.length; i++) {
            if(title_flag.equals(args[i]) && i + 1 < args.length) {
                try {
                    title = args[i + 1];
                    hasRequiredParam = true;
                    break;
                }
                catch (Exception e) {
                    System.out.println("Incorrect Type Used for title");
                    System.exit(0);
                }
            }
            else {
                hasRequiredParam = false;
            }
        }      

        // Check if violated ERROR#1
        if(hasRequiredParam == false) {
            System.out.println("\nERROR#1 : The customer (cid), the club, or the book (title & year) does not exist");
            System.exit(0);
        }

        // Parse the year
        for(int i = 0; i < args.length; i++) {
            if(year_flag.equals(args[i]) && i + 1 < args.length) {
                try {
                    year = new Short(args[i + 1]);
                    hasRequiredParam = true;
                    break;
                }
                catch (NumberFormatException e) {
                    System.out.println("Incorrect Type Used for year");
                    System.exit(0);
                }
            }
            else {
                hasRequiredParam = false;
            }
        }        

        // Check if violated ERROR#1
        if(hasRequiredParam == false) {
            System.out.println("\nERROR#1 : The customer (cid), the club, or the book (title & year) does not exist");
            System.exit(0);
        }

        // Parse the whenp
        whenp = new java.sql.Timestamp(new java.util.Date().getTime()); // Default
        // PrepStmt.setTimestamp(1, date);
        for(int i = 0; i < args.length; i++) {
            if(whenp_flag.equals(args[i]) && i + 1 < args.length) {
                if(args[i + 1].length() == 10) {
                    try {
                        whenp = java.sql.Timestamp.valueOf(args[i + 1] + " 00:00:00");
                        break;
                    }
                    catch (Exception e) {
                        System.out.println("Incorrect Type Used for whenp");
                        System.exit(0);
                    }
                }
                else {
                    try {
                        whenp = java.sql.Timestamp.valueOf(args[i + 1]);
                        break;
                    }
                    catch (Exception e) {
                        System.out.println("Incorrect Type Used for whenp");
                        System.exit(0);
                    }
                }
            }
        }

        // Parse the qnty
        qnty = 1; // Default
        for(int i = 0; i < args.length; i++) {
            if(qnty_flag.equals(args[i]) && i + 1 < args.length) {
                try {
                    qnty = new Short(args[i + 1]);
                    break;
                }
                catch (NumberFormatException e) {
                    System.out.println("Incorrect Type Used for qnty");
                    System.exit(0);
                }
            }
        }

        // Check if violated ERROR#2
        if(!belongsToClub()) {
            System.out.println("\nERROR#2 : The customer (cid) doesn't belong to that club: if the customer is not a member of the given club");
            System.exit(0);
        }

        // Check if violated ERROR#3
        if(!offersBook()) {
            System.out.println("\nERROR#3 : The club doesn't offer the book (title & year): if the club does not offer the book");
            System.exit(0);
        }

        // Check if violated ERROR#4
        java.sql.Timestamp curr_date = new java.sql.Timestamp(new java.util.Date().getTime()); // Current Date
        if(!(whenp.toString().substring(0, 10)).equals(curr_date.toString().substring(0, 10))) {
            System.out.println("\nERROR#4 : whenp is not today: if the new purchase is not made in today (the day performing your app)");
            System.exit(0);
        }

        // Check if violated ERROR#5
        if(qnty < 0) {
            System.out.println("\nERROR#5 : qnty value is improper: if the qnty is not a positive integer");
            System.exit(0);
        }

        // No Errors! Now create statement to insert into the relation
        
        String insertText = "INSERT INTO yrb_purchase(cid,club,title,year,whenp,qnty) VALUES(" + cid + ",'" + club + "','" + title + "'," + year + ",'" + whenp + "'," + qnty + ");";
        PreparedStatement insertSt = null;

        // Prepare the statement.
        try {
            insertSt = conDB.prepareStatement(insertText);
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Execute the insert statement.
        try {
            insertSt.executeUpdate();
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.
        try {
            insertSt.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }    

        // Commit Changes
        try {
            conDB.commit();
        } catch(SQLException e) {
            System.out.print("\nFailed trying to commit.\n");
            e.printStackTrace();
            System.exit(0);
        }    

        // Close the connection.
        try {
            conDB.close();
        } catch(SQLException e) {
            System.out.print("\nFailed trying to close the connection.\n");
            e.printStackTrace();
            System.exit(0);
        }    

    }

    // Check for ERROR#2
    public boolean belongsToClub() {
        String queryText = "SELECT * FROM yrb_member WHERE cid = " + cid + " AND club = '" + club + "'";
        PreparedStatement querySt = null;
        ResultSet answers = null;

        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Execute the query.
        try {
            answers = querySt.executeQuery();
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Any answer?
        try {
           if(answers.next() == false) {
               return false;
           }
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }

       // Close the cursor.
        try {
            answers.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.
        try {
            querySt.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }    

        return true;
    }

    // Check for ERROR#3
    public boolean offersBook() {
        String queryText = "SELECT * FROM yrb_offer WHERE club = '" + club + "' AND title = '" + title + "' AND year = " + year + ";";
        PreparedStatement querySt = null;
        ResultSet answers = null;

        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Execute the query.
        try {
            answers = querySt.executeQuery();
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Any answer?
        try {
           if(answers.next() == false) {
               return false;
           }
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }

       // Close the cursor.
        try {
            answers.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.
        try {
            querySt.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }    

        return true;
    }

    public static void main(String[] args) {
        AddPurchase ct = new AddPurchase(args);
    }
 }