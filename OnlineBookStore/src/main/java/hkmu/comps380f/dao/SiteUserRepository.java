package hkmu.comps380f.dao;

import hkmu.comps380f.model.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUser, String> {
}

