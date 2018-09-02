package com.zwy.work.web;

import com.zwy.work.dao.AdminDao;
import com.zwy.work.entity.Admin;
import com.zwy.work.util.ImageUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public class MainServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //获取请求路径  /findCosts.do
        String path = req.getServletPath();
        //设置编码格式
        req.setCharacterEncoding("utf-8");
        res.setCharacterEncoding("utf-8");
        //根据规范做出判断处理
        switch (path) {
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
                req.setAttribute("path", path);
                req.getRequestDispatcher("/toCostServlet").forward(req, res);
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
                req.setAttribute("path", path);
                req.getRequestDispatcher("/toAdminServlet").forward(req, res);
                break;
            case "/findRoles.do":
            case "/toModifyRole.do":
            case "/modifyRole.do":
            case "/toAddRole.do":
            case "/addRole.do":
            case "/deleteRole.do":
                req.setAttribute("path", path);
                req.getRequestDispatcher("/toRoleServlet").forward(req, res);
                break;
            case "/findAccounts.do":
            case "/toAddAccount.do":
            case "/addAccount.do":
                req.setAttribute("path", path);
                req.getRequestDispatcher("/toAccountServlet").forward(req, res);
                break;
            case "/findServices.do":
            case "/toAddService.do":
                req.setAttribute("path", path);
                req.getRequestDispatcher("/toServiceServlet").forward(req, res);
                break;
            case "/findReports.do":
                req.setAttribute("path", path);
                req.getRequestDispatcher("/toReportServlet").forward(req, res);
                break;
            case "/findBills.do":
                req.setAttribute("path", path);
                req.getRequestDispatcher("/toBillServlet").forward(req, res);
                break;
            default:
                throw new RuntimeException("查无此页面");
        }
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
        req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
    }

    //登陆
    private void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //获取表单提交的数据
        String adminCode = req.getParameter("adminCode");
        String password = req.getParameter("password");
        String imgcode = req.getParameter("imgcode");
        //根据账号查找用户
        AdminDao dao = new AdminDao();
        Admin user = dao.findUserByCode(adminCode);
        //获取session中的验证码
        HttpSession session = req.getSession();
        String imgcode2 = (String) session.getAttribute("imgcode");
        //判断表单提交的验证码和服务端产生的验证码是否一致
//        if (imgcode == null || imgcode.equals("") || !imgcode.equalsIgnoreCase(imgcode2)) {
//            //验证码错误,转发到登陆界面
//            req.setAttribute("error", "验证码错误");
//            req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
//        } else
        if (user == null) {
            //账号错误,转发到登陆界面
            req.setAttribute("error", "账号不存在");
            req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
        } else if (!user.getPassword().equals(password)) {
            //密码错误,转发到登陆界面
            req.setAttribute("error", "密码错误");
            req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
        } else {
            //将账户存入cookie
            Cookie c = new Cookie("adminCode", adminCode);
            c.setPath("/netctoss");
            res.addCookie(c);
            //将账号存入session
            session.setAttribute("adminCode", adminCode);
            //登陆成功,重定向到主界面
            res.sendRedirect("toIndex.do");
        }
    }

    //打开主页
    private void toIndex(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/main/index.jsp").forward(req, res);
    }

}
