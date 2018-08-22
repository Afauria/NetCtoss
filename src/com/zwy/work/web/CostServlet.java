package com.zwy.work.web;

import com.zwy.work.dao.CostDao;
import com.zwy.work.entity.Cost;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CostServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getAttribute("path").toString();
        //根据规范做出判断处理
        System.out.println(path);
        switch (path) {
            case "/findCosts.do":
                findCosts(req, res);
                break;
            case "/toAddCost.do":
                toAddCost(req, res);
                break;
            case "/addCost.do":
                addCost(req, res);
                break;
            case "/toUpdateCost.do":
                toUpdateCost(req, res);
                break;
            case "/updateCost.do":
                updateCost(req, res);
                break;
            case "/costDetail.do":
                findCostById(req, res);
                break;
            default:
                throw new RuntimeException("查无此页面");
        }
    }

    //查询资费信息
    private void findCosts(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //查询所有的资费
        CostDao dao = new CostDao();
        List<Cost> costs = dao.findCost();
        //讲数据保存到HttpServletRequest对象上,并转发到JSP
        req.setAttribute("costs", costs);
        //当前:netctoss/findCosts.do
        //目标:netctoss/WEB-INF/cost/find.jsp
        req.getRequestDispatcher("WEB-INF/cost/find.jsp").forward(req, res);
    }

    private void findCostById(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        CostDao dao = new CostDao();
        Cost cost = dao.findById(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("cost", cost);
        req.getRequestDispatcher("WEB-INF/cost/fee_detail.jsp").forward(req, res);
    }

    //去添加资费的页面
    private void toAddCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //当前:netctoss/toAddCost.do
        //目标:netctoss/WEB-INF/cost/add.jsp
        req.getRequestDispatcher("WEB-INF/cost/add.jsp").forward(req, res);
    }

    //添加资费
    private void addCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //接受表单提交的数据
        String name = req.getParameter("name");
        String costType = req.getParameter("costType");
        String baseDuration = req.getParameter("baseDuration");
        String baseCost = req.getParameter("baseCost");
        String unitCost = req.getParameter("unitCost");
        String descr = req.getParameter("descr");
        //封装
        Cost c = new Cost();
        c.setName(name);
        c.setCostType(costType);
        if (baseDuration != null && !baseDuration.equals("")) {
            c.setBaseDuration(Integer.parseInt(baseDuration));
        }
        if (baseCost != null && !baseCost.equals("")) {
            c.setBaseCost(Double.parseDouble(baseCost));
        }
        if (unitCost != null && !unitCost.equals("")) {
            c.setUnitCost(Double.parseDouble(unitCost));
        }
        c.setDescr(descr);
        CostDao dao = new CostDao();
        dao.save(c);
        //重定向到资费查询页面
        //当前:/netctoss/addCost.do
        //目标:/netctoss/findCosts.do
        res.sendRedirect("findCosts.do");
    }


    //打开资费修改页面
    private void toUpdateCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        CostDao dao = new CostDao();
        Cost cost = dao.findById(id);
        req.setAttribute("cost", cost);
        req.getRequestDispatcher("WEB-INF/cost/update.jsp").forward(req, res);
    }

    private void updateCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("costId"));
        //接受表单提交的数据
        String name = req.getParameter("name");
        String costType = req.getParameter("radFeeType");
        String baseDuration = req.getParameter("baseDuration");
        String baseCost = req.getParameter("baseCost");
        String unitCost = req.getParameter("unitCost");
        String descr = req.getParameter("descr");
        Cost c = new Cost();
        c.setCostId(id);
        c.setName(name);
        c.setCostType(costType);
        c.setBaseDuration(Integer.parseInt(baseDuration));
        c.setBaseCost(Double.parseDouble(baseCost));
        c.setUnitCost(Double.parseDouble(unitCost));
        c.setDescr(descr);
        CostDao dao = new CostDao();
        dao.updateCost(c);
        res.sendRedirect("findCosts.do");
    }
}
