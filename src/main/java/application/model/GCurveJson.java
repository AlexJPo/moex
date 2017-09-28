package application.model;

import java.util.List;

public class GCurveJson {
    private Params params;

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }


    public static class Params {
        private List<String> columns;
        private List<List<Object>> data;

        public List<List<Object>> getData() {
            return data;
        }

        public void setData(List<List<Object>> data) {
            this.data = data;
        }

        public List<String> getColumns() {
            return columns;
        }

        public void setColumns(List<String> columns) {
            this.columns = columns;
        }

        @Override
        public String toString() {
            return "Params{" +
                    "columns=" + columns +
                    ", data=" + data +
                    '}';
        }
    }


}
