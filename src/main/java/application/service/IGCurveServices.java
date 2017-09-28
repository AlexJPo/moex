package application.service;

import application.entity.GCurveModel;

import java.util.List;

public interface IGCurveServices {
    void addCurve(GCurveModel curve);

    void updateCurve(GCurveModel curve);

    List<GCurveModel> getAll();

    GCurveModel findCurveByDate(String date);
}
