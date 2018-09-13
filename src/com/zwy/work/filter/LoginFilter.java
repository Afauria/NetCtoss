package com.zwy.work.filter;

import com.zwy.work.dao.AdminDao;
import com.zwy.work.dao.ModuleDao;
import com.zwy.work.dao.RoleDao;
import com.zwy.work.entity.Admin;
import com.zwy.work.entity.Module;
import com.zwy.work.entity.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginFilter implements Filter {
    private String excludedPages;
    private String[] excludedPageArray;

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        boolean isExcludedPage = false;
        for (String page : excludedPageArray) {//判断是否在过滤url之外
            if (req.getServletPath().equals(page)) {
                isExcludedPage = true;
                break;
            }
        }

        if (isExcludedPage) {
            //排除页面
            chain.doFilter(req, resp);
            return;
        }
        HttpSession session = req.getSession();
        String adminCode = (String) session.getAttribute("adminCode");
        if (adminCode == null || "".equals(adminCode)) {
            // 跳转到登陆页面
//            resp.sendRedirect("toLogin.do");
            throw new RuntimeException("用户未登录！");
        } else {
            // 已经登陆,继续此次请求
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig arg0) throws ServletException {
        excludedPages = arg0.getInitParameter("excludedPages");
        if (null != excludedPages) {
            excludedPageArray = excludedPages.split(",");
        }
        return;
    }
}
