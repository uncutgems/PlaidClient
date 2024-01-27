package com.producer.plaidclient.repository;

import com.producer.plaidclient.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    @Modifying
    @Query("update User u set u.actionRequired = :action_required where u.itemId = :item_id")
    void notifyValidityByItemId(@Param(value = "item_id") String itemId,
                                @Param(value = "action_required") String actionRequired);

}
