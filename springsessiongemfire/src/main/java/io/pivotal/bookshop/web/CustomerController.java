package io.pivotal.bookshop.web;

import io.pivotal.bookshop.dao.BookJdbcDao;
import io.pivotal.bookshop.dao.CustomerJdbcDao;
import io.pivotal.bookshop.domain.BookMaster;
import io.pivotal.bookshop.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("customer")
public class CustomerController {
    private Logger logger = LoggerFactory.getLogger("CustomerController");

    private CustomerJdbcDao customerDao;
    private BookJdbcDao bookDao;

    @Autowired
    public CustomerController(CustomerJdbcDao dao) {
        this.customerDao = dao;
    }

    @GetMapping("/")
    public String home(@SessionAttribute(value = "customer", required = false) Customer customer, Model model) {
        logger.info("In home()");
        if (customer == null) {
            logger.info("No customer found");
            return "enterCustomer";
        } else {
            model.addAttribute("customer", customer);
            logger.info("Found customer in session: " + customer);
            return "displayCustomer";
        }
    }

    @GetMapping("/enterCustomer")
    public String enterCustomer() {
        return "enterCustomer";
    }

    @PostMapping("/changeCustomer")
    public String changeCustomer(@RequestParam String customerNumber, @CookieValue (name="APP_SESSION_ID", required = false) String sessionId, Model model) {
        logger.info("In changeCustomer() processing customer number: " + customerNumber);
        logger.info("APP_SESSION_ID = " + sessionId);
        Customer c = loadCustomer(customerNumber);
        if (c != null) {
            model.addAttribute("customer", loadCustomer(customerNumber));
            model.addAttribute("books", loadBooks(customerNumber));
            model.addAttribute("sessionId", sessionId);
            return "displayCustomer";
        } else {
            logger.info("Customer not found for customerNumber: " + customerNumber);
            return "enterCustomer";
        }
    }

    private Customer loadCustomer(String customerNumber) {
        Customer cust = customerDao.getCustomer(new Integer(customerNumber));
        logger.info("Loaded customer: " + cust);
        return cust;
    }

    private List<BookMaster> loadBooks(String customerNumber) {
        List<BookMaster> books = new ArrayList<>();
        Customer cust = customerDao.getCustomer(new Integer(customerNumber));
        if(cust.getMyBookOrders()!=null) {
            cust.getMyBookOrders().forEach(
                    (Integer orderId) -> {
                        books.add(bookDao.getBook(orderId));
                    });
        }
        logger.info("Loaded books: " + books.toString());
        return books;
    }
}
