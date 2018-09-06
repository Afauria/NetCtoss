package com.zwy.work.web;

import com.zwy.work.dao.AccountDao;
import com.zwy.work.dao.RoleDao;
import com.zwy.work.entity.Account;
import com.zwy.work.util.ValidateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AccountServlet extends HttpServlet {
    private int singlePageLimit = 5;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getAttribute("path").toString();
        //根据规范做出判断处理
        System.out.println(path);
        switch (path) {
            case "/findAccounts.do":
                findAccounts(req, res);
                break;
            case "/toAddAccount.do":
                toAddAccount(req, res);
                break;
            case "/addAccount.do":
                addAccount(req, res);
                break;
            case "/toModifyAccount.do":
                toModifyAccount(req, res);
                break;
            case "/modifyAccount.do":
                modifyAccount(req, res);
                break;
            case "/deleteAccount.do":
                deleteAccount(req, res);
                break;
            case "/searchAccounts.do":
                searchAccounts(req, res);
                break;
            case "/accountDetail.do":
                accountDetailById(req, res);
                break;
            default:
                throw new RuntimeException("查无此页面");
        }
    }

    private void accountDetailById(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        AccountDao dao = new AccountDao();
        Account account = dao.findAccountById(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("account", account);
        req.getRequestDispatcher("WEB-INF/account/account_detail.jsp").forward(req, res);
    }

    private void findAccounts(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer currentPage = 1;
        if (req.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }
        AccountDao dao = new AccountDao();
        List<Account> accounts = dao.findAccounts();
        RoleDao roleDao = new RoleDao();

        int serviceCount = accounts.size();
        int pageCount = serviceCount / singlePageLimit + (serviceCount % singlePageLimit > 0 ? 1 : 0);
        if (pageCount == 0) {
            pageCount = 1;
        }
        int lastPageCostCount = serviceCount - (pageCount - 1) * singlePageLimit;
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        accounts = accounts.subList((currentPage - 1) * singlePageLimit, (currentPage - 1) * singlePageLimit + (currentPage == pageCount ?
                lastPageCostCount : singlePageLimit));
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("accounts", accounts);
        req.setAttribute("path", "findAccounts.do");
        req.getRequestDispatcher("WEB-INF/account/account_list.jsp").forward(req, res);
    }

    private void toAddAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/account/account_add.jsp").forward(req, res);
    }

    private void addAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String realName = req.getParameter("realName");
        String accountIdCard = req.getParameter("accountIdCard");
        String loginName = req.getParameter("loginName");
        String telephone = req.getParameter("telephone");
        String email = req.getParameter("email");
        String recommenderIdCard = req.getParameter("recommenderIdCard");
        String occupation = req.getParameter("occupation");
        String gender = req.getParameter("gender");
        String mailAddress = req.getParameter("mailAddress");
        String zipcode = req.getParameter("zipcode");
        String QQ = req.getParameter("QQ");
        AccountDao accountDao = new AccountDao();
        Account a = accountDao.findAccountByIdCard(accountIdCard);
        if (a != null) {
            String errorMsg = "保存失败，该身份证已经开通过账务账号！";
            errorMsg = new String(errorMsg.getBytes("UTF-8"), "iso-8859-1");
            res.sendRedirect("toAddAccount.do?error='" + errorMsg + "'");
            return;
        }
        a = accountDao.findAccountByLoginName(loginName);
        if (a != null) {
            String errorMsg = "保存失败，该登录名已被使用！";
            errorMsg = new String(errorMsg.getBytes("UTF-8"), "iso-8859-1");
            res.sendRedirect("toAddAccount.do?error='" + errorMsg + "'");
            return;
        }
        Account recommender = new Account();
        if (!recommenderIdCard.equals("")) {
            recommender = accountDao.findAccountByIdCard(recommenderIdCard);
            if (recommender == null) {
                String errorMsg = "推荐人身份证不正确";
                errorMsg = new String(errorMsg.getBytes("UTF-8"), "iso-8859-1");
                res.sendRedirect("toAddAccount.do?error='" + errorMsg + "'");
                return;
            }
        }
        Account account = new Account();
        account.setRealName(realName);
        account.setIdCard(accountIdCard);
        account.setLoginName(loginName);
        account.setLoginPasswd(password);
        account.setTelephone(telephone);
        account.setBirthdate(ValidateUtil.getBirthDateFromIdCard(accountIdCard));
        account.setEmail(email);
        account.setRecommenderId(recommender.getRecommenderId());
        account.setOccupation(occupation);
        account.setOccupation(occupation);
        account.setGender(gender);
        account.setMailAddress(mailAddress);
        account.setZipcode(zipcode);
        account.setQQ(QQ);
        accountDao.addAccount(account);
        res.sendRedirect("findAccounts.do");
    }

    private void toModifyAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getParameter("accountId") == null) {
            throw new RuntimeException("账务账号id不能为空");
        }
        int accountId = Integer.parseInt(req.getParameter("accountId"));
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.findAccountById(accountId);
        Account recommender = accountDao.findAccountById(account.getRecommenderId());
        req.setAttribute("accountInfo", account);
        req.setAttribute("recommenderInfo", recommender);
        req.getRequestDispatcher("WEB-INF/account/account_modi.jsp").forward(req, res);
    }

    private void modifyAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getParameter("accountId") == null) {
            throw new RuntimeException("账务账号id不能为空");
        }
        Integer accountId = Integer.parseInt(req.getParameter("accountId"));
        String realName = req.getParameter("realName");
        String telephone = req.getParameter("telephone");
        String email = req.getParameter("email");
        String occupation = req.getParameter("occupation");
        String gender = req.getParameter("gender");
        String mailAddress = req.getParameter("mailAddress");
        String zipcode = req.getParameter("zipcode");
        String QQ = req.getParameter("QQ");
        AccountDao accountDao = new AccountDao();
        Account account = new Account();
        account.setAccountId(accountId);
        account.setRealName(realName);
        account.setTelephone(telephone);
        account.setEmail(email);
        account.setOccupation(occupation);
        account.setGender(gender);
        account.setMailAddress(mailAddress);
        account.setZipcode(zipcode);
        account.setQQ(QQ);
        accountDao.updateAccount(account);
        res.sendRedirect("findAccounts.do");
    }

    private void deleteAccount(HttpServletRequest req, HttpServletResponse res) throws IOException {
        if (req.getParameter("accountId") == null) {
            throw new RuntimeException("账务账号id不能为空");
        }
        Integer accountId = Integer.parseInt(req.getParameter("accountId"));
        AccountDao accountDao = new AccountDao();
        accountDao.deleteAccount(accountId);
        res.sendRedirect("findAccounts.do?deleteSuccess=1");
    }

    private void searchAccounts(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer currentPage = 1;
        if (req.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }
        String status = req.getParameter("status");
        String idcard = req.getParameter("idcard");
        String realname = req.getParameter("realname");
        String loginname = req.getParameter("loginname");
        req.setAttribute("status", status);
        req.setAttribute("idcard", idcard);
        req.setAttribute("realname", realname);
        req.setAttribute("loginname", loginname);
        AccountDao dao = new AccountDao();
        List<Account> accounts = dao.searchAccounts(status, idcard, realname, loginname);
        int accountsCount = accounts.size();
        int pageCount = accountsCount / singlePageLimit + (accountsCount % singlePageLimit > 0 ? 1 : 0);
        if (pageCount == 0) {
            pageCount = 1;
        }
        int lastPageCostCount = accountsCount - (pageCount - 1) * singlePageLimit;
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        accounts = accounts.subList((currentPage - 1) * singlePageLimit, (currentPage - 1) * singlePageLimit + (currentPage == pageCount ?
                lastPageCostCount : singlePageLimit));
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("accounts", accounts);
        req.setAttribute("path", "searchAccounts.do");
        req.getRequestDispatcher("WEB-INF/account/account_list.jsp").forward(req, res);
    }
}
