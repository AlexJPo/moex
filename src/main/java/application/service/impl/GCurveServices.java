package application.service.impl;

import application.entity.GCurveModel;
import application.repository.IGCurveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GCurveServices {
    @Autowired
    private IGCurveRepository repository;

    public void addCurve(GCurveModel curve) {
        repository.saveAndFlush(curve);
    }

    public void updateCurve(GCurveModel curve) {
        repository.saveAndFlush(curve);
    }

    public GCurveModel findCurveByDate(String date) {
        GCurveModel result = repository.findCurveByDate(date);
        return result;
    }
}
