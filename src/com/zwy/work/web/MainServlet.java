package com.zwy.work.web;

import com.zwy.work.dao.AdminDao;
import com.zwy.work.dao.ModuleDao;
import com.zwy.work.dao.RoleDao;
import com.zwy.work.entity.Admin;
import com.zwy.work.entity.Module;
import com.zwy.work.entity.Role;
import com.zwy.work.util.ImageUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainServlet extends HttpServlet {
    private static final int ROLE_PERMISSION = 1;
    private static final int ADMIN_PERMISSION = 2;
    private static final int COST_PERMISSION = 3;
    private static final int ACCOUNT_PERMISSION = 4;
    private static final int SERVICE_PERMISSION = 5;
    private static final int BILL_PERMISSION = 6;
    private static final int REPORT_PERMISSION = 7;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //获取请求路径  /findCosts.do
        String path = req.getServletPath();
        //设置编码格式
        req.setCharacterEncoding("utf-8");
        res.setCharacterEncoding("utf-8");
        //根据规范做出判断处理
        switch (path) {
            case "/toNoPower.do":
                req.getRequestDispatcher("WEB-INF/nopower.jsp").forward(req, res);
                break;
            case "/toLogin.do":
                toLogin(req, res);
                break;
            case "/toIndex.do":
                toIndex(req, res);
                break;
            case "/login.do":
                login(req, res);
                break;
            case "/createImg.do":
                createImg(req, res);
                break;
            case "/findCosts.do":
            case "/toAddCost.do":
            case "/addCost.do":
            case "/toUpdateCost.do":
            case "/updateCost.do":
            case "/costDetail.do":
            case "/deleteCost.do":
            case "/setCostState.do":
                if (checkPermission(req, res, COST_PERMISSION)) {
                    req.setAttribute("path", path);
                    req.getRequestDispatcher("/toCostServlet").forward(req, res);
                }
                break;
            case "/findUserInfo.do":
            case "/updateUserInfo.do":
            case "/toModifyPwd.do":
            case "/modifyPwd.do":
            case "/findAdmins.do":
            case "/searchAdmins.do":
            case "/toModifyAdmin.do":
            case "/modifyAdmin.do":
            case "/toAddAdmin.do":
            case "/addAdmin.do":
            case "/deleteAdmin.do":
            case "/resetPwd.do":
                if (checkPermission(req, res, ADMIN_PERMISSION)) {
                    req.setAttribute("path", path);
                    req.getRequestDispatcher("/toAdminServlet").forward(req, res);
                }
                break;
            case "/findRoles.do":
            case "/toModifyRole.do":
            case "/modifyRole.do":
            case "/toAddRole.do":
            case "/addRole.do":
            case "/deleteRole.do":
                if (checkPermission(req, res, ROLE_PERMISSION)) {
                    req.setAttribute("path", path);
                    req.getRequestDispatcher("/toRoleServlet").forward(req, res);
                }
                break;
            case "/findAccounts.do":
            case "/toAddAccount.do":
            case "/addAccount.do":
            case "/toModifyAccount.do":
            case "/modifyAccount.do":
            case "/deleteAccount.do":
            case "/searchAccounts.do":
            case "/accountDetail.do":
            case "/setAccountState.do":
                if (checkPermission(req, res, ACCOUNT_PERMISSION)) {
                    req.setAttribute("path", path);
                    req.getRequestDispatcher("/toAccountServlet").forward(req, res);
                }
                break;
            case "/findServices.do":
            case "/toAddService.do":
            case "/addService.do":
            case "/toModifyService.do":
            case "/modifyService.do":
            case "/deleteService.do":
            case "/searchServices.do":
            case "/serviceDetail.do":
            case "/setServiceState.do":
                if (checkPermission(req, res, SERVICE_PERMISSION)) {
                    req.setAttribute("path", path);
                    req.getRequestDispatcher("/toServiceServlet").forward(req, res);
                }
                break;
            case "/findReports.do":
                if (checkPermission(req, res, REPORT_PERMISSION)) {
                    req.setAttribute("path", path);
                    req.getRequestDispatcher("/toReportServlet").forward(req, res);
                }
                break;
            case "/findBills.do":
            case "/billItem.do":
            case "/billServiceDetail.do":
            case "/searchBills.do":
                if (checkPermission(req, res, BILL_PERMISSION)) {
                    req.setAttribute("path", path);
                    req.getRequestDispatcher("/toBillServlet").forward(req, res);
                }
                break;
            default:
                throw new RuntimeException("查无此页面");
        }
    }
    private boolean checkPermission(HttpServletRequest req, HttpServletResponse res, int moduleid) throws IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("userInfo") == null) {
            throw new RuntimeException("用户未登录！");
        }
        Admin user = (Admin) session.getAttribute("userInfo");
        boolean hasPermission = false;
        for (Module module : user.getAdminModules()) {
            if (module.getModuleId() == moduleid) {
                hasPermission = true;
                break;
            }
        }
        if (!hasPermission) {
            res.sendRedirect("toNoPower.do");
            return false;
        }
        return true;
    }

    private void createImg(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //生成验证码和图片
        Object[] objs = ImageUtil.createImage();
        String imgcode = (String) objs[0];
        BufferedImage img = (BufferedImage) objs[1];
        //将验证码存入session
        HttpSession session = req.getSession();
        session.setAttribute("imgcode", imgcode);
        //将图片给浏览器
        res.setContentType("image/jpeg");
        OutputStream os = res.getOutputStream();
        ImageIO.write(img, "jpeg", os);
    }

    //打开登陆界面
    private void toLogin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("adminCode");
        session.removeAttribute("userInfo");
        req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
    }

    //登陆
    private void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //获取表单提交的数据
        String adminCode = req.getParameter("adminCode");
        String password = req.getParameter("password");
        String imgcode = req.getParameter("imgcode");
        //根据账号查找用户
        AdminDao adminDao = new AdminDao();
        Admin user = adminDao.findUserByCode(adminCode);
        //获取session中的验证码
        HttpSession session = req.getSession();
        String imgcode2 = (String) session.getAttribute("imgcode");
        //判断表单提交的验证码和服务端产生的验证码是否一致
        if (imgcode == null || imgcode.equals("") || !imgcode.equalsIgnoreCase(imgcode2)) {
            //验证码错误,转发到登陆界面
            req.setAttribute("error", "验证码错误");
            req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
        } else if (user == null) {
            //账号错误,转发到登陆界面
            req.setAttribute("error", "账号不存在");
            req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
        } else if (!user.getPassword().equals(password)) {
            //密码错误,转发到登陆界面
            req.setAttribute("error", "密码错误");
            req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
        } else {
            RoleDao roleDao = new RoleDao();
            ModuleDao moduleDao = new ModuleDao();
            List<Role> roles = roleDao.findRolesByAdminId(user.getAdminId());
            List<Module> modules = new ArrayList<>();
            for (Role role : roles) {
                List<Module> tem = moduleDao.findRoleModulesByRoleId(role.getRoleId());
                for (Module module : tem) {
                    boolean hasModule = false;
                    for (Module module1 : modules) {
                        if (module1.getModuleId() == module.getModuleId()) {
                            hasModule = true;
                        }
                    }
                    if (!hasModule) {
                        modules.add(module);
                        System.out.println(module.getModuleName());
                    }
                }
            }
            user.setAdminModules(modules);

            //将账户存入cookie
            Cookie c = new Cookie("adminCode", adminCode);
            c.setPath("/netctoss");
            res.addCookie(c);
            //将账号存入session
            session.setAttribute("adminCode", adminCode);
            session.setAttribute("userInfo", user);
            //登陆成功,重定向到主界面
            res.sendRedirect("toIndex.do");
        }
    }

    //打开主页
    private void toIndex(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/main/index.jsp").forward(req, res);
    }

}
