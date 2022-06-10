package com.hitsz.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hitsz.userdata.User;

public class UserDao {


    public boolean login(String name,String password){

        String sql = "select * from users where name = ? and password = ?";

        Connection  con = JDBCUtils.getConn();

        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,name);
            pst.setString(2,password);

            if(pst.executeQuery().next()){

                return true;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }

        return false;
    }

    public boolean register(User user){

//        String sql = "insert into users(name,username,password,age,phone) values (?,?,?,?,?)";
        String sql = "insert into users(name,username,password) values (?,?,?)";
        Connection  con = JDBCUtils.getConn();

        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,user.getName());
            pst.setString(2,user.getUsername());
            pst.setString(3,user.getPassword());
//            pst.setInt(4,user.getAge());
//            pst.setString(5,user.getPhone());

            int value = pst.executeUpdate();

            if(value>0){
                return true;
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return false;
    }

    public User findUser(String name){

        String sql = "select * from users where name = ?";

        Connection  con = JDBCUtils.getConn();
        User user = null;
        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,name);

            ResultSet rs = pst.executeQuery();

            while (rs.next()){

                int id = rs.getInt(1);
                String namedb = rs.getString(2);
                String username = rs.getString(3);
                String passworddb  = rs.getString(4);
                int age = rs.getInt(5);
                String phone = rs.getString(6);
                user = new User(id,namedb,username,passworddb,age,phone);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }

        return user;
    }

    public String battleState(String mode,String user){
        String sql = "select * from battlestate where mode = "+"'"+mode+ "'" + " && user2 = null ";

        Connection  con = JDBCUtils.getConn();

        try {
            PreparedStatement pst=con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            // 若已有用户等待，添加用户2
            while(rs.next()){
                if(!user.equals(rs.getString(3))){
                    sql = "select battlestate update set user2 = "+"user"+" user2 = null ";//"update battlestate set user2 = "+"user"+" user2 = null "
                    con.prepareStatement(sql).executeUpdate();
                    return new Integer(rs.getInt(1)).toString();
//                    return "match";// id
                }
            }
            // 无用户在等待
            sql = "insert into battlestate(id,mode,user1) values (?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(2,mode);
            pst.setString(3,user);
            int value = pst.executeUpdate();
            if(value>0){
                return "waiting";
            }


//            boolean isWait = rs.getBoolean(2);
//            String user1 = rs.getString(3);
//            String user2 = rs.getString(4);
//
//            if(user1.equals(null)){
//                pst.setString(3,name);
//            }else if(user2.equals(null)){
//                pst.setString(4,name);
//            }
//            pst.setBoolean(2,!isWait);
//            return isWait;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return "err";
//        return false;
    }


}


