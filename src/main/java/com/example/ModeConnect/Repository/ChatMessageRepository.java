package com.example.ModeConnect.Repository;

import com.example.ModeConnect.model.ChatMessage;
import com.example.ModeConnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * Finds messages exchanged between two specific users, ordered by timestamp.
     */
    @Query("SELECT m FROM ChatMessage m WHERE " +
           "(m.sender = :user1 AND m.receiver = :user2) OR " +
           "(m.sender = :user2 AND m.receiver = :user1) " +
           "ORDER BY m.timestamp ASC")
    List<ChatMessage> findConversationBetween(@Param("user1") User user1, @Param("user2") User user2);

    /**
     * Gets a list of unique users who have exchanged messages with the specified user.
     */
    @Query("SELECT DISTINCT CASE WHEN m.sender = :user THEN m.receiver ELSE m.sender END FROM ChatMessage m WHERE " +
           "m.sender = :user OR m.receiver = :user")
    List<User> findContactedUsers(@Param("user") User user);
    
    /**
     * Count unread messages for a specific receiver.
     */
    long countByReceiverAndIsReadFalse(User receiver);
}
