package solver;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private List<Double> coeffs;
    private int idxNoNullElem;

    public List<Double> getCoeffs() {
        return coeffs;
    }

    public void setIdxNoNullElem(int idxNoNullElem) {
        this.idxNoNullElem = idxNoNullElem;
    }

    public Row(List<Double> coeffs, int idxNoNullElem) {
        this.coeffs = coeffs;
        this.idxNoNullElem = idxNoNullElem;
    }

    public List<Double> multiply(double factor){
        List<Double> result = new ArrayList<>();
        for(int i = 0; i < coeffs.size(); i++){
            result.add(i, coeffs.get(i) * factor);
        }
        return result;
    }

    public boolean isSolved(){
        double sum = 0.0;
        for(int i = 0; i < coeffs.size() - 1; i++){
            sum = sum + (i == idxNoNullElem? 0 : coeffs.get(i));
        }
        if(sum == 0.0 && coeffs.get(idxNoNullElem) != 0.0){
            return true;
        }else{
            return false;
        }
    }

    public boolean toDiagonalView(){
        double factor = coeffs.get(idxNoNullElem);
        for(int i = 0; i < coeffs.size(); i++){
            coeffs.set(i, coeffs.get(i) / factor);
        }
        return true;
    }

    public double getSumCoef(){
        double sum = 0;
        for(int i = 0; i < this.coeffs.size() - 1; i++){
            sum = sum + this.coeffs.get(i);
        }
        return sum;
    }

    public double getFreeCoef(){
        return this.coeffs.get(this.coeffs.size() - 1);
    }


}
