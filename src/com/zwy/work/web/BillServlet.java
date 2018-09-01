package com.zwy.work.web;

import com.zwy.work.dao.BillDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BillServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getAttribute("path").toString();
        //根据规范做出判断处理
        System.out.println(path);
        switch (path) {
            case "/findBills.do":
                findBills(req, res);
                break;
            default:
                throw new RuntimeException("查无此页面");
        }
    }

    private void findBills(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BillDao dao=new BillDao();
        dao.findBills();
    }
}
