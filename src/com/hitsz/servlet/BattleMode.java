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

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();


        String result = "";


        System.out.println(request.getMethod());
//        String password = request.getParameter("password");
//        String name = request.getParameter("name");
        String mode = request.getParameter("mode");
        String username = request.getParameter("username");
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String life = request.getParameter("life");
        String score = request.getParameter("score");
        System.out.println("mode="+mode + " username=" + username);

        UserDao dao = new UserDao();
        switch (action){
            case "match":
                System.out.println("寻求匹配");
                String isWait = dao.battleState(mode,username,id);
                System.out.println("isWait="+isWait);

                result = isWait;
                break;
            case "cancel":
                System.out.println("取消匹配");
                boolean cancelSuccess = dao.battleCancel(username,"id");
                if(cancelSuccess){
                    result = "Cancel Success!";
                }
                break;
            case "delete":
                System.out.println("退出");
                boolean deleteSuccess = dao.battleCancel(username,"id");
                if(deleteSuccess){
                    result = "Delete Success!";
                }
                break;
            case "score":
                System.out.println("同步得分");
                result = dao.syncScore(username,id,score,life);
                break;
            default:
                break;
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
