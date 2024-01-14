
package com.example.BalanceBuddy;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbFunctions {

     public DbFunctions() {
     }

     public static Connection connect() throws SQLException {
          return DriverManager.getConnection("jdbc:postgresql://localhost:5432/accounting", "postgres", "Ionut.cornea12");
     }

     public static boolean authenticateUser(String userEmail, String userPassword) {
          String query = "SELECT * FROM users WHERE email = ? AND password = ?";

          try {
               Connection con = connect();

               boolean var6;
               try {
                    PreparedStatement pst = con.prepareStatement(query);

                    try {
                         pst.setString(1, userEmail);
                         pst.setString(2, userPassword);
                         ResultSet resultSet = pst.executeQuery();

                         try {
                              var6 = resultSet.next();
                         } catch (Throwable var11) {
                              if (resultSet != null) {
                                   try {
                                        resultSet.close();
                                   } catch (Throwable var10) {
                                        var11.addSuppressed(var10);
                                   }
                              }

                              throw var11;
                         }

                         if (resultSet != null) {
                              resultSet.close();
                         }
                    } catch (Throwable var12) {
                         if (pst != null) {
                              try {
                                   pst.close();
                              } catch (Throwable var9) {
                                   var12.addSuppressed(var9);
                              }
                         }

                         throw var12;
                    }

                    if (pst != null) {
                         pst.close();
                    }
               } catch (Throwable var13) {
                    if (con != null) {
                         try {
                              con.close();
                         } catch (Throwable var8) {
                              var13.addSuppressed(var8);
                         }
                    }

                    throw var13;
               }

               if (con != null) {
                    con.close();
               }

               return var6;
          } catch (SQLException var14) {
               Logger lgr = Logger.getLogger(DbFunctions.class.getName());
               lgr.log(Level.SEVERE, var14.getMessage(), var14);
               return false;
          }
     }

     public static boolean addUser(String userEmail, String userPassword) {
          try {
               Connection connection = connect();

               boolean var5;
               try {
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO users (email, password) VALUES (?, ?)");

                    try {
                         statement.setString(1, userEmail);
                         statement.setString(2, userPassword);
                         int rowsAffected = statement.executeUpdate();
                         var5 = rowsAffected > 0;
                    } catch (Throwable var8) {
                         if (statement != null) {
                              try {
                                   statement.close();
                              } catch (Throwable var7) {
                                   var8.addSuppressed(var7);
                              }
                         }

                         throw var8;
                    }

                    if (statement != null) {
                         statement.close();
                    }
               } catch (Throwable var9) {
                    if (connection != null) {
                         try {
                              connection.close();
                         } catch (Throwable var6) {
                              var9.addSuppressed(var6);
                         }
                    }

                    throw var9;
               }

               if (connection != null) {
                    connection.close();
               }

               return var5;
          } catch (SQLException var10) {
               Logger logger = Logger.getLogger(DbFunctions.class.getName());
               logger.log(Level.SEVERE, "Error adding worker", var10);
               return false;
          }
     }

     public static boolean addTransaction(Transaction transaction) {
          try {
               Connection connection = connect();

               boolean var5;
               try {
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO transactions (amount, transaction_date, description, measure_unit,supplier_name,id)  VALUES (?,?, ?, ?, ?,?)");

                    try {
                         statement.setFloat(1, transaction.getAmount());
                         Date sqlDate = new Date(transaction.getDate().getTime());
                         statement.setDate(2, sqlDate);
                         statement.setString(3, transaction.getDescription());
                         statement.setString(4, transaction.getMeasureUnit());
                         statement.setString(5, transaction.getsupplierName());
                         statement.setInt(6, transaction.getId());
                         int rowsAffected = statement.executeUpdate();
                         var5 = rowsAffected > 0;
                    } catch (Throwable var8) {
                         if (statement != null) {
                              try {
                                   statement.close();
                              } catch (Throwable var7) {
                                   var8.addSuppressed(var7);
                              }
                         }

                         throw var8;
                    }

                    if (statement != null) {
                         statement.close();
                    }
               } catch (Throwable var9) {
                    if (connection != null) {
                         try {
                              connection.close();
                         } catch (Throwable var6) {
                              var9.addSuppressed(var6);
                         }
                    }

                    throw var9;
               }

               if (connection != null) {
                    connection.close();
               }

               return var5;
          } catch (SQLException var10) {
               Logger logger = Logger.getLogger(DbFunctions.class.getName());
               logger.log(Level.SEVERE, "Error adding transaction", var10);
               return false;
          }
     }

     public static boolean deleteTransaction(int id) {
          try {
               Connection connection = connect();
               try {
                    String query = "DELETE FROM transactions WHERE id = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, id);
                    int rowsAffected = statement.executeUpdate();
                    return rowsAffected > 0;
               } finally {
                    if (connection != null) {
                         connection.close();
                    }
               }
          } catch (SQLException e) {
               Logger logger = Logger.getLogger(DbFunctions.class.getName());
               logger.log(Level.SEVERE, "Error deleting transaction", e);
               return false;
          }
     }

     public static boolean addTransactionDetails(TransactionDetails transactionDetails) {
          try {
               Connection connection = connect();
               boolean success;
               try {
                    PreparedStatement statement = connection.prepareStatement(
                            "INSERT INTO transaction_details (transactionid, price, tva, price_no_tva, discount, total) VALUES (?, ?, ?, ?, ?, ?)"
                    );

                    try {
                         statement.setInt(1, transactionDetails.getTransactionId());
                         statement.setFloat(2, transactionDetails.getPrice());
                         statement.setInt(3, transactionDetails.getTva());
                         statement.setFloat(4, transactionDetails.getPriceNoTva());
                         statement.setInt(5, transactionDetails.getDiscount());
                         statement.setFloat(6, transactionDetails.getTotal());

                         int rowsAffected = statement.executeUpdate();
                         success = rowsAffected > 0;
                    } catch (Throwable var8) {
                         if (statement != null) {
                              try {
                                   statement.close();
                              } catch (Throwable var7) {
                                   var8.addSuppressed(var7);
                              }
                         }
                         throw var8;
                    } finally {
                         if (statement != null) {
                              statement.close();
                         }
                    }
               } catch (Throwable var9) {
                    if (connection != null) {
                         try {
                              connection.close();
                         } catch (Throwable var6) {
                              var9.addSuppressed(var6);
                         }
                    }
                    throw var9;
               } finally {
                    if (connection != null) {
                         connection.close();
                    }
               }

               return success;
          } catch (SQLException var10) {
               Logger logger = Logger.getLogger(DbFunctions.class.getName());
               logger.log(Level.SEVERE, "Error adding transaction details", var10);
               return false;
          }
     }

     public static boolean deleteTransactionDetails(int transactionId) {
          try {
               Connection connection = connect();
               try {
                    String query = "DELETE FROM transaction_details WHERE transactionid = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, transactionId);
                    int rowsAffected = statement.executeUpdate();
                    return rowsAffected > 0;
               } finally {
                    if (connection != null) {
                         connection.close();
                    }
               }
          } catch (SQLException e) {
               Logger logger = Logger.getLogger(DbFunctions.class.getName());
               logger.log(Level.SEVERE, "Error deleting transaction", e);
               return false;
          }
     }

     public static boolean addSupplier(Suppliers supplier) {
          try {
               Connection connection = connect();

               boolean var5;
               try {
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO suppliers (name, address, phone_number, cui) VALUES (?, ?, ?,?)");

                    try {
                         statement.setString(1, supplier.getname());
                         statement.setString(2, supplier.getaddress());
                         statement.setString(3, supplier.getphone());
                         statement.setString(4, supplier.getcui());
                         int rowsAffected = statement.executeUpdate();
                         var5 = rowsAffected > 0;
                    } catch (Throwable var8) {
                         if (statement != null) {
                              try {
                                   statement.close();
                              } catch (Throwable var7) {
                                   var8.addSuppressed(var7);
                              }
                         }

                         throw var8;
                    }

                    if (statement != null) {
                         statement.close();
                    }
               } catch (Throwable var9) {
                    if (connection != null) {
                         try {
                              connection.close();
                         } catch (Throwable var6) {
                              var9.addSuppressed(var6);
                         }
                    }

                    throw var9;
               }

               if (connection != null) {
                    connection.close();
               }

               return var5;
          } catch (SQLException var10) {
               Logger logger = Logger.getLogger(DbFunctions.class.getName());
               logger.log(Level.SEVERE, "Error adding supplier", var10);
               return false;
          }
     }

     public static boolean deletesupplier(int supplierId) {
          try {
               Connection connection = connect();
               try {
                    String query = "DELETE FROM suppliers WHERE id = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, supplierId);
                    int rowsAffected = statement.executeUpdate();
                    return rowsAffected > 0;
               } finally {
                    if (connection != null) {
                         connection.close();
                    }
               }
          } catch (SQLException e) {
               Logger logger = Logger.getLogger(DbFunctions.class.getName());
               logger.log(Level.SEVERE, "Error deleting supplier", e);
               return false;
          }
     }


     public static boolean addSummary(Summary summary) {
               String query = "INSERT INTO summary (date, total) VALUES (?, ?)";

               try (Connection con = connect();
                    PreparedStatement pst = con.prepareStatement(query)) {


                    Date sqlDateSum = new Date(summary.getDateSum().getTime());
                    pst.setDate(1, sqlDateSum);

                    pst.setFloat(2, summary.getTotalSum());

                    int rowsAffected = pst.executeUpdate();
                    return rowsAffected > 0;

               } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
               }
          }

     public static boolean deleteSummary(int summaryId) {
          try {
               Connection connection = connect();
               try {
                    String query = "DELETE FROM summary WHERE id = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, summaryId);
                    int rowsAffected = statement.executeUpdate();
                    return rowsAffected > 0;
               } finally {
                    if (connection != null) {
                         connection.close();
                    }
               }
          } catch (SQLException e) {
               Logger logger = Logger.getLogger(DbFunctions.class.getName());
               logger.log(Level.SEVERE, "Error deleting summary", e);
               return false;
          }
     }


     public static List<Integer> getTransactionIdsBySupplierName(Connection con, String supplierName) {
          List<Integer> transactionIds = new ArrayList<>();

          String query = "SELECT t.id FROM transactions t " +
                  "JOIN suppliers s ON t.supplier_name = s.\"name\" " +
                  "WHERE s.\"name\" = ?";

          try (PreparedStatement pst = con.prepareStatement(query)) {
               pst.setString(1, supplierName);

               try (ResultSet resultSet = pst.executeQuery()) {
                    while (resultSet.next()) {
                         int transactionId = resultSet.getInt("id");
                         transactionIds.add(transactionId);
                    }
               }
          } catch (SQLException e) {
               // Handle the exception or log the error
               e.printStackTrace();
          }

          return transactionIds;
     }
     public static int getSupplierIdByName(Connection con, String supplierName) throws SQLException {
          String query = "SELECT id FROM suppliers WHERE name = ?";

          try (PreparedStatement pst = con.prepareStatement(query)) {
               pst.setString(1, supplierName);

               try (ResultSet resultSet = pst.executeQuery()) {
                    if (resultSet.next()) {
                         return resultSet.getInt("id");
                    } else {
                         return -1; // Supplier not found
                    }
               }
          }
     }

     public static boolean addTransactionsForSupplier(Connection con, int supplierId, List<Integer> transactionIds) {
          try {
               // Check if the records already exist for the given supplier and transactions
               if (recordsExistInSuppliersTransactions(con, supplierId, transactionIds)) {
                    System.out.println("Records already exist in suppliers_transactions table");
                    return true;
               }

               // Add records to the suppliers_transactions table
               String insertQuery = "INSERT INTO suppliers_transactions (transaction_id, supplier_id) VALUES (?, ?)";
               try (PreparedStatement pst = con.prepareStatement(insertQuery)) {
                    for (int transactionId : transactionIds) {
                         pst.setInt(1, transactionId);
                         pst.setInt(2, supplierId);
                         pst.addBatch();
                    }

                    // Execute the batch update
                    int[] result = pst.executeBatch();

                    // Check if all records were successfully inserted
                    for (int i : result) {
                         if (i == PreparedStatement.EXECUTE_FAILED) {
                              System.err.println("Failed to add records to suppliers_transactions table");
                              return false;
                         }
                    }

                    System.out.println("Records added to suppliers_transactions table successfully");
                    return true;
               }
          } catch (SQLException e) {
               // Handle the exception or log the error
               e.printStackTrace();
               return false;
          }
     }

     private static boolean recordsExistInSuppliersTransactions(Connection con, int supplierId, List<Integer> transactionIds) throws SQLException {
          String query = "SELECT COUNT(*) FROM suppliers_transactions WHERE supplier_id = ? AND transaction_id = ?";

          try (PreparedStatement pst = con.prepareStatement(query)) {
               for (int transactionId : transactionIds) {
                    pst.setInt(1, supplierId);
                    pst.setInt(2, transactionId);

                    try (ResultSet resultSet = pst.executeQuery()) {
                         if (resultSet.next() && resultSet.getInt(1) > 0) {
                              return true; // Records already exist
                         }
                    }
               }
          }

          return false; // Records do not exist
     }

     public static List<Integer> getTransactionIdsBySupplierId(Connection con, int supplierId) {
          List<Integer> transactionIds = new ArrayList<>();

          String query = "SELECT transaction_id FROM suppliers_transactions WHERE supplier_id = ?";

          try (PreparedStatement pst = con.prepareStatement(query)) {
               pst.setInt(1, supplierId);

               try (ResultSet resultSet = pst.executeQuery()) {
                    while (resultSet.next()) {
                         int transactionId = resultSet.getInt("transaction_id");
                         transactionIds.add(transactionId);
                    }
               }
          } catch (SQLException e) {
               // Handle the exception or log the error
               e.printStackTrace();
          }

          return transactionIds;
     }



     public static List<Transaction> getAllTransactions() {
          List<Transaction> transactions = new ArrayList<>();

          String query = "SELECT * FROM transactions";

          try (Connection con = connect();
               PreparedStatement pst = con.prepareStatement(query);
               ResultSet resultSet = pst.executeQuery()) {

               while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    float amount = resultSet.getFloat("amount");
                    Date date = resultSet.getDate("transaction_date");
                    String description = resultSet.getString("description");
                    String measureUnit = resultSet.getString("measure_unit");
                    String supplierName = resultSet.getString("supplier_name");

                    Transaction transaction = new Transaction(id, date, description, amount, measureUnit, supplierName);
                    transactions.add(transaction);
               }

          } catch (SQLException e) {
               Logger logger = Logger.getLogger(DbFunctions.class.getName());
               logger.log(Level.SEVERE, "Error getting all transactions", e);
          }

          return transactions;
     }

     public static List<TransactionDetails> getAllTransactionDetails() {
          List<TransactionDetails> transactiondetails = new ArrayList<>();

          String query = "SELECT * FROM transaction_details";

          try (Connection con = connect();
               PreparedStatement pst = con.prepareStatement(query);
               ResultSet resultSet = pst.executeQuery()) {

               while (resultSet.next()) {
                    float price = resultSet.getFloat("price");
                    int tva = resultSet.getInt("tva");
                    float price_no_tva = resultSet.getFloat("price_no_tva");
                    int discount = resultSet.getInt("discount");
                    float total = resultSet.getFloat("total");
                    int transactionid = resultSet.getInt("transactionid");
                    TransactionDetails transactiondetail = new TransactionDetails(transactionid, price, tva, price_no_tva, discount, total);
                    transactiondetails.add(transactiondetail);
               }

          } catch (SQLException e) {
               Logger logger = Logger.getLogger(DbFunctions.class.getName());
               logger.log(Level.SEVERE, "Error getting all transactions", e);
          }

          return transactiondetails;
     }

     public static List<Suppliers> getAllSuppliers() {
          List<Suppliers> suppliers = new ArrayList<>();

          String query = "SELECT * FROM suppliers";

          try (Connection con = connect();
               PreparedStatement pst = con.prepareStatement(query);
               ResultSet resultSet = pst.executeQuery()) {

               while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String address = resultSet.getString("address");
                    String phone_number = resultSet.getString("phone_number");
                    String cui = resultSet.getString("cui");
                    Suppliers supplier = new Suppliers(id, name,address,phone_number,cui);
                    suppliers.add(supplier);
               }

          } catch (SQLException e) {
               Logger logger = Logger.getLogger(DbFunctions.class.getName());
               logger.log(Level.SEVERE, "Error getting all transactions", e);
          }

          return suppliers;
     }

     public static List<Summary> getAllSummaries() {
          List<Summary> summaries = new ArrayList<>();

          String query = "SELECT * FROM summary";

          try (Connection con = connect();
               PreparedStatement pst = con.prepareStatement(query);
               ResultSet resultSet = pst.executeQuery()) {

               while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    float total = resultSet.getFloat("total");
                    Date date = resultSet.getDate("date");
                    Summary summary = new Summary(id, date, total);
                    summaries.add(summary);
               }

          } catch (SQLException e) {
               Logger logger = Logger.getLogger(DbFunctions.class.getName());
               logger.log(Level.SEVERE, "Error getting all transactions", e);
          }

          return summaries;
     }
}







