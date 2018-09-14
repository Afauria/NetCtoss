package com.zwy.work.web;

import com.zwy.work.dao.ModuleDao;
import com.zwy.work.dao.RoleDao;
import com.zwy.work.entity.Module;
import com.zwy.work.entity.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoleServlet extends HttpServlet {
    private int singlePageLimit = 5;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getAttribute("path").toString();
        //根据规范做出判断处理
        System.out.println(path);
        switch (path) {
            case "/findRoles.do":
                findRoles(req, res);
                break;
            case "/toModifyRole.do":
                toModifyRole(req, res);
                break;
            case "/modifyRole.do":
                modifyRole(req, res);
                break;
            case "/toAddRole.do":
                toAddRole(req, res);
                break;
            case "/addRole.do":
                addRole(req, res);
                break;
            case "/deleteRole.do":
                deleteRole(req, res);
                break;
            default:
                throw new RuntimeException("查无此页面");
        }
    }

    private void findRoles(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer currentPage = 1;
        if (req.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }
        RoleDao roleDao = new RoleDao();
        List<Role> roles = roleDao.findRoles();
        ModuleDao moduleDao = new ModuleDao();
        for (int i = 0; i < roles.size(); i++) {
            List<Module> roleModules = moduleDao.findRoleModulesByRoleId(roles.get(i).getRoleId());
            roles.get(i).setRoleModules(roleModules);
        }
        int rolesCount = roles.size();
        int pageCount = rolesCount / singlePageLimit + (rolesCount % singlePageLimit > 0 ? 1 : 0);
        if (pageCount == 0) {
            pageCount = 1;
        }
        int lastPageCostCount = rolesCount - (pageCount - 1) * singlePageLimit;
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        roles = roles.subList((currentPage - 1) * singlePageLimit,
                (currentPage - 1) * singlePageLimit + (currentPage == pageCount ? lastPageCostCount : singlePageLimit));
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("roles", roles);
        req.getRequestDispatcher("WEB-INF/role/role_list.jsp").forward(req, res);
    }

    private void toModifyRole(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getParameter("roleId") == null) {
            throw new RuntimeException("角色信息不能为空");
        }
        int roleId = Integer.parseInt(req.getParameter("roleId"));
        RoleDao roleDao = new RoleDao();
        Role roleInfo = roleDao.findRoleById(roleId);
        ModuleDao moduleDao = new ModuleDao();
        List<Module> roleModules = moduleDao.findRoleModulesByRoleId(roleId);
        roleInfo.setRoleModules(roleModules);
        req.setAttribute("roleInfo", roleInfo);
        List<Module> totalModules = moduleDao.findModules();
        req.setAttribute("totalModules", totalModules);
        req.getRequestDispatcher("WEB-INF/role/role_modi.jsp").forward(req, res);
    }

    private void modifyRole(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getParameter("roleId") == null) {
            throw new RuntimeException("角色id不能为空");
        }
        Integer roleId = Integer.parseInt(req.getParameter("roleId"));
        String roleName = req.getParameter("roleName");
        String[] selectModulesId = req.getParameterValues("selectModulesId");
        List<Module> selectModules = new ArrayList<>();
        ModuleDao moduleDao = new ModuleDao();
        for (String moduleId : selectModulesId) {
            Module module = moduleDao.findModuleById(Integer.parseInt(moduleId));
            selectModules.add(module);
        }
        Role role = new Role(roleId, roleName, selectModules);
        RoleDao roleDao = new RoleDao();
        roleDao.updateRoleInfo(role);
        res.sendRedirect("findRoles.do");
    }

    private void toAddRole(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RoleDao roleDao = new RoleDao();
        List<Role> roles = roleDao.findRoles();
        req.setAttribute("roles", roles);
        ModuleDao moduleDao = new ModuleDao();
        List<Module> totalModules = moduleDao.findModules();
        req.setAttribute("totalModules", totalModules);
        req.getRequestDispatcher("WEB-INF/role/role_add.jsp").forward(req, res);
    }

    private void addRole(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String roleName = req.getParameter("roleName");
        RoleDao roleDao = new RoleDao();
        List<Role> roles = roleDao.findRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(roleName)) {
                res.sendRedirect("toAddRole.do?error='该角色已存在'");
                return;
            }
        }
        String[] selectModulesId = req.getParameterValues("selectModulesId");
        List<Module> selectModules = new ArrayList<>();
        ModuleDao moduleDao = new ModuleDao();
        for (String moduleId : selectModulesId) {
            Module module = moduleDao.findModuleById(Integer.parseInt(moduleId));
            selectModules.add(module);
        }
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleModules(selectModules);
        roleDao.addRole(role);
        res.sendRedirect("findRoles.do");
    }

    private void deleteRole(HttpServletRequest req, HttpServletResponse res) throws IOException {
        if (req.getParameter("roleId") == null) {
            throw new RuntimeException("角色id不能为空");
        }
        Integer roleId = Integer.parseInt(req.getParameter("roleId"));
        RoleDao roleDao = new RoleDao();
        roleDao.deleteRole(roleId);
        res.sendRedirect("findRoles.do?deleteSuccess=1");
    }
}
