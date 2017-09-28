package application.service.impl;

import application.entity.GCurveModel;
import application.repository.IGCurveRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class GCurveServices {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IGCurveRepository repository;

    public void addCurve(GCurveModel curve) {
        repository.saveAndFlush(curve);
        //Session session = entityManager.unwrap(Session.class);
        //session.save(curve);
    }

    public void updateCurve(GCurveModel curve) {
        Session session = entityManager.unwrap(Session.class);
        session.update(curve);
    }

    public List<GCurveModel> getAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GCurveModel> query = builder.createQuery(GCurveModel.class);

        Root<GCurveModel> root = query.from(GCurveModel.class);
        query.select(root);

        return entityManager.createQuery(query).getResultList();
    }

    public GCurveModel findCurveByDate(String date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        GCurveModel result = repository.findCurveByDate(date);
        return result;

        /*CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GCurveModel> query = builder.createQuery(GCurveModel.class);

        Root<GCurveModel> root = query.from(GCurveModel.class);
        query.select(root);

        try {
            query.where(builder.equal(root.get("tradedate"), simpleDateFormat.parse(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return entityManager.createQuery(query).getSingleResult();*/
    }

}
