package com.zwy.work.web;

import com.zwy.work.dao.*;
import com.zwy.work.entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BillServlet extends HttpServlet {
    private int singlePageLimit = 5;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getAttribute("path").toString();
        //根据规范做出判断处理
        System.out.println(path);
        switch (path) {
            case "/findBills.do":
                findBills(req, res);
                break;
            case "/billItem.do":
                billItemById(req, res);
                break;
            case "/billServiceDetail.do":
                billServiceDetail(req, res);
                break;
            case "/searchBills.do":
                searchBills(req, res);
                break;
            default:
                throw new RuntimeException("查无此页面");
        }
    }

    private void findBills(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer currentPage = 1;
        if (req.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }
        BillDao dao = new BillDao();
        List<Bill> bills = dao.findBills();
        AccountDao accountDao = new AccountDao();
        for (int i = 0; i < bills.size(); i++) {
            Account account = accountDao.findAccountById(bills.get(i).getAccountId());
            bills.get(i).setAccount(account);
        }
        int billsCount = bills.size();
        int pageCount = billsCount / singlePageLimit + (billsCount % singlePageLimit > 0 ? 1 : 0);
        if (pageCount == 0) {
            pageCount = 1;
        }
        int lastPageCostCount = billsCount - (pageCount - 1) * singlePageLimit;
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        bills = bills.subList((currentPage - 1) * singlePageLimit, (currentPage - 1) * singlePageLimit + (currentPage == pageCount ?
                lastPageCostCount : singlePageLimit));
        req.setAttribute("bills", bills);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("path", "findBills.do");
        req.getRequestDispatcher("WEB-INF/bill/bill_list.jsp").forward(req, res);
    }

    private void searchBills(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer currentPage = 1;
        if (req.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }
        String idCard = req.getParameter("idCard");
        String loginName = req.getParameter("loginName");
        String realName = req.getParameter("realName");
        String year = req.getParameter("year");
        String month = req.getParameter("month");
        req.setAttribute("idCard", idCard);
        req.setAttribute("loginName", loginName);
        req.setAttribute("realName", realName);
        req.setAttribute("year", year);
        req.setAttribute("month", month);
        BillDao dao = new BillDao();
        List<Bill> bills = dao.searchBills(idCard, loginName, realName, year, month);
        AccountDao accountDao = new AccountDao();
        for (int i = 0; i < bills.size(); i++) {
            Account account = accountDao.findAccountById(bills.get(i).getAccountId());
            bills.get(i).setAccount(account);
        }
        int billsCount = bills.size();
        int pageCount = billsCount / singlePageLimit + (billsCount % singlePageLimit > 0 ? 1 : 0);
        if (pageCount == 0) {
            pageCount = 1;
        }
        int lastPageCostCount = billsCount - (pageCount - 1) * singlePageLimit;
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        bills = bills.subList((currentPage - 1) * singlePageLimit, (currentPage - 1) * singlePageLimit + (currentPage == pageCount ?
                lastPageCostCount : singlePageLimit));
        req.setAttribute("bills", bills);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("path", "searchBills.do");
        req.getRequestDispatcher("WEB-INF/bill/bill_list.jsp").forward(req, res);
    }

    private void billItemById(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getParameter("billId") == null) {
            throw new RuntimeException("账单id不能为空");
        }
        Integer currentPage = 1;
        if (req.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }
        int billId = Integer.parseInt(req.getParameter("billId"));
        BillDao billDao = new BillDao();
        Bill bill = billDao.findBillById(billId);
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.findAccountById(bill.getAccountId());
        ServiceDao serviceDao = new ServiceDao();
        List<Service> services = serviceDao.findServicesByAccountId(account.getAccountId());
        CostDao costDao = new CostDao();
        for (int i = 0; i < services.size(); i++) {
            Cost cost = costDao.findCostById(services.get(i).getCostId());
            services.get(i).setCost(cost);
        }
        bill.setAccount(account);
        int servicesCount = services.size();
        int pageCount = servicesCount / singlePageLimit + (servicesCount % singlePageLimit > 0 ? 1 : 0);
        if (pageCount == 0) {
            pageCount = 1;
        }
        int lastPageCostCount = servicesCount - (pageCount - 1) * singlePageLimit;
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        services = services.subList((currentPage - 1) * singlePageLimit, (currentPage - 1) * singlePageLimit + (currentPage == pageCount ?
                lastPageCostCount : singlePageLimit));
        bill.setServices(services);
        req.setAttribute("bill", bill);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("path", "billItem.do");
        req.getRequestDispatcher("WEB-INF/bill/bill_item.jsp").forward(req, res);
    }

    private void billServiceDetail(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getParameter("billId") == null) {
            throw new RuntimeException("账单id不能为空");
        }
        if (req.getParameter("serviceId") == null) {
            throw new RuntimeException("业务账号id不能为空");
        }
        Integer currentPage = 1;
        if (req.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }
        Integer billId = Integer.parseInt(req.getParameter("billId"));
        Integer serviceId = Integer.parseInt(req.getParameter("serviceId"));
        BillDao billDao = new BillDao();
        ServiceDao serviceDao = new ServiceDao();
        Bill bill = billDao.findBillById(billId);
        Service service = serviceDao.findServiceById(serviceId);

        AccountDao accountDao = new AccountDao();
        Account account = accountDao.findAccountById(bill.getAccountId());
        bill.setAccount(account);

        CostDao costDao = new CostDao();
        Cost cost = costDao.findCostById(service.getCostId());
        service.setCost(cost);


        RecordDao recordDao = new RecordDao();
        List<Record> records = recordDao.findRecordsByServiceId(serviceId);
        int servicesCount = records.size();
        int pageCount = servicesCount / singlePageLimit + (servicesCount % singlePageLimit > 0 ? 1 : 0);
        if (pageCount == 0) {
            pageCount = 1;
        }
        int lastPageCostCount = servicesCount - (pageCount - 1) * singlePageLimit;
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        records = records.subList((currentPage - 1) * singlePageLimit, (currentPage - 1) * singlePageLimit + (currentPage == pageCount ?
                lastPageCostCount : singlePageLimit));
        service.setRecords(records);

        req.setAttribute("service", service);
        req.setAttribute("bill", bill);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("path", "billItem.do");
        req.getRequestDispatcher("WEB-INF/bill/bill_service_detail.jsp").forward(req, res);
    }
}
