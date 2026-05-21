package com.CheatSheet.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.CheatSheet.Config.DBConnect;
import com.CheatSheet.Model.RegisterLoginBean;

public class RegisterLoginRepository {

    public RegisterLoginBean validateUser(String username, String email, String password) throws SQLException {
        RegisterLoginBean user = null;
        
        // 1. Updated SQL to match your table columns: id, username, email, password, enabled, roles
        String userSql = "INSERT INTO users (username, email, password, enabled, roles) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = DBConnect.getConnection()) {
            // 2. Start a transaction
            con.setAutoCommit(false);

            int userId = -1;
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            
            // 3. Insert User and get the generated ID
            try (PreparedStatement ps = con.prepareStatement(userSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, hashedPassword);
                ps.setInt(4, 1);       // enabled = 1
                ps.setString(5, "User"); // Default role assigned directly to the users table
                
                ps.executeUpdate();

                // Retrieve the auto-generated ID for the user
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        userId = rs.getInt(1);
                    }
                }
            }

            // 4. Commit if successful
            if (userId != -1) {
                user = new RegisterLoginBean();
                user.setId(userId);
                user.setUsername(username);
                user.setEmail(email);
                user.setEnabled(1);
                user.setRoleName("User");
                
                con.commit();
                System.out.println("User registered successfully!");
            } else {
                con.rollback();
            }

        } catch (SQLException e) {
            System.out.println("Registration Error: " + e.getMessage());
            throw e;
        }
        return user;
    }
}