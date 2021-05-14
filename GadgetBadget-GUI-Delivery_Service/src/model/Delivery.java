package model;

import java.sql.*; 

public class Delivery {
	
	public Connection connect()
	{
		Connection con = null; 
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/delivery-db?serverTimezone=UTC",
					"root", ""); 
			
			//For testing 
			System.out.print("Successfully connected"); 
		}	
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
		return con;
	}
	
	
	//insert
	public String insertDelivery(String fee, String date, String location, String time) 
	 { 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for inserting."; 
			 } 
		 	 	 // create a prepared statement
			 String query = " insert into delivery_gui"
						+ "(`deliveryId`,`deliveryFee`,`date`,`location`,`time`)"
						+ "  values (?, ?, ?, ?, ?)"; 
			 
			 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 					 
				 
				 // binding values

					preparedStmt.setDouble(2,  Double.parseDouble(fee));
					preparedStmt.setString(3, date); 
					preparedStmt.setString(4, location); 
					preparedStmt.setString(5, time);
				 				 
				 preparedStmt.execute(); 
				 con.close(); 
				 
				 
				 String newDelivery = readDeliverys(); 
				 output = "{\"status\":\"success\", \"data\": \"" + newDelivery+ "\"}";
				 //output = "Inserted successfully"; 
		 } 
		 catch (Exception e) 
		 { 
			 //output = "Error while inserting the Delivery.";
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the order.\"}";
			 System.err.println(e.getMessage());
			 System.out.println(e.getMessage());
				System.out.println(e);
				e.printStackTrace();
			
		 } 
	 	return output; 
	 } 
	
	
	//read
	public String readDeliverys()
	{
		 String output = ""; 

		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for reading."; 
			 } 
			 
			 // Prepare the html table to be displayed
			 output = "<table border='1'>"+ "<tr><th>Delivery ID</th>" + 
			 "<th>Delivery Fee</th>" + 
			 "<th>Date</th>" + 
			 "<th>Location</th>" + 
			 "<th>Time</th>" +
			
			 "<th>Update</th><th>Remove</th></tr>"; 
		 
			 
			 String query = "select * from delivery_gui";
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
				 String deliveryId = Integer.toString(rs.getInt("deliveryId")); 
					String deliveryFee = Double.toString(rs.getDouble("deliveryFee"));
					String date = Integer.toString(rs.getInt("date"));
					String location = rs.getString("location");
					String time = rs.getString("time"); 
				 
					// Add a row into the html table 
					output += "<tr><td>" + deliveryFee + "</td>"; 
					output += "<td>" + date + "</td>"; 
					output += "<td>" + location + "</td>"; 
					output += "<td>" + time + "</td>"; 				 
				 
				 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						 + "<td><button class='btnRemove btn btn-danger' name='btnRemove' id ='btnRemove' value='"+ deliveryId +"' >Remove</button></td></tr>";
			 } 
			 	 con.close(); 
			 	 // Complete the html table
			 	 output += "</table>"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "Error while reading the delivery"; 
			 System.err.println(e.getMessage()); 
		 } 
	 	 return output; 
	 } 
		
	
	
	/////////////////
	public String updateDelivery(String id, String fee, String date, String location, String time)
	{
		
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for updating."; 
			 } 
			 
			 // create a prepared statement
			 String query = "UPDATE deliverys SET deliveryFee=?, date=?, location=?, time=? WHERE deliveryId=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			//binding values
				preparedStmt.setDouble(1, Double.parseDouble(fee));
				preparedStmt.setInt(2, Integer.parseInt(date));
				preparedStmt.setString(3, location);
				preparedStmt.setString(4, time);
				preparedStmt.setInt(5, Integer.parseInt(id));
			 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 //output = "Updated successfully"; 
			 String newOrder = readDeliverys(); 
			 output = "{\"status\":\"success\", \"data\": \"" + newOrder + "\"}"; 

		 } 
		 catch (Exception e) 
		 { 
			 //output = "Error while updating the delivery."; 
			 output = "{\"status\":\"error\", \"data\": \"Error while updating the delivery.\"}"; 
			 System.err.println(e.getMessage()); 
			 System.out.println(e);
		 } 
		 	return output; 
		 } 
		

		
		
		
		//////////////////////////////////
		public String deleteDelivery(String deliveryId)
		{
			 String output = ""; 
			 try
			 { 
				 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for deleting."; 
			 } 
			 
			 	 // create a prepared statement
				 String query = "delete from delivery where deliveryId=?";
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setInt(1, Integer.parseInt(deliveryId)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 //output = "Deleted successfully"; 
				 String newDelivery = readDeliverys(); output = "{\"status\":\"success\", \"data\": \"" + newDelivery + "\"}";

			 } 
			 catch (Exception e) 
			 { 
				 //output = "Error while deleting the delivery."; 
				 output = "{\"status\":\"error\", \"data\": \"Error while deleting the delivery.\"}"; 
				 System.err.println(e.getMessage()); 
				 System.out.println(e);
			 } 
			 return output;
			 }

}
