package hkmu.comps380f.dao;

import hkmu.comps380f.model.SiteUser;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserManagementService {
    @Resource
    private SiteUserRepository suRepo;
    @Transactional
    public List<SiteUser> getSiteUsers() {
        return suRepo.findAll();
    }
    @Transactional
    public void delete(String username) {
        SiteUser siteUser = suRepo.findById(username).orElse(null);
        if (siteUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        suRepo.delete(siteUser);
    }
    @Transactional
    public void createSiteUser(String username, String password, String fullname, String email, String address, String[] roles) {
        SiteUser user = new SiteUser(username, password, fullname, email, address, roles);
        suRepo.save(user);
    }
}
