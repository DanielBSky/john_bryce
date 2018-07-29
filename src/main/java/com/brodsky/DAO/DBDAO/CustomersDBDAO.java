package com.brodsky.DAO.DBDAO;

import com.brodsky.DAO.CustomersDAO;
import com.brodsky.connectionPool.ConnectionPool;
import com.brodsky.javaBeans.Coupon;
import com.brodsky.javaBeans.Customer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomersDBDAO implements CustomersDAO {

    private ConnectionPool connectionPool = ConnectionPool.getInstance();


    @Override
    public int getCustomerId(String email, String password) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "SELECT ID FROM CUSTOMERS WHERE EMAIL = ? AND " +
                    "PASSWORD = ?;";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt(1);
            }

            return 0;
        }
        finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public boolean isCustomerExists(String email, String password) throws Exception{

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = String.format("SELECT * FROM CUSTOMERS WHERE EMAIL = '%s' AND " +
                    "PASSWORD = '%s';", email, password);

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return true;
            }

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
        return false;
    }

    @Override
    public boolean isCustomerExistsById(int id) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = String.format("SELECT COUNT(*) FROM CUSTOMERS WHERE ID = %d;", id);

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) == 1;
        }

        finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public boolean isCustomerEmailExists(String email) throws Exception{
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "{call isCustomerEmailExists(?)}";

            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, email);


            resultSet = callableStatement.executeQuery();

            resultSet.next();
            return resultSet.getInt(1) == 1;

        }

        finally {
            if (resultSet != null) resultSet.close();
            if (callableStatement != null) callableStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }


    @Override
    public void addCustomer(Customer customer) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = String.format("INSERT INTO CUSTOMERS " +
                    "(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) " +
                    "values ('%s', '%s', '%s', '%s');",
                    customer.getFirstName(), customer.getLastName(),
                    customer.getEmail(), customer.getPassword());

            connection = connectionPool.getConnection();

            preparedStatement =
                    connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            resultSet.next();
            int id = resultSet.getInt(1);
            customer.setId(id);

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws Exception{

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String sql = String.format("UPDATE CUSTOMERS SET " +
                    "FIRST_NAME = '%s', LAST_NAME = '%s', EMAIL = '%s', " +
                    "PASSWORD = '%s' WHERE ID = %d;",
                    customer.getFirstName(), customer.getLastName(),
                    customer.getEmail(), customer.getPassword(), customer.getId());

            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void deleteCustomer(int customerID) throws Exception{

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String sql = "DELETE FROM CUSTOMERS WHERE ID = " + customerID + ";";

            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public ArrayList<Customer> getAllCustomers() throws Exception{

        ArrayList<Customer> customers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "SELECT * FROM CUSTOMERS";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String firstName = resultSet.getString("FIRST_NAME");
                String lastName = resultSet.getString("LAST_NAME");
                String email = resultSet.getString("EMAIL");
                String password = resultSet.getString("PASSWORD");

                ArrayList<Coupon> coupons = getCouponsByCustomerId(id);

                Customer customer = new Customer(id, firstName, lastName,
                        email, password, coupons);
                customers.add(customer);
            }

            return customers;

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public Customer getOneCustomer(int customerId) throws Exception{
        Customer customer;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "SELECT * FROM CUSTOMERS WHERE ID = " + customerId;

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();


            if(!resultSet.next()) {
                return null;
            }

            int id = resultSet.getInt("ID");
            String firstName = resultSet.getString("FIRST_NAME");
            String lastName = resultSet.getString("LAST_NAME");
            String email = resultSet.getString("EMAIL");
            String password = resultSet.getString("PASSWORD");

            ArrayList<Coupon> coupons = getCouponsByCustomerId(id);

            customer = new Customer(id, firstName, lastName,
                    email, password, coupons);

            return customer;

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    private ArrayList<Coupon> getCouponsByCustomerId(int customerID) throws Exception {

        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = String.format("SELECT * FROM COUPONS JOIN CUSTOMERS_VS_COUPONS" +
                    " ON COUPONS.ID = CUSTOMERS_VS_COUPONS.COUPON_ID " +
                    "WHERE CUSTOMER_ID = %d", customerID);

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int companyID = resultSet.getInt("COMPANY_ID");
                int categoryID = resultSet.getInt("CATEGORY_ID");
                String title = resultSet.getString("TITLE");
                String description = resultSet.getString("DESCRIPTION");
                LocalDate startDate = resultSet.getDate("START_DATE").toLocalDate();
                LocalDate endDate = resultSet.getDate("END_DATE").toLocalDate();
                int amount = resultSet.getInt("AMOUNT");
                double email = resultSet.getDouble("PRICE");
                String image = resultSet.getString("IMAGE");

                Coupon coupon = new Coupon(id, companyID, categoryID, title, description,
                        startDate, endDate, amount, email, image);
                coupons.add(coupon);
            }

            return coupons;

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }
}
