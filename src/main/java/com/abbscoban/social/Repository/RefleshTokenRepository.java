package com.abbscoban.social.Repository;

import com.abbscoban.social.model.RefleshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefleshTokenRepository extends JpaRepository<RefleshToken,Long> {

    Optional<RefleshToken> findByRefleshToken(String refleshToken);
}
