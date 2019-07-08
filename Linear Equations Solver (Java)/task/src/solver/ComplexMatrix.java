package solver;

import java.util.ArrayList;
import java.util.List;

public class ComplexMatrix {
    private List<ComplexRow> sysEq;
    private List<String> swapHistory;

    public ComplexMatrix(List<ComplexRow> sysEq) {
        this.sysEq = sysEq;
        this.swapHistory = new ArrayList<>();
    }

    public void plus(int idxImRow, int idxMdfyRow){
        ComplexRow imRow = sysEq.get(idxImRow);
        ComplexRow mdfyRow = sysEq.get(idxMdfyRow);
        if(!imRow.getCoeffs().get(idxImRow).isNull()) {
            ComplexNumber factor = mdfyRow.getCoeffs().get(idxImRow).divide(imRow.getCoeffs().get(idxImRow)).multiply(new ComplexNumber(-1, 0));
            List<ComplexNumber> result = new ArrayList<>();
            for (int i = 0; i < imRow.getCoeffs().size(); i++) {
                result.add(i, mdfyRow.getCoeffs().get(i).add(imRow.multiply(factor).get(i)));
            }
            sysEq.set(idxMdfyRow, new ComplexRow(result, idxMdfyRow));
        }
    }

    public boolean toDiagonalView(int i){
        if(!sysEq.get(i).getCoeffs().get(i).isNull()){
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
            if(!sysEq.get(j).getCoeffs().get(i).isNull()){
                swapRows(i, j);
                return true;
            }
        }
        for(int j = i + 1; j < sysEq.get(i).getCoeffs().size() - 1; j++){
            if(!sysEq.get(i).getCoeffs().get(j).isNull()){
                swapColumn(i, j);
                return true;
            }
        }
        for(int j = i + 1; j < sysEq.size(); j++){
            for(int k = 0; k < i; k++){
                if(!sysEq.get(j).getCoeffs().get(k).isNull()){
                    swapRows(i, j);
                    swapColumn(i, k);
                    return true;
                }
            }
        }
        return false;
    }

    private void swapRows(int i, int j){
        ComplexRow tempI;
        ComplexRow tempJ;
        tempI = sysEq.get(i);
        tempJ = sysEq.get(j);
        tempI.setIdxNoNullElem(j);
        tempJ.setIdxNoNullElem(i);
        sysEq.set(i, tempJ);
        sysEq.set(j, tempI);
    }

    private void swapColumn(int i, int j){
        ComplexNumber tmp;
        for(ComplexRow r: sysEq){
            tmp = r.getCoeffs().get(i);
            r.getCoeffs().set(i, r.getCoeffs().get(j));
            r.getCoeffs().set(j, tmp);
        }
        swapHistory.add(i + "<-->" + j);
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        for(ComplexRow r : sysEq){
            for(ComplexNumber d: r.getCoeffs()){
                result.append(d).append("\t").append("\t");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public String getSolution(){
        List<ComplexNumber> answer = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        ComplexNumber tmp;
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
        for(ComplexNumber d: answer){
            sb.append(d).append("\n");
        }
        return sb.toString();
    }

    public boolean isExistsSolution(){
        for(ComplexRow r : sysEq){
            if(r.getAbsSumCoef() == 0 && !r.getFreeCoef().isNull()){
                return false;
            }
        }
        return true;
    }

    public boolean isInfSolutions(){
        int cntSignEqua = sysEq.size();
        for(ComplexRow r : sysEq){
            if(r.getAbsSumCoef() == 0 && r.getFreeCoef().isNull()){
                cntSignEqua --;
            }
        }
        int cntVars = sysEq.get(0).getCoeffs().size() - 1;
        return cntSignEqua != cntVars;
    }
}
