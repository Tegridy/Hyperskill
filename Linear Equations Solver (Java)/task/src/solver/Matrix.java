package solver;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private List<Row> sysEq;
    private List<String> swapHistory;

    public Matrix(List<Row> sysEq) {
        this.sysEq = sysEq;
        this.swapHistory = new ArrayList<>();
    }

    public void plus(int idxImRow, int idxMdfyRow){
        Row imRow = sysEq.get(idxImRow);
        Row mdfyRow = sysEq.get(idxMdfyRow);
        if(imRow.getCoeffs().get(idxImRow) != 0) {
            double factor = (-1) * mdfyRow.getCoeffs().get(idxImRow) / imRow.getCoeffs().get(idxImRow);
            List<Double> result = new ArrayList<>();
            for (int i = 0; i < imRow.getCoeffs().size(); i++) {
                result.add(i, mdfyRow.getCoeffs().get(i) + imRow.multiply(factor).get(i));
            }
            sysEq.set(idxMdfyRow, new Row(result, idxMdfyRow));
        }
    }

    public boolean toDiagonalView(int i){
        if(sysEq.get(i).getCoeffs().get(i) != 0){
            return  sysEq.get(i).toDiagonalView();
        }else{
            if(searchNonNullElem(i)) {
                return sysEq.get(i).toDiagonalView();
            }
        }
        return false;
    }

    private boolean searchNonNullElem(int i){
        for(int j = i + 1; j < sysEq.size(); j++){
            if(sysEq.get(j).getCoeffs().get(i) != 0){
                swapRows(i, j);
                return true;
            }
        }
        for(int j = i + 1; j < sysEq.get(i).getCoeffs().size() - 1; j++){
            if(sysEq.get(i).getCoeffs().get(j) != 0){
                swapColumn(i, j);
                return true;
            }
        }
        for(int j = i + 1; j < sysEq.size(); j++){
            for(int k = 0; k < i; k++){
                if(sysEq.get(j).getCoeffs().get(k) != 0){
                    swapRows(i, j);
                    swapColumn(i, k);
                    return true;
                }
            }
        }
        return false;
    }

    private void swapRows(int i, int j){
        Row tempI;
        Row tempJ;
        tempI = sysEq.get(i);
        tempJ = sysEq.get(j);
        tempI.setIdxNoNullElem(j);
        tempJ.setIdxNoNullElem(i);
        sysEq.set(i, tempJ);
        sysEq.set(j, tempI);
    }

    private void swapColumn(int i, int j){
        double tmp;
        for(Row r: sysEq){
            tmp = r.getCoeffs().get(i);
            r.getCoeffs().set(i, r.getCoeffs().get(j));
            r.getCoeffs().set(j, tmp);
        }
        swapHistory.add(i + "<-->" + j);
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        for(Row r : sysEq){
            for(Double d: r.getCoeffs()){
                result.append(d).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public String getSolution(){
        List<Double> answer = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        double tmp;
        int idxAnswer = sysEq.get(0).getCoeffs().size() - 1;
        for(int i = 0; i < idxAnswer; i ++){
            answer.add(i, sysEq.get(i).getCoeffs().get(idxAnswer));
        }
        for(String s : swapHistory){
            String[] record = s.split("<-->");
            tmp = answer.get(Integer.parseInt(record[0]));
            answer.set(Integer.parseInt(record[0]), answer.get(Integer.parseInt(record[1])));
            answer.set(Integer.parseInt(record[1]), tmp);
        }
        for(Double d: answer){
            sb.append(d).append("\n");
        }
        return sb.toString();
    }

    public boolean isExistsSolution(){
        for(Row r : sysEq){
            if(r.getSumCoef() == 0 && r.getFreeCoef() != 0){
                return false;
            }
        }
        return true;
    }

    public boolean isInfSolutions(){
        int cntSignEqua = sysEq.size();
        for(Row r : sysEq){
            if(r.getSumCoef() == 0 && r.getFreeCoef() == 0){
                cntSignEqua --;
            }
        }
        int cntVars = sysEq.get(0).getCoeffs().size() - 1;
        return cntSignEqua != cntVars;
    }
}
