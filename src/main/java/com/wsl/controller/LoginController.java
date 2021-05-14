package com.wsl.controller;

import com.wsl.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String login(User user, HttpServletRequest request) {
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            return "请输入用户名和密码！";
        }
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            HttpSession session = request.getSession();
            User sysUser = null;

            if (user.getUserName().equals("zhangsan")) {
                sysUser = new User("2", "zhangsan", "123456");
            }

            if (user.getUserName().equals("wsl")) {
                sysUser = new User("1", "wsl", "123456");
            }

            session.setAttribute("user_in_session", sysUser);
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
        } catch (UnknownAccountException e) {
            log.error("用户名不存在！", e);
            return "用户名不存在！";
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
            return "没有权限";
        }
        return "login success";
    }

    @RequiresRoles("admin")
    @GetMapping("/admin")
    public String admin() {
        return "admin success!";
    }

    @RequiresPermissions("query")
    @GetMapping("/index")
    public String index(HttpSession session) {
        User sysUser = (User) session.getAttribute("user_in_session");
        System.out.println(sysUser);
        return "index success!" + sysUser;
    }

    @RequiresPermissions("add")
    @GetMapping("/add")
    public String add(HttpSession session) {
        User sysUser = (User) session.getAttribute("user_in_session");
        System.out.println(sysUser);
        return "add success!" + sysUser;
    }
}
