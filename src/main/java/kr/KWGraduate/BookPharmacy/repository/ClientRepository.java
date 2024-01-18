package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,String> {

    @Query("select m from Client m where m.nickname = :nickname")
    Optional<Client> findByNickname(@Param("nickname") String nickname);

    @Query("select m from Client m where m.email = :email")
    Optional<Client> findByEmail(@Param("email") String email);


}