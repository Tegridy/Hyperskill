package solver;

import java.text.DecimalFormat;

public class ComplexNumber {
    private double re;
    private double im;

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }
    public ComplexNumber(ComplexNumber complexNumber){
        this.re = complexNumber.re;
        this.im = complexNumber.im;
    }
    public ComplexNumber(String str){
        if(!str.contains("+") && !str.contains("-")) {//1
            if(str.contains("i")){
                this.re = 0;
                this.im = Double.parseDouble(str.replace("i", (str.equals("i") ? "1" : str.equals("-i") ? "1" : "")));
            } else {
                this.re = Double.parseDouble(str);
                this.im = 0;
            }
        }else{
            int minus1 = str.indexOf("-"); //0, n, -1
            int minus2 = str.indexOf("-", 1); //n, -1
            int plus = str.indexOf("+"); //n, -1
            if(minus1 > 0){//2
                this.re = Double.parseDouble(str.substring(0, minus1));
                if(str.substring(minus1).equals("-i")){
                    this.im = -1;
                }else{
                    this.im = Double.parseDouble(str.substring(minus1).replace("i", ""));
                }
            }else if(plus > 0 &&  minus1 == -1){//3
                this.re = Double.parseDouble(str.substring(0, plus));
                if(str.substring(plus + 1).equals("i")){
                    this.im = 1;
                }else{
                    this.im = Double.parseDouble(str.substring(plus + 1).replace("i", ""));
                }
            }else if(minus1 == 0 && minus2 == -1 && plus == -1){ //4
                if(str.contains("i")){
                    this.re = 0;
                    this.im = Double.parseDouble(str.replace("i", str.equals("-i") ? "1" : ""));
                } else {
                    this.re = Double.parseDouble(str);
                    this.im = 0;
                }
            }else if(minus1 == 0 && plus > 0){//5
                this.re = Double.parseDouble(str.substring(0, plus));
                if(str.substring(plus + 1).equals("i")){
                    this.im = 1;
                }else {
                    this.im = Double.parseDouble(str.substring(plus + 1).replace("i", ""));
                }
            }else if(minus1 == 0 && minus2 > 0){ //6
                this.re = Double.parseDouble(str.substring(0, minus2));
                if (str.substring(minus2).equals("-i")){
                    this.im = -1;
                }else {
                    this.im = Double.parseDouble(str.substring(minus2).replace("i", ""));
                }
            }
        }


    }

    public ComplexNumber add(ComplexNumber complexNumber){
        this.re = this.re + complexNumber.re;
        this.im = this.im + complexNumber.im;
        return this;
    }

    public ComplexNumber multiply(ComplexNumber complexNumber){
        double re = this.re * complexNumber.re - this.im * complexNumber.im;
        double im = this.re * complexNumber.im + this.im * complexNumber.re;
        /*this.re = re;
        this.im = im;*/
        return new ComplexNumber(re, im);
    }

    public void conjugate(){
        this.im = (-1) * this.im;
    }

    public ComplexNumber divide(ComplexNumber complexNumber){
        double re = (this.re * complexNumber.re + this.im * complexNumber.im) / (Math.pow(complexNumber.re, 2) + Math.pow(complexNumber.im, 2));
        double im = (this.im * complexNumber.re - this.re * complexNumber.im) / (Math.pow(complexNumber.re, 2) + Math.pow(complexNumber.im, 2));
        this.im = im;
        this.re = re;
        return this;
    }

    public String toString(){
        String answer;
        //DecimalFormat f = new DecimalFormat("0.##");
        if (this.im == 0){
            return String.valueOf(this.re);
        }
        if (this.re == 0){
            return (this.im == 1 ? "" : this.im == -1 ? "-" : String.valueOf(this.im)) + 'i';
        }
        return String.valueOf(this.re) + (this.im > 0 ? '+': '-') + (Math.abs(this.im) == 1 ? "" : Math.abs(this.im)) + 'i';
    }

    public boolean isNull(){
        if(this.re == 0d  && this.im == 0d) {
            return true;
        }
        return false;
    }

    public double abs(){
        return this.re*this.re + this.im*this.im;
    }
}
