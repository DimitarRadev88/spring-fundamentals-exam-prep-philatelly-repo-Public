package com.philately.stamp.dao;

import com.philately.stamp.model.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StampRepository extends JpaRepository<Stamp, UUID> {

    List<Stamp> findAllByOwnerId(UUID userId);

    @Query(
            """
                    FROM Stamp s
                    WHERE s.owner.id != :ownerId
                    AND s NOT IN (SELECT u.purchasedStamps FROM User u)
                    """
//                    AND st.id NOT IN (SELECT id FROM p_s)
    )
    List<Stamp> findAllByOwnerIdNotAndNotPurchased(UUID ownerId);
}