package com.zwy.work.web;

import com.zwy.work.dao.AdminDao;
import com.zwy.work.dao.RoleDao;
import com.zwy.work.entity.Admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getAttribute("path").toString();
        //根据规范做出判断处理
        System.out.println(path);
        switch (path) {
            case "/findUserInfo.do":
                findUserInfo(req, res);
                break;
            case "/updateUserInfo.do":
                updateUserInfo(req, res);
                break;
            case "/toModifyPwd.do":
                toModifyPwd(req, res);
                break;
            case "/modifyPwd.do":
                modifyPwd(req, res);
                break;
            default:
                throw new RuntimeException("查无此页面");
        }
    }

    //查询资费信息
    private void findUserInfo(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //查询所有的资费
        HttpSession session = req.getSession();
        Object userCode = session.getAttribute("adminCode");
        if (userCode == null) {
            throw new RuntimeException("用户未登录");
        }
        AdminDao adminDao = new AdminDao();
        Admin user = adminDao.findUserByCode(userCode.toString());
        RoleDao roleDao = new RoleDao();
        String roles = roleDao.findRoleByAdminId(user.getAdminId());
        req.setAttribute("userInfo", user);
        req.setAttribute("userRoles", roles);
        req.getRequestDispatcher("WEB-INF/user/user_info.jsp").forward(req, res);
    }

    private void updateUserInfo(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String adminCode = req.getParameter("adminCode");
        String adminName = req.getParameter("adminName");
        String telephone = req.getParameter("telephone");
        String email = req.getParameter("email");
        Admin user = new Admin();
        user.setAdminCode(adminCode);
        user.setAdminName(adminName);
        user.setTelephone(telephone);
        user.setEmail(email);
        AdminDao dao = new AdminDao();
        dao.updateUserInfo(user);
        res.sendRedirect("findUserInfo.do");
    }

    private void toModifyPwd(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/user/user_modi_pwd.jsp").forward(req, res);
    }

    private void modifyPwd(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object adminCode = session.getAttribute("adminCode");
        if (adminCode == null) {
            throw new RuntimeException("用户未登录");
        }
        String oldPwd = req.getParameter("oldPwd");
        String newPwd = req.getParameter("newPwd");
        String confirmPwd = req.getParameter("confirmPwd");
        if (oldPwd == null || newPwd == null || confirmPwd == null ||
                oldPwd.equals("") || newPwd.equals("") || confirmPwd.equals("")) {
            req.setAttribute("error", "密码不能为空");
            req.getRequestDispatcher("toModifyPwd.do").forward(req, res);
            //需要return避免连续发两次forward，报错：Cannot forward after response has been committed
            return;
        }
        AdminDao dao = new AdminDao();
        Admin oldUser = dao.findUserByCode(adminCode.toString());
        if (!oldUser.getPassword().equals(oldPwd)) {
            req.setAttribute("error", "旧密码错误");
            req.getRequestDispatcher("toModifyPwd.do").forward(req, res);
        } else if (!newPwd.equals(confirmPwd)) {
            req.setAttribute("error", "两次密码输入不一致");
            req.getRequestDispatcher("toModifyPwd.do").forward(req, res);
        } else {
            Admin newUser = new Admin();
            newUser.setAdminCode(oldUser.getAdminCode());
            newUser.setPassword(newPwd);
            dao.modifyPwd(newUser);
            throw new RuntimeException("修改密码成功，请重新登录");
        }
    }
}
