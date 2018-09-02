package com.zwy.work.web;

import com.zwy.work.dao.*;
import com.zwy.work.entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServicesServlet extends HttpServlet {
    private int singlePageLimit = 5;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getAttribute("path").toString();
        //根据规范做出判断处理
        System.out.println(path);
        switch (path) {
            case "/findServices.do":
                findServices(req, res);
                break;
            case "/toAddService.do":
                toAddService(req,res);
                break;
            case "/addService.do":
                addService(req,res);
                break;
            default:
                throw new RuntimeException("查无此页面");
        }
    }

    private void findServices(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer currentPage = 1;
        if (req.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }
        ServiceDao dao = new ServiceDao();
        List<Service> services = dao.findServices();
        AccountDao accountDao = new AccountDao();
        CostDao costDao = new CostDao();
        for (int i = 0; i < services.size(); i++) {
            Account account = accountDao.findAccountById(services.get(i).getAccountId());
            services.get(i).setAccount(account);
            Cost cost = costDao.findCostById(services.get(i).getCostId());
            services.get(i).setCost(cost);
        }
        int serviceCount = services.size();
        int pageCount = serviceCount / singlePageLimit + (serviceCount % singlePageLimit > 0 ? 1 : 0);
        if (pageCount == 0) {
            pageCount = 1;
        }
        int lastPageCostCount = serviceCount - (pageCount - 1) * singlePageLimit;
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        services = services.subList((currentPage - 1) * singlePageLimit, (currentPage - 1) * singlePageLimit + (currentPage == pageCount ? lastPageCostCount : singlePageLimit));
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("services", services);
        req.getRequestDispatcher("WEB-INF/service/service_list.jsp").forward(req, res);
    }
    private void toAddService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/service/service_add.jsp").forward(req, res);
    }
    private void addService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String password = req.getParameter("adminPassword");
        String confirmPassword = req.getParameter("adminConfirmPassword");
        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "两次密码输入不一致");
            req.getRequestDispatcher("toAddAdmin.do").forward(req, res);
            return;
        }
        String adminCode = req.getParameter("adminCode");
        AdminDao adminDao = new AdminDao();
        List<Admin> admins = adminDao.findAdmins();
        for (Admin admin : admins) {
            if (admin.getAdminCode().equals(adminCode)) {
                res.sendRedirect("toAddAdmin.do?error='该用户已存在'");
                return;
            }
        }
        String[] selectRolesId = req.getParameterValues("selectRolesId");
        List<Role> selectRoles = new ArrayList<>();
        RoleDao roleDao = new RoleDao();
        for (String roleId : selectRolesId) {
            Role role = roleDao.findRoleById(Integer.parseInt(roleId));
            selectRoles.add(role);
        }
        String adminName = req.getParameter("adminName");
        String telephone = req.getParameter("adminTelephone");
        String email = req.getParameter("adminEmail");
        Admin admin = new Admin();
        admin.setAdminCode(adminCode);
        admin.setAdminRoles(selectRoles);
        admin.setAdminName(adminName);
        admin.setTelephone(telephone);
        admin.setEmail(email);
        admin.setPassword(password);
        adminDao.addAdmin(admin);
        res.sendRedirect("findAdmins.do");
    }
}
