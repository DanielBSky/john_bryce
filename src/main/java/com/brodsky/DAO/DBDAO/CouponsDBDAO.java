package com.brodsky.DAO.DBDAO;

import com.brodsky.DAO.CouponsDAO;
import com.brodsky.connectionPool.ConnectionPool;
import com.brodsky.javaBeans.Coupon;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class CouponsDBDAO implements CouponsDAO {

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public boolean isCouponExistsById(int id) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = String.format("SELECT COUNT(*) FROM COUPONS WHERE ID = %d;", id);

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
    public boolean isCouponIdExistsInThisCompany(int companyId, int couponId) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "SELECT COUNT(*) FROM COUPONS WHERE ID = ? AND COMPANY_ID = ?;";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, couponId);
            preparedStatement.setInt(2, companyId);

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

    public boolean isCouponTitleExistsInThisCompany(Coupon coupon) throws Exception {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "{call isCouponTitleExistsInThisCompany(?, ?)}";

            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, coupon.getTitle());
            callableStatement.setInt(2, coupon.getCompanyId());

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
    public void addCoupon(Coupon coupon) throws Exception{

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = String.format("INSERT INTO COUPONS " +
                    "(COMPANY_ID, CATEGORY_ID, TITLE, DESCRIPTION, START_DATE, END_DATE, " +
                    "AMOUNT, PRICE, IMAGE) " +
                    "VALUES (%d, %d, '%s', '%s', '%s', '%s', %d, '%.2f', '%s');",
                    coupon.getCompanyId(), coupon.getCategoryId(), coupon.getTitle(),
                    coupon.getDescription(), coupon.getStartDate().toString(),
                    coupon.getEndDate().toString(), coupon.getAmount(),
                    coupon.getPrice(), coupon.getImage());

            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);
            coupon.setId(id);
        }
        finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void updateCoupon(Coupon coupon) throws Exception{

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            String sql = String.format("UPDATE COUPONS SET " +
                    "CATEGORY_ID = %d, TITLE = '%s', " +
                    "DESCRIPTION = '%s', START_DATE = '%s', END_DATE = '%s', " +
                    "AMOUNT = %d, PRICE = %.2f, IMAGE = '%s' " +
                    "WHERE ID = %d;",
                    coupon.getCategoryId(), coupon.getTitle(),
                    coupon.getDescription(), coupon.getStartDate().toString(),
                    coupon.getEndDate().toString(), coupon.getAmount(),
                    coupon.getPrice(), coupon.getImage(), coupon.getId());

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }

    }

    @Override
    public void deleteCoupon(int couponID) throws Exception{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String sql = "DELETE FROM COUPONS WHERE ID = " + couponID + ";";
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate(sql);

        } finally {
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }

    }

    public Coupon getCompanyCoupon(int companyId, int CouponId) throws Exception{
        Coupon coupon;
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "{call getCompanyCoupon(?, ?)};";

            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, companyId);
            callableStatement.setInt(2, CouponId);

            resultSet = callableStatement.executeQuery();

            if(!resultSet.next()) {
                return null;
            }

            int id = resultSet.getInt("ID");
            int categoryId = resultSet.getInt("CATEGORY_ID");
            String title = resultSet.getString("TITLE");
            String description = resultSet.getString("DESCRIPTION");
            String startDate = resultSet.getString("START_DATE");
            String endDate = resultSet.getString("END_DATE");
            int amount = resultSet.getInt("AMOUNT");
            double price = resultSet.getDouble("PRICE");
            String image = resultSet.getString("IMAGE");

            coupon = new Coupon(id, companyId, categoryId, title,
                    description, LocalDate.parse(startDate),
                    LocalDate.parse(endDate), amount, price, image);

            return coupon;

        } finally {
            if (resultSet != null) resultSet.close();
            if (callableStatement != null) callableStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public ArrayList<Coupon> getCompanyCoupons(int companyID) throws Exception {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "{call getCompanyCoupons(?)}";

            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, companyID);
            resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int companyId = resultSet.getInt("COMPANY_ID");
                int categoryId = resultSet.getInt("CATEGORY_ID");
                String title = resultSet.getString("TITLE");
                String description = resultSet.getString("DESCRIPTION");
                String startDate = resultSet.getString("START_DATE");
                String endDate = resultSet.getString("END_DATE");
                int amount = resultSet.getInt("AMOUNT");
                double price = resultSet.getDouble("PRICE");
                String image = resultSet.getString("IMAGE");

                Coupon coupon = new Coupon(id, companyId, categoryId, title,
                        description, LocalDate.parse(startDate),
                        LocalDate.parse(endDate), amount, price, image);
                coupons.add(coupon);
            }

            return coupons;

        } finally {
            if (resultSet != null) resultSet.close();
            if (callableStatement != null) callableStatement.close();
            connectionPool.restoreConnection(connection);
        }

    }


    @Override
    public ArrayList<Coupon> getCompanyCouponsByCategory(int companyId, int categoryId) throws Exception {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "{call getCompanyCouponsByCategory(?, ?)}";

            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, companyId);
            callableStatement.setInt(2, categoryId);
            resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String title = resultSet.getString("TITLE");
                String description = resultSet.getString("DESCRIPTION");
                String startDate = resultSet.getString("START_DATE");
                String endDate = resultSet.getString("END_DATE");
                int amount = resultSet.getInt("AMOUNT");
                double price = resultSet.getDouble("PRICE");
                String image = resultSet.getString("IMAGE");

                Coupon coupon = new Coupon(id, companyId, categoryId, title,
                        description, LocalDate.parse(startDate),
                        LocalDate.parse(endDate), amount, price, image);
                coupons.add(coupon);
            }

            return coupons;

        } finally {
            if (resultSet != null) resultSet.close();
            if (callableStatement != null) callableStatement.close();
            connectionPool.restoreConnection(connection);
        }

    }


    @Override
    public ArrayList<Coupon> getAllCoupons() throws Exception{
        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "SELECT * FROM COUPONS";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int companyId = resultSet.getInt("COMPANY_ID");
                int categoryId = resultSet.getInt("CATEGORY_ID");
                String title = resultSet.getString("TITLE");
                String description = resultSet.getString("DESCRIPTION");
                String startDate = resultSet.getString("START_DATE");
                String endDate = resultSet.getString("END_DATE");
                int amount = resultSet.getInt("AMOUNT");
                double price = resultSet.getDouble("PRICE");
                String image = resultSet.getString("IMAGE");

                Coupon coupon = new Coupon(id, companyId, categoryId, title,
                        description, LocalDate.parse(startDate),
                        LocalDate.parse(endDate), amount, price, image);
                coupons.add(coupon);
            }

            return coupons;

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public Coupon getOneCoupon(int couponID) throws Exception{

        Coupon coupon = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "SELECT * FROM COUPONS WHERE ID = " + couponID;

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return null;
            }

            int id = resultSet.getInt("ID");
            int companyId = resultSet.getInt("COMPANY_ID");
            int categoryId = resultSet.getInt("CATEGORY_ID");
            String title = resultSet.getString("TITLE");
            String description = resultSet.getString("DESCRIPTION");
            String startDate = resultSet.getString("START_DATE");
            String endDate = resultSet.getString("END_DATE");
            int amount = resultSet.getInt("AMOUNT");
            double price = resultSet.getDouble("PRICE");
            String image = resultSet.getString("IMAGE");

            coupon = new Coupon(id, companyId, categoryId, title,
                description, LocalDate.parse(startDate),
                    LocalDate.parse(endDate), amount, price, image);

            return coupon;

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }

    }

    @Override
    public ArrayList<Coupon> getCustomerCoupons(int customerId) throws Exception{
        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "SELECT COUPONS.* FROM COUPONS " +
                    "JOIN CUSTOMERS_VS_COUPONS " +
                    "ON COUPONS.ID = CUSTOMERS_VS_COUPONS.COUPON_ID " +
                    "WHERE CUSTOMERS_VS_COUPONS.CUSTOMER_ID = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int companyId = resultSet.getInt("COMPANY_ID");
                int categoryId = resultSet.getInt("CATEGORY_ID");
                String title = resultSet.getString("TITLE");
                String description = resultSet.getString("DESCRIPTION");
                String startDate = resultSet.getString("START_DATE");
                String endDate = resultSet.getString("END_DATE");
                int amount = resultSet.getInt("AMOUNT");
                double price = resultSet.getDouble("PRICE");
                String image = resultSet.getString("IMAGE");

                Coupon coupon = new Coupon(id, companyId, categoryId, title,
                        description, LocalDate.parse(startDate),
                        LocalDate.parse(endDate), amount, price, image);
                coupons.add(coupon);
            }

            return coupons;

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }


