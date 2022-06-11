package com.hitsz.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hitsz.userdata.User;
import com.sun.xml.internal.ws.model.soap.SOAPBindingImpl;

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

    public String battleState(String mode,String user,String id){
        String sql = "select * from battlestate where mode = "+"'"+mode+ "'" + " and user1 is not null and user2 is null ";//+ " && user2 = null "
        System.out.println("user="+user+" id="+id);
        Connection  con = JDBCUtils.getConn();

        try {
            PreparedStatement pst=con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
//            System.out.println("rs.next()="+rs.next());
            // 若已有一个用户在等待
            while(rs.next()){
                System.out.println("user1="+rs.getString(3));
                //
                if(!user.equals(rs.getString(3))){
                    sql = " update battlestate set user2 = "+ "'"+user+"'" +" where user2 is null  ";//+id;//+mode+"'"+" & user2 is null ";//"update battlestate set user2 = "+"user"+" user2 = null "
                    con.prepareStatement(sql).executeUpdate();
                    return rs.getString(1)+"&Success";
//                    return "match";// id
                }
                // 等待用户和发送请求的用户是同一个人
                else{
                    return id+"&waiting";
                }
            }
            // 已有两个用户
            rs = pst.executeQuery("select * from battlestate where mode = "+"'"+mode+ "'" + " and user2 is not null and user1 is not null and id=  "+id);
            if(rs.next()){
                return id+"&Success";// 匹配成功
            }
            // 无用户在等待
            rs = pst.executeQuery("select * from battlestate");
            rs.last();
            int count = rs.getRow();
            System.out.println("count="+count);
            sql = "insert into battlestate(id,mode,user1) values (?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1,new Integer(count+1).toString());
            pst.setString(2,mode);
            pst.setString(3,user);
            int value = pst.executeUpdate();
            if(value>0){
                return (count+1) + "&waiting";// 返回id
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
        return id+"&err";
//        return false;
    }

    public boolean battleCancel(String user,String id){
        System.out.println(id);
        Connection  con = JDBCUtils.getConn();
        try {
//            con.prepareStatement("delete from battlestate where id = " + id).execute();
            con.prepareStatement("delete from battlestate where id = "+id+" user1 = " + user + " and user2 is null").execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String syncScore(String username,String id,String score,String life){

        String sql = "select * from users where id = "+id+" & user1 = "+ username;
        Connection  con = JDBCUtils.getConn();

        try {
            PreparedStatement pst=con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                // 写入自身数据
                sql = " update battlestate set user1score = "+ score +" ,user1life = "+life+" where id =  "+id;
                con.prepareStatement(sql).executeUpdate();
                // 获取对手数据
                sql = "select * from battlestate where id = "+id+" & user1 = "+ username;
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    return rs.getString(6)+"&"+rs.getString(9);
                }
            }
            else {
                // 写入自身数据
                sql = " update battlestate set user2score = "+ score +" ,user2life = "+life+" where id =  "+id;
                con.prepareStatement(sql).executeUpdate();
                // 获取对手数据
                sql = "select * from battlestate where id = "+id+" & user2 = "+ username;
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    return rs.getString(5)+"&"+rs.getString(8);
                }
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return null;
    }
}


