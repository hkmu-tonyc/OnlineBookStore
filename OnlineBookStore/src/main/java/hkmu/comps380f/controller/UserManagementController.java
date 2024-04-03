package hkmu.comps380f.controller;

import hkmu.comps380f.dao.UserManagementService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserManagementController {
    @Resource
    UserManagementService umService;
    @GetMapping({"", "/", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("siteUsers", umService.getSiteUsers());
        return "listUser";
    }
    public static class Form {
        private String username;
        private String password;
        private String fullname;
        private String email;
        private String address;
        private String[] roles;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }
    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("addUser", "siteUser", new Form());
    }
    @PostMapping("/create")
    public String create(Form form) throws IOException {
        umService.createSiteUser(form.getUsername(),
                form.getPassword(), form.getFullname(), form.getEmail(), form.getAddress(), form.getRoles());
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username) {
        umService.delete(username);
        return "redirect:/user/list";
    }
}