//    @Override
//    public ArrayList<Coupon> getCustomerCouponsByCategory(int customerId) throws Exception{
//        ArrayList<Coupon> coupons = new ArrayList<>();
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = connectionPool.getConnection();
//
//            String sql = "SELECT COUPONS.* FROM COUPONS " +
//                    "JOIN CUSTOMERS_VS_COUPONS " +
//                    "ON COUPONS.ID = CUSTOMERS_VS_COUPONS.COUPON_ID " +
//                    "WHERE CUSTOMERS_VS_COUPONS.CUSTOMER_ID = ? AND COUPONS.CATEGORY_ID = ?";
//
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, customerId);
//            preparedStatement.setInt(2, customerId);
//            resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("ID");
//                int companyId = resultSet.getInt("COMPANY_ID");
//                int categoryId = resultSet.getInt("CATEGORY_ID");
//                String title = resultSet.getString("TITLE");
//                String description = resultSet.getString("DESCRIPTION");
//                String startDate = resultSet.getString("START_DATE");
//                String endDate = resultSet.getString("END_DATE");
//                int amount = resultSet.getInt("AMOUNT");
//                double price = resultSet.getDouble("PRICE");
//                String image = resultSet.getString("IMAGE");
//
//                Coupon coupon = new Coupon(id, companyId, categoryId, title,
//                        description, LocalDate.parse(startDate),
//                        LocalDate.parse(endDate), amount, price, image);
//                coupons.add(coupon);
//            }
//
//            return coupons;
//
//        } finally {
//            if (resultSet != null) resultSet.close();
//            if (preparedStatement != null) preparedStatement.close();
//            connectionPool.restoreConnection(connection);
//        }
//    }


    public boolean isPurchaseExists(int customerID, int couponID) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();
            String sql = "SELECT * FROM CUSTOMERS_VS_COUPONS " +
                    "WHERE CUSTOMER_ID = ? AND COUPON_ID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerID);
            preparedStatement.setInt(2, couponID);

            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return false;
            }

            return true;

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }

    }


    @Override
    public void addCouponPurchase(int customerID, int couponID) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();
            String sql = String.format(
                    "INSERT INTO CUSTOMERS_VS_COUPONS(CUSTOMER_ID, COUPON_ID) VALUES(%d, %d)",
                    customerID, couponID);

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        }

        finally {
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();
            String sql = String.format(
                    "DELETE FROM CUSTOMERS_VS_COUPONS WHERE CUSTOMER_ID=%d AND COUPON_ID=%d",
                    customerID, couponID);
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate();
        }

        finally {
            if (preparedStatement != null) preparedStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }


    private ArrayList<Coupon> getCouponsByCompanyIdentifications(int companyID, String storedProcedure) throws Exception {

        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            callableStatement = connection.prepareCall(storedProcedure);
            resultSet = callableStatement.executeQuery();

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
            if (callableStatement != null) callableStatement.close();
            connectionPool.restoreConnection(connection);
        }
    }
}
