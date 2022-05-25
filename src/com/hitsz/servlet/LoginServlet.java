package com.hitsz.servlet;

import com.hitsz.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // TODO Auto-generated method stub
//        System.out.println("ll");
//        response.getWriter().append("Served at: ").append(request.getContextPath());

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String userpwd = request.getParameter("password");

        System.out.println("username="+username);
        System.out.println("password="+userpwd);

        String result = "";
        boolean isExit = false;

        if("admin".equals(username) && "123456".equals(userpwd)){
            System.out.println("Login success");
            isExit = true;
        }
        if(isExit){
            result = "success";
        }else {
            result = "failed";
        }

        out.write(result);
        out.flush();
        out.close();
        System.out.println(result+"\n");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();


        String result = "";


        System.out.println(request.getMethod());
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        System.out.println("name="+name + " password=" + password);

        UserDao dao = new UserDao();
        boolean login = dao.login(name,password);
        System.out.println("login="+login);

        if(login){
            System.out.println("Login success");
            result = "success";
        }else {
            result = "failed";
        }

        out.write(result);
        out.flush();
        out.close();
        System.out.println(result+"\n");


//        request.setCharacterEncoding("utf-8");
//        InputStream iStream = request.getInputStream();
//        int len = request.getContentLength();
//        byte[] bs = new byte[len];
//        iStream.read(bs);
//        System.out.println("获取的json数据：" + new String(bs,"utf-8"));


//        doGet(request, response);
    }
}
