package com.zwy.work.web;

import com.zwy.work.dao.AdminDao;
import com.zwy.work.dao.ModuleDao;
import com.zwy.work.dao.RoleDao;
import com.zwy.work.entity.Admin;
import com.zwy.work.entity.Module;
import com.zwy.work.entity.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminServlet extends HttpServlet {
    private int singlePageLimit = 5;

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
            case "/findAdmins.do":
                findAdmins(req, res);
                break;
            case "/searchAdmins.do":
                searchAdmins(req,res);
                break;
            case "/toModifyAdmin.do":
                toModifyAdmin(req, res);
                break;
            case "/modifyAdmin.do":
                modifyAdmin(req, res);
                break;
            case "/toAddAdmin.do":
                toAddAdmin(req, res);
                break;
            case "/addAdmin.do":
                addAdmin(req, res);
                break;
            case "/deleteAdmin.do":
                deleteAdmin(req, res);
                break;
            case "/resetPwd.do":
                resetPwd(req, res);
                break;
            default:
                throw new RuntimeException("查无此页面");
        }
    }

    private void findUserInfo(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object userCode = session.getAttribute("adminCode");
        if (userCode == null) {
            throw new RuntimeException("用户未登录");
        }
        AdminDao adminDao = new AdminDao();
        Admin user = adminDao.findUserByCode(userCode.toString());
        RoleDao roleDao = new RoleDao();
        List<Role> roles = roleDao.findRolesByAdminId(user.getAdminId());
        user.setAdminRoles(roles);
        req.setAttribute("userInfo", user);
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
        AdminDao adminDao = new AdminDao();
        adminDao.updateUserInfo(user);
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
        AdminDao adminDao = new AdminDao();
        Admin oldUser = adminDao.findUserByCode(adminCode.toString());
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
            adminDao.modifyPwd(newUser);
            throw new RuntimeException("修改密码成功，请重新登录");
        }
    }

    private void findAdmins(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer currentPage = 1;
        if (req.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }
        AdminDao dao = new AdminDao();
        List<Admin> admins = dao.findAdmins();
        RoleDao roleDao = new RoleDao();
        for (int i = 0; i < admins.size(); i++) {
            List<Role> adminRoles = roleDao.findRolesByAdminId(admins.get(i).getAdminId());
            admins.get(i).setAdminRoles(adminRoles);
        }
        int adminsCount = admins.size();
        int pageCount = adminsCount / singlePageLimit + (adminsCount % singlePageLimit > 0 ? 1 : 0);
        if (pageCount == 0) {
            pageCount = 1;
        }
        int lastPageCostCount = adminsCount - (pageCount - 1) * singlePageLimit;
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        admins = admins.subList((currentPage - 1) * singlePageLimit, (currentPage - 1) * singlePageLimit + (currentPage == pageCount ? lastPageCostCount : singlePageLimit));
        ModuleDao moduleDao = new ModuleDao();
        List<Module> totalModules = moduleDao.findModules();
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("admins", admins);
        req.setAttribute("totalModules", totalModules);
        req.getRequestDispatcher("WEB-INF/admin/admin_list.jsp").forward(req, res);
    }

    private void searchAdmins(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer currentPage = 1;
        if (req.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }
        String moduleName = req.getParameter("moduleName");
        String roleName = req.getParameter("roleName");
        req.setAttribute("moduleName", moduleName);
        req.setAttribute("roleName",roleName);
        AdminDao dao = new AdminDao();
        List<Admin> admins = dao.searchAdmins(moduleName, roleName);
        RoleDao roleDao = new RoleDao();
        for (int i = 0; i < admins.size(); i++) {
            List<Role> adminRoles = roleDao.findRolesByAdminId(admins.get(i).getAdminId());
            admins.get(i).setAdminRoles(adminRoles);
        }
        int adminsCount = admins.size();
        int pageCount = adminsCount / singlePageLimit + (adminsCount % singlePageLimit > 0 ? 1 : 0);
        if (pageCount == 0) {
            pageCount = 1;
        }
        int lastPageCostCount = adminsCount - (pageCount - 1) * singlePageLimit;
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        admins = admins.subList((currentPage - 1) * singlePageLimit, (currentPage - 1) * singlePageLimit + (currentPage == pageCount ? lastPageCostCount : singlePageLimit));
        ModuleDao moduleDao = new ModuleDao();
        List<Module> totalModules = moduleDao.findModules();
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("admins", admins);
        req.setAttribute("totalModules", totalModules);
        req.getRequestDispatcher("WEB-INF/admin/admin_list.jsp").forward(req, res);
    }
    private void toModifyAdmin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getParameter("adminId") == null) {
            throw new RuntimeException("管理员id不能为空");
        }
        Integer adminId = Integer.parseInt(req.getParameter("adminId"));
        RoleDao roleDao = new RoleDao();
        List<Role> totalRoles = roleDao.findRoles();
        req.setAttribute("totalRoles", totalRoles);
        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.findAdminById(adminId);
        admin.setAdminRoles(roleDao.findRolesByAdminId(adminId));
        req.setAttribute("adminInfo", admin);
        req.getRequestDispatcher("/WEB-INF/admin/admin_modi.jsp").forward(req, res);
    }

    private void modifyAdmin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer adminId = Integer.parseInt(req.getParameter("adminId"));
        String adminName = req.getParameter("adminName");
        String adminCode = req.getParameter("adminCode");
        String telephone = req.getParameter("adminTelephone");
        String email = req.getParameter("adminEmail");
        String[] selectRolesId = req.getParameterValues("selectRolesId");
        List<Role> selectRoles = new ArrayList<>();
        RoleDao roleDao = new RoleDao();
        for (String roleId : selectRolesId) {
            Role role = roleDao.findRoleById(Integer.parseInt(roleId));
            selectRoles.add(role);
        }
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setAdminName(adminName);
        admin.setAdminCode(adminCode);
        admin.setTelephone(telephone);
        admin.setEmail(email);
        admin.setAdminRoles(selectRoles);
        AdminDao adminDao = new AdminDao();
        adminDao.updateAdmin(admin);
        req.getRequestDispatcher("findAdmins.do").forward(req, res);
    }

    private void toAddAdmin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        AdminDao adminDao = new AdminDao();
        List<Admin> roles = adminDao.findAdmins();
        RoleDao roleDao = new RoleDao();
        List<Role> totalRoles = roleDao.findRoles();
        req.setAttribute("totalRoles", totalRoles);
        req.setAttribute("admins", roles);
        req.getRequestDispatcher("WEB-INF/admin/admin_add.jsp").forward(req, res);
    }

    private void addAdmin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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

    private void deleteAdmin(HttpServletRequest req, HttpServletResponse res) throws IOException {
        if (req.getParameter("adminId") == null) {
            throw new RuntimeException("管理员id不能为空");
        }
        Integer adminId = Integer.parseInt(req.getParameter("adminId"));
        AdminDao adminDao = new AdminDao();
        adminDao.deleteAdmin(adminId);
        res.sendRedirect("findAdmins.do?deleteSuccess=1");
    }

    private void resetPwd(HttpServletRequest req, HttpServletResponse res) throws IOException {
        if (req.getParameter("selectAdminId") == null) {
            throw new RuntimeException("管理员id不能为空");
        }
        String[] selectAdminIds = req.getParameterValues("selectAdminId");
        AdminDao adminDao = new AdminDao();
        adminDao.resetPwd(selectAdminIds);
        res.sendRedirect("findAdmins.do?resetPwdSuccess=1");
    }
}
