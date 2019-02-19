package com.capg.repo;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.capg.beans.Customer;

public interface WalletRepo {
	public boolean save(Customer customer) throws ClassNotFoundException, SQLException;
	public Customer findOne(String mobileNo) throws SQLException, ClassNotFoundException;
	public Customer fundTransfer(String sMobileNo,String tMobileNo,BigDecimal amount) throws ClassNotFoundException, SQLException;
}
