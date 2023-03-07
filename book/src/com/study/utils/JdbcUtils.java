package com.study.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
    static {

        try {
            Properties properties = new Properties();
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");

            properties.load(inputStream);
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接池的连接
     * @return 返回null就是连接失败，有值就是成功
     */

    public static Connection getConnection(){
        Connection conn = conns.get();
        if (conn == null){
            try {
                conn = dataSource.getConnection();//从数据库连接池中获取连接
                //保存到ThreadLocal对象中，供后面jdbc使用
                conns.set(conn);
                //设置为手动管理
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 提交事物，并关闭释放连接
     */
    public static void commitAndClose(){
        Connection connection = conns.get();
        if (connection != null){//如果不等于空说明之前使用国数据块
            try {
                connection.commit();//提交事物

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();//关闭连接释放资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //一定要执行remove操作，否则就会出错(因为Tomcat服务器底层使用了线程池)
                conns.remove();
            }
        }
    }
    /**
     * 回滚事物，并关闭释放连接
     */
    public static void rollbackAndClose(){
        Connection connection = conns.get();
        if (connection != null){//如果不等于空说明之前使用国数据块
            try {
                connection.rollback();//回滚事物

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();//关闭连接释放资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //一定要执行remove操作，否则就会出错(因为Tomcat服务器底层使用了线程池)
                conns.remove();
            }
        }
    }

//    public static void close(Connection connection){
//        if (connection != null){
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }



}
