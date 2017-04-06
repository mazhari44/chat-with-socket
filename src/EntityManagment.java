import java.awt.Point;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntityManagment {

	public Connection connection() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = null;
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3322/chat?user=root&password=");

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;

	}

	public void addUser(User user) {

		try {
			PreparedStatement ps = connection()
					.prepareStatement(
							"INSERT INTO user(username,password)"
									+ " VALUES (?,?,?,?)");

			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, 0);
			ps.setInt(4, 0);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void deleteUser(String us) {
		try {

			PreparedStatement ps = connection().prepareStatement(
					"DELETE FROM userWHERE username like ?");

			ps.setString(1, us);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public String userExist(User user) {
		String message="";
		try {

			PreparedStatement ps = connection()
					.prepareStatement(
							"select * from user where username like ?");

			ps.setString(1, user.getUsername());
			//ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				if(rs.getString(2).equals(user.getPassword()))
					message= "USER TRUE";
				else message= "USER PASSWORD";
			}
			else
				message="USER USERNAME";

			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return message;

	}

	public User[] getOnlineIdleUser() {
		User[] users = null;
		try {

			Statement stat = connection().createStatement();
			ResultSet rs = stat
					.executeQuery("select count(*) from user where idle=1 and online=1");
			rs.next();
			int count = rs.getInt(1);
			PreparedStatement ps = connection().prepareStatement(
					"select * from user where idle=? and online=?");

			ps.setInt(1, 1);
			ps.setInt(2, 1);
			rs = ps.executeQuery();

			users = new User[count];
			
			int i = 0;
			while (rs.next()) {
				users[i]=new User();
				users[i].setUsername(rs.getString(1));
				users[i].setPassword(rs.getString(2));
				i++;
			}

			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return users;
	}

	public void setOnline(String username) {
		try {
			PreparedStatement ps = connection().prepareStatement(
					"UPDATE user SET online=? where username=?");

			ps.setInt(1, 1);
			ps.setString(2, username);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setIdle(String username) {
		try {
			PreparedStatement ps = connection().prepareStatement(
					"UPDATE user SET idle=? where username=?");

			ps.setInt(1, 1);
			ps.setString(2, username);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
