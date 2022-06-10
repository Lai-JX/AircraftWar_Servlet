package com.hitsz.servlet;

import com.hitsz.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BattleMode extends HttpServlet {

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // TODO Auto-generated method stub

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String mode = request.getParameter("mode");
        String wait = request.getParameter("wait");

        System.out.println("mode="+mode);
        System.out.println("wait="+wait);

        String result = "";
//        boolean isExit = false;
//
//        if("admin".equals(username) && "123456".equals(userpwd)){
//            System.out.println("Login success");
//            isExit = true;
//        }
//        if(isExit){
//            result = "success";
//        }else {
//            result = "failed";
//        }

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
        System.out.println("寻求匹配");
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();


        String result = "";


        System.out.println(request.getMethod());
//        String password = request.getParameter("password");
//        String name = request.getParameter("name");
        String mode = request.getParameter("mode");
        String user = request.getParameter("user");
        System.out.println("mode="+mode + " user=" + user);

        UserDao dao = new UserDao();
        String isWait = dao.battleState(mode,user);
        System.out.println("isWait="+isWait);

        if("waiting".equals(isWait)){
            System.out.println("有对手正在等待匹配");
            result = "success";
        }else {
            // 匹配成功，返回id
            result = isWait;
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
