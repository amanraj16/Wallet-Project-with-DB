package com.capg.repo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.capg.beans.Customer;
import com.capg.beans.Wallet;
import com.capg.connection.EstablishConnection;
import com.capg.beans.*;
import com.capg.beans.Customer;
public class WalletRepoImpl implements WalletRepo {
	EstablishConnection establishConnection=new EstablishConnection();
	@Override
	public boolean save(Customer customer) throws ClassNotFoundException, SQLException {
		Connection connection=establishConnection.getConnection();
		System.out.println("Connecting");
		PreparedStatement pstmt=connection.prepareStatement("insert into customer(cust_name,mobileNo,wallet) values(?,?,?)");
		pstmt.setString(1,customer.getName());
		pstmt.setString(2,customer.getMobileNo());
		pstmt.setInt(3,customer.getWallet().getBalance().intValue());
		int n=0;
		n=pstmt.executeUpdate();
		if(n>=1)
			return true;
		else
			return false;
	}

	@Override
	public Customer findOne(String mobileNo) throws SQLException, ClassNotFoundException {
		Connection connection=establishConnection.getConnection();
		Statement stmt=connection.createStatement();
		ResultSet rs=stmt.executeQuery("select * from customer");
		while(rs.next())
		{
			if(rs.getString(2).equals(mobileNo))
			{
				BigDecimal bigDecimal=new BigDecimal(rs.getString(3));
				Wallet wallet=new Wallet(bigDecimal);
				Customer cust=new Customer(rs.getString(1),rs.getString(2),wallet);
				return cust;
			}
		}
		return null;
	}

	@Override
	public Customer fundTransfer(String sMobileNo, String tMobileNo, BigDecimal amount)
			throws ClassNotFoundException, SQLException {
		Connection connection=establishConnection.getConnection();
		PreparedStatement pstmt=connection.prepareStatement("update customer set wallet=? where mobileno=?");
		pstmt.setInt(1,findOne(sMobileNo).getWallet().getBalance().subtract(amount).intValue());
		pstmt.setString(2,sMobileNo);
		pstmt.executeUpdate();
		PreparedStatement pstmt1=connection.prepareStatement("update customer set wallet=? where mobileno=?");
		pstmt1.setInt(1,findOne(tMobileNo).getWallet().getBalance().add(amount).intValue());
		pstmt1.setString(2,tMobileNo);
		pstmt1.executeUpdate();
		return findOne(sMobileNo);
	}
	public Customer withdrawAmount(String mobileno,BigDecimal amount) throws ClassNotFoundException, SQLException
	{
		Connection connection=establishConnection.getConnection();
		PreparedStatement pstmt=connection.prepareStatement("update customer set wallet=? where mobileno=?");
		pstmt.setInt(1,findOne(mobileno).getWallet().getBalance().subtract(amount).intValue());
		pstmt.setString(2,mobileno);
		pstmt.executeUpdate();
		return findOne(mobileno);
	}
	public Customer depositAmount(String mobileno,BigDecimal amount) throws ClassNotFoundException, SQLException
	{
		Connection connection=establishConnection.getConnection();
		PreparedStatement pstmt=connection.prepareStatement("update customer set wallet=? where mobileno=?");
		pstmt.setInt(1,findOne(mobileno).getWallet().getBalance().add(amount).intValue());
		pstmt.setString(2,mobileno);
		pstmt.executeUpdate();
		return findOne(mobileno);
	}

}
