package hkmu.comps380f.dao;

import hkmu.comps380f.model.CoverPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CoverPhotoRepository extends JpaRepository<CoverPhoto, UUID> {
}
