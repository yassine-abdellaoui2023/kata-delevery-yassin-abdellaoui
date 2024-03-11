package fr.carrefour.delivery.repositories;

import java.util.List;
import java.util.Optional;

import fr.carrefour.delivery.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = """
      select t from Token t inner join Client u\s
      on t.user.id = u.idClient\s
      where u.idClient = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(Integer id);

  Optional<Token> findByToken(String token);
}