	package com.CheatSheet.Repository;
	
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	
	import org.mindrot.jbcrypt.BCrypt;
	
	import com.CheatSheet.Config.DBConnect;
	import com.CheatSheet.Model.RegisterLoginBean;
	
	public class UserLoginRepository {
	
		Connection con = null;
	
			public RegisterLoginBean loginUser(String username, String password) throws SQLException {
		        RegisterLoginBean user = null;
		        String sql = "SELECT * FROM users WHERE username = ?";

		        // Try-with-resources ကိုသုံးပြီး Connection ကို Method ထဲမှာပဲ တိုက်ရိုက်ယူပါ
		        try (Connection con = DBConnect.getConnection()) {
		            
		            // Connection မရှိလျှင် Error ပြရန် (Null Check)
		            if (con == null) {
		                throw new SQLException("Database Connection is null. Please check DBConnect settings.");
		            }

		            try (PreparedStatement ps = con.prepareStatement(sql)) {
		                ps.setString(1, username);
		                
		                try (ResultSet rs = ps.executeQuery()) {
		                    if (rs.next()) {
		                        String storedHashedPassword = rs.getString("password");
		                        
		                        // BCrypt Verify လုပ်ခြင်း
		                        if (BCrypt.checkpw(password, storedHashedPassword)) {
		                            user = new RegisterLoginBean();
		                            user.setId(rs.getInt("id"));
		                            user.setUsername(rs.getString("username"));
		                            user.setEmail(rs.getString("email"));
		                            user.setEnabled(rs.getInt("enabled"));
		                            user.setRoleName(rs.getString("roles")); // DB ထဲက Column အမည်အတိုင်း ဖြစ်ပါစေ
		                        }
		                    }
		                }
		            }
		        } catch (SQLException e) {
		            System.err.println("Login Repository Error: " + e.getMessage());
		            throw e;
		        }
		        return user;
		    }
	}
