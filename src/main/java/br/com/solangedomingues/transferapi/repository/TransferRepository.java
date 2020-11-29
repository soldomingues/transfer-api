package br.com.solangedomingues.transferapi.repository;

import br.com.solangedomingues.transferapi.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query("SELECT t FROM Transfer t WHERE t.originAccount = :numberAccount or t.destinationAccount = :numberAccount ORDER BY t.date")
    List<Transfer> findAllByAccount(@Param("numberAccount") Long numberAccount);

}
