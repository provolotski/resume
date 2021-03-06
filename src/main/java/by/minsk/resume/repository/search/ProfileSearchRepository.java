package by.minsk.resume.repository.search;

import by.minsk.resume.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProfileSearchRepository extends ElasticsearchRepository<Profile,Long> {

    Page<Profile> findByObjectiveLikeOrSummaryLikeOrInfoOrPracticsCompanyLikeOrPracticsPositionLike(
            String objective, String summary, String info, String practicsCompany,String practicPosition, Pageable pageable);

}
