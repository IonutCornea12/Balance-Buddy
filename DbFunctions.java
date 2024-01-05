
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


}





