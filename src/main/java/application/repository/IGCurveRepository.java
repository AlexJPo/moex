package application.repository;

import application.entity.GCurveModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IGCurveRepository extends JpaRepository<GCurveModel, Long>{
  /*  void addCurve(GCurveModel curve);

    void updateCurve(GCurveModel curve);

    List<GCurveModel> getAll();
*/
    @Query("SELECT c FROM GCurveModel c WHERE c.tradedate = :date")
    GCurveModel findCurveByDate(@Param("date") String date);
}
