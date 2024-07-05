# MySQL Connection
Developed to make it easy to establish a connection to the MySQL database

#### Features
1) Create a connection
2) CRUD Operations
3) SQL Injection Protection

#### Methods
1) >.createConnection(ip, port, database, userName, password);
2) >.executeIUD(query, args);
3) >.executeSearch(query, args);

#### How to Use
1) Download the .jar file and add it to your project
2) Import Library:
   
   ```java
   
   import com.MySQL;
   
   ```

3) Use Methods: </br></br>
    i) .createConnection(ip, port, database, userName, password) </br>
    > This method is used to create a connection with the database. Req to do it at the beginning.
    
   ```java
   
   try {
       MySQL.createConnection(ip, port, database, userName, password);
   } catch (Exception e) {
       e.printStackTrace();
   }
   
   ```
    ii) .executeIUD(query, args); </br>
    > This method is used for the Insert,Update, and Delete operations.</br>
    > query -> mysql query</br>
    > args -> arguments for the query
    
   ```java
   
   try {
       MySQL.executeIUD("UPDATE `test` SET `name`=? WHERE `id`=?", "Kamal","5");
   } catch (Exception e) {
       e.printStackTrace();
   }
   
   ```
    iii) .executeSearch(query, args); </br>
    > This method is used for the Search operations.</br>
    > query -> mysql query</br>
    > args -> arguments for the search query
    
   ```java
   
   try {
       ResultSet executeSearch = MySQL.executeSearch("SELECT * FROM `user` WHERE `id`=?", "5");
   } catch (Exception e) {
       e.printStackTrace();
   }
   
   ```

  #### Outputs:
  1) .createConnection(ip, port, database, userName, password) -> no return value
  2) .executeIUD(query, args); -> no return value
  3) .executeSearch(query, args); -> ResultSet
     ```java
     import java.sql.ResultSet;
     ```

#### Example Code:

```java

import com.MySQL;
import java.sql.ResultSet;

public class Test {

    public static void main(String[] args) {

        try {
            MySQL.createConnection(ip, port, database, userName, password);
            
            MySQL.executeIUD(query, args);
            
            ResultSet executeSearch = MySQL.executeSearch(query, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


```
 
