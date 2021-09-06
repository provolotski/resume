package by.minsk.resume.repository.storage;

import by.minsk.resume.entity.ProfileRestore;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRestoreRepository extends CrudRepository<ProfileRestore, Long> {
    ProfileRestore findByToken(String token);
}
