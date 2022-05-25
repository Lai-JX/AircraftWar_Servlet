package com.hitsz.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hitsz.dao.UserDao;

import com.hitsz.userdata.User;

public class RegisterServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("-----------------");
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password =  request.getParameter("password");
//        String phone =  request.getParameter("phone");
//        int age =  Integer.parseInt(request.getParameter("age"));


        User user = new User();

        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
//        user.setAge(age);
//        user.setPhone(phone);

        String msg = "";
        UserDao userDao = null;
        User uu = null;
        userDao = new UserDao();
        uu = userDao.findUser(user.getName());


        boolean flag = false;
        if(uu == null){
            flag = userDao.register(user);

        }


        if(flag){
            msg = "成功";
            System.out.println("注册成功");
        }else{
            msg = "失败";
            System.out.println("注册失败");
        }
        if(uu != null)
        {

            msg = "已存在";
            System.out.println("用户已存在");
        }

        PrintWriter out = response.getWriter();
        out.println(msg);
        out.flush();
        out.close();


    }


}



