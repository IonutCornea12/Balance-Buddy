//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.proiect2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO transactions (amount, transaction_date, description, measure_unit) VALUES (?, ?, ?, ?)");

                    try {
                         statement.setDouble(1, transaction.getAmount());
                         Date sqlDate = new Date(transaction.getDate().getTime());
                         statement.setDate(2, sqlDate);
                         statement.setString(3, transaction.getDescription());
                         statement.setString(4, transaction.getMeasureUnit());
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
     public static boolean deleteTransaction(int transactionId) {
          try {
               Connection connection = connect();
               try {
                    String query = "DELETE FROM transactions WHERE id = ?";
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

     public static List<Transaction> getAllTransactions() {
          List<Transaction> transactions = new ArrayList();
          String query = "SELECT id, amount, transaction_date, description, measure_unit FROM transactions";


          try {
               Connection connection = connect();

               try {
                    Statement statement = connection.createStatement();

                    try {
                         ResultSet resultSet = statement.executeQuery(query);

                         try {
                              while(resultSet.next()) {
                                   int id = resultSet.getInt("id");
                                   double amount = resultSet.getDouble("amount");
                                   Date transactionDate = resultSet.getDate("transaction_date");
                                   String description = resultSet.getString("description");
                                   String measureUnit = resultSet.getString("measure_unit");
                                   Transaction transaction = new Transaction(id,new Date((long)transactionDate.getDate()), description, amount, measureUnit);
                                   transactions.add(transaction);
                              }
                         } catch (Throwable var14) {
                              if (resultSet != null) {
                                   try {
                                        resultSet.close();
                                   } catch (Throwable var13) {
                                        var14.addSuppressed(var13);
                                   }
                              }

                              throw var14;
                         }

                         if (resultSet != null) {
                              resultSet.close();
                         }
                    } catch (Throwable var15) {
                         if (statement != null) {
                              try {
                                   statement.close();
                              } catch (Throwable var12) {
                                   var15.addSuppressed(var12);
                              }
                         }

                         throw var15;
                    }

                    if (statement != null) {
                         statement.close();
                    }
               } catch (Throwable var16) {
                    if (connection != null) {
                         try {
                              connection.close();
                         } catch (Throwable var11) {
                              var16.addSuppressed(var11);
                         }
                    }

                    throw var16;
               }

               if (connection != null) {
                    connection.close();
               }
          } catch (SQLException var17) {
               Logger logger = Logger.getLogger(DbFunctions.class.getName());
               logger.log(Level.SEVERE, "Error retrieving transactions", var17);
          }

          return transactions;
     }
}
