package com.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.dao.UserDao;
import com.blog.model.User;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserController {
    private UserDao userDAO;

    @Resource
    public void setUserDAO(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    private static void addCookie(String name, String value, int maxAge, HttpServletResponse response) throws Exception {
        //���cookie����
        Cookie cookie = new Cookie(name, URLEncoder.encode(value.trim(), "UTF-8"));
        cookie.setMaxAge(maxAge);// ����Ϊ10��
        cookie.setPath("/");
        System.out.println("�����" + name);
        response.addCookie(cookie);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "login";
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> validate(@RequestBody User user, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        User user1 = userDAO.findById(user.getUid());
        //��¼�ɹ�����user��ʧ�ܷ���null
        if (user1 == null || !user1.getUsername().equals(user.getUsername())) {
            map.put("result", "�û�������ȷ��");
            return map;
        } else if (!user1.getPassword().equals(user.getPassword())) {
            map.put("result", "���벻��ȷ��");
            return map;
        } else {
            map.put("result", "SUCCESS");
            addCookie("uid", user1.getUid().toString(), 10 * 24 * 60 * 60, response);
            addCookie("username", user1.getUsername(), 10 * 24 * 60 * 60, response);
            return map;
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> register(@RequestBody User user, HttpServletResponse response) throws Exception {
        Map<String, String> result = new HashMap<String, String>();
        userDAO.save(user);
        addCookie("uid", user.getUid().toString(), 10 * 24 * 60 * 60, response);
        addCookie("username", user.getUsername(), 10 * 24 * 60 * 60, response);
        result.put("result", "SUCCESS");
        return result;
    }

    @RequestMapping(value = "/account/{uid}")
    public String account(@PathVariable("uid") Integer uid, ModelMap modelMap) {
        User user = userDAO.findById(uid);
        modelMap.addAttribute("user", user);
        return "account";
    }

    @RequestMapping(value = "/changePasswd/{uid}", method = RequestMethod.POST)
    public String changePasswd(@PathVariable("uid") Integer uid, String newPassword) {
        User user = userDAO.findById(uid);
        user.setPassword(newPassword);
        userDAO.save(user);
        return "homePage";
    }

    @RequestMapping(value = "/logout/{uid}")
    public String logout(@PathVariable("uid") Integer uid, HttpServletResponse response) throws Exception {
        User user = userDAO.findById(uid);
        addCookie("uid", user.getUid().toString(), 0, response);
        addCookie("username", user.getUsername(), 0, response);
        return "redirect:/";
    }
}