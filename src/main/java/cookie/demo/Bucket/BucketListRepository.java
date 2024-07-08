package cookie.demo.Bucket;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketListRepository extends JpaRepository <Bucket, Long> {
    Bucket findTopByOrderByIdDesc();
}
