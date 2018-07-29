package com.brodsky.DAO.DBDAO;

import com.brodsky.DAO.CompaniesDAO;
import com.brodsky.connectionPool.ConnectionPool;
import com.brodsky.javaBeans.Company;
import com.brodsky.javaBeans.Coupon;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class CompaniesDBDAO implements CompaniesDAO {

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public int getCompanyId(String email, String password) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "SELECT ID FROM COMPANIES WHERE EMAIL = ? AND " +
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
    public boolean isCompanyExists(String email, String password) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = String.format("SELECT COUNT(*) FROM COMPANIES WHERE EMAIL = '%s' AND " +
                    "PASSWORD = '%s';", email, password);

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
    public boolean isCompanyExistsById(int id) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = String.format("SELECT COUNT(*) FROM COMPANIES WHERE ID = %d;", id);

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

    public boolean isCompanyEmailExists(String email) throws Exception{
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "{call isCompanyEmailExists(?)}";

            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, email);


            resultSet = callableStatement.executeQuery();

            resultSet.next();
            return resultSet.getInt(1) == 1;
        } finally {
            if (resultSet != null) resultSet.close();
            if (callableStatement != null) callableStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    public boolean isCompanyNameExists(String name) throws Exception{
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "{call isCompanyNameExists(?)}";

            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, name);


            resultSet = callableStatement.executeQuery();

            resultSet.next();
            return resultSet.getInt(1) == 1;

        } finally {
            if (resultSet != null) resultSet.close();
            if (callableStatement != null) callableStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void addCompany(Company company) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = String.format("INSERT INTO COMPANIES (NAME, EMAIL, PASSWORD)" +
                    " values ('%s', '%s', '%s');",
                    company.getName(), company.getEmail(), company.getPassword());

            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            resultSet.next();
            int id = resultSet.getInt(1);
            company.setId(id);
         }
        finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void updateCompany(Company company) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            String sql = String.format("UPDATE COMPANIES SET " +
                    "NAME = '%s', EMAIL = '%s', PASSWORD = '%s' " +
                    "WHERE ID = %d;",
                    company.getName(), company.getEmail(), company.getPassword(),
                    company.getId());

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }


    @Override

    public void updateCompanyEmailAndPassword
            (Company company) throws Exception {
        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "{call updateCompanyEmailAndPassword(?, ?, ?)}";

            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, company.getName());
            callableStatement.setString(2, company.getEmail());
            callableStatement.setString(3, company.getPassword());

            callableStatement.executeQuery();

        } finally {
            if (callableStatement != null) callableStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override

    public void updateCompanyEmail(String email, String name) throws Exception {
        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "{call updateCompanyEmail(?, ?)}";

            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, email);
            callableStatement.setString(2, name);

            callableStatement.executeQuery();

        } finally {
            if (callableStatement != null) callableStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    public void updateCompanyPassword(String password, String name) throws Exception {
        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "{call updateCompanyPassword(?, ?)}";

            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, password);
            callableStatement.setString(2, name);

            callableStatement.executeQuery();

        } finally {
            if (callableStatement != null) callableStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void deleteCompany(int companyID) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "DELETE FROM COMPANIES WHERE ID = " + companyID + ";";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }


    @Override
    public ArrayList<Company> getAllCompanies() throws Exception {

        ArrayList<Company> companies = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "SELECT * FROM COMPANIES";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                String email = resultSet.getString("EMAIL");
                String password = resultSet.getString("PASSWORD");

                ArrayList<Coupon> coupons = getCouponsByCompanyId(id);

                Company company = new Company(id, name, email, password, coupons);
                companies.add(company);
            }

            return companies;

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }

    }

    @Override
    public Company getOneCompany(int companyID) throws Exception {

        Company company = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "SELECT * FROM COMPANIES WHERE ID = " + companyID;

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return null;
            }

            int id = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");
            String email = resultSet.getString("EMAIL");
            String password = resultSet.getString("PASSWORD");

            ArrayList<Coupon> coupons = getCouponsByCompanyId(id);

            company = new Company(id, name, email, password, coupons);

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }

        return company;
    }

    @Override
    public void getOneCompanyByName(Company company) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "SELECT * FROM COMPANIES WHERE NAME = ?;";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, company.getName());
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return;
            }

            int id = resultSet.getInt("ID");
            String email = resultSet.getString("EMAIL");
            String password = resultSet.getString("PASSWORD");

            company.setId(id);
            company.setEmail(email);
            company.setPassword(password);
            company.setCoupons(getCouponsByCompanyId(id));

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }


    public ArrayList<Company> getAllCompaniesStoredProcedure() throws Exception {
        ArrayList<Company> companies = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "{call getAllCompanies()}";

            callableStatement = connection.prepareCall(sql);
            resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                String email = resultSet.getString("EMAIL");
                String password = resultSet.getString("PASSWORD");

                ArrayList<Coupon> coupons = getCouponsByCompanyId(id);

                Company company = new Company(id, name, email, password, coupons);
                companies.add(company);
            }

            return companies;

        } finally {
            if (resultSet != null) resultSet.close();
            if (callableStatement != null) callableStatement.close();
            connectionPool.restoreConnection(connection);
        }

    }

    private ArrayList<Coupon> getCouponsByCompanyId(int companyID) throws Exception {

        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = String.format("SELECT * FROM COUPONS WHERE COMPANY_ID = %d", companyID);

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
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