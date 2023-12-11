package com.exaze.ritika.travel.portal.repository;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import com.exaze.ritika.travel.portal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelRequestRepository extends JpaRepository<TravelRequest, Long> {
    @Query("SELECT tr FROM TravelRequest tr WHERE tr.manager.id = :managerId AND tr.status = 'SUBMITTED'")
    List<TravelRequest> findPendingTravelRequestsByManagerId(@Param("managerId") Long managerId);

    @Query("SELECT tr FROM TravelRequest tr WHERE tr.status = 'APPROVEDBYMANAGER'")
    List<TravelRequest>findManagerApprovedTravelRequests();

    @Query("SELECT tr FROM TravelRequest tr WHERE tr.status = 'APPROVEDBYADMINTEAM'")
    List<TravelRequest>findAdminApprovedTravelRequests();
    Optional<TravelRequest> findTravelRequestById(Long id);
    List<TravelRequest> findByUser(User user);

    @Query("SELECT tr FROM TravelRequest tr WHERE tr.user.id = :userId AND tr.status <> 'DRAFT'")
    List<TravelRequest> findTravelRequestsNotDraft(@Param("userId") Long userId);


    @Query("SELECT tr FROM TravelRequest tr WHERE tr.user.id = :userId AND tr.status = 'DRAFT'")
    List<TravelRequest> findByUserIdAndStatus(@Param("userId") Long userId);

    @Query("SELECT tr FROM TravelRequest tr WHERE tr.user.id = :userId AND tr.status = 'REVIEW'")
    List<TravelRequest> findByUserIdAndReviewStatus(@Param("userId") Long userId);

}
