package by.minsk.resume.repository.storage;

import by.minsk.resume.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ProfileRepository extends PagingAndSortingRepository<Profile, Long> {
    Profile findByUid(String uid);
    Profile findByEmail(String email);
    Profile findByPhone(String phone);
    int  countByUid(String uid);
    Page<Profile> findAllByCompletedTrue(Pageable pageable);
    List<Profile> findByCompletedFalseAndCreatedBefore(Timestamp oldDate);
}
