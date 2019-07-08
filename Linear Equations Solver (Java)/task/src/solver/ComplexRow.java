package solver;

import java.util.ArrayList;
import java.util.List;

public class ComplexRow {
    private List<ComplexNumber> coeffs;
    private int idxNoNullElem;

    public List<ComplexNumber> getCoeffs() {
        return coeffs;
    }

    public void setIdxNoNullElem(int idxNoNullElem) {
        this.idxNoNullElem = idxNoNullElem;
    }

    public ComplexRow(List<ComplexNumber> coeffs, int idxNoNullElem) {
        this.coeffs = coeffs;
        this.idxNoNullElem = idxNoNullElem;
    }

    public List<ComplexNumber> multiply(ComplexNumber factor){
        List<ComplexNumber> result = new ArrayList<>();
        ComplexNumber complexNumber;
        for(int i = 0; i < coeffs.size(); i++){
            complexNumber = new ComplexNumber(coeffs.get(i));
            result.add(i, complexNumber.multiply(factor));
        }
        return result;
    }

    public boolean toDiagonalView(){
        ComplexNumber factor = new ComplexNumber(coeffs.get(idxNoNullElem));
        for(int i = 0; i < coeffs.size(); i++){
            coeffs.set(i, coeffs.get(i).divide(factor));
        }
        return true;
    }

    public double getAbsSumCoef(){
        double sum = 0;
        for(int i = 0; i < this.coeffs.size() - 1; i++){
            sum = sum + this.coeffs.get(i).abs();
        }
        return sum;
    }

    public ComplexNumber getFreeCoef(){
        return this.coeffs.get(this.coeffs.size() - 1);
    }
}
