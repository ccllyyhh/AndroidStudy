package com.example.caoliyuan.calculator;

import java.text.DecimalFormat;
import java.util.StringTokenizer;


public class Calculatorv2 {

    //控制DRG按键，true为角度，false为弧度
    public boolean drg_flag = true;
    //π值：3.14
    public double pi=4*Math.atan(1);

    final int MAXLEN = 500;
    /*
     * 计算表达式
     * 从左向右扫描，数字入number栈，运算符入operator栈
     * +-基本优先级为1，×÷基本优先级为2，log ln sin cos tan n!基本优先级为3，√^基本优先级为4
     * 括号内层运算符比外层同级运算符优先级高4
     * 当前运算符优先级高于栈顶压栈，低于栈顶弹出一个运算符与两个数进行运算
     * 重复直到当前运算符大于栈顶
     * 扫描完后对剩下的运算符与数字依次计算
     */
    public String process(String str,Boolean drg) {
        this.drg_flag=drg;
        try{
        str = str.replaceAll("sin", "s");
        str = str.replaceAll("cos", "c");
        str = str.replaceAll("tan", "t");

        int weightPlus = 0, topOp = 0,
                topNum = 0, flag = 1, weightTemp = 0;
        //weightPlus为同一（）下的基本优先级，weightTemp临时记录优先级的变化
        //topOp为weight[]，operator[]的计数器；topNum为number[]的计数器
        //flag为正负数的计数器，1为正数，-1为负数
        int weight[];  //保存operator栈中运算符的优先级，以topOp计数
        double number[];  //保存数字，以topNum计数
        char ch, ch_gai, operator[];//operator[]保存运算符，以topOp计数
        String num;//记录数字，str以+-×÷()sctgl!√^分段，+-×÷()sctgl!√^字符之间的字符串即为数字
        weight = new int[MAXLEN];
        number = new double[MAXLEN];
        operator = new char[MAXLEN];
        String expression = str;
        StringTokenizer expToken = new StringTokenizer(expression,"+-×÷()sctgl!√^");
        int i = 0;
        while (i < expression.length()) {
            ch = expression.charAt(i);
            //判断正负数
            if (i == 0) {
                if (ch == '-')
                    flag = -1;
            } else if(expression.charAt(i-1) == '(' && ch == '-')
                flag = -1;
            //取得数字，并将正负符号转移给数字
            if (ch <= '9' && ch >= '0'|| ch == '.' || ch == 'E') {
                num = expToken.nextToken();
                ch_gai = ch;
                //取得整个数字
                while (i < expression.length() &&
                        (ch_gai <= '9' && ch_gai >= '0'|| ch_gai == '.' || ch_gai == 'E'))
                {
                    ch_gai = expression.charAt(i++);
                }
                //将指针退回之前的位置
                if (i >= expression.length()) i-=1; else {i-=2;}
                if (num.compareTo(".") == 0) number[topNum++] = 0;
                    //将正负符号转移给数字
                else {
                    number[topNum++] = Double.parseDouble(num)*flag;
                    flag = 1;
                }
            }
            //计算运算符的优先级
            if (ch == '(') weightPlus+=4;
            if (ch == ')') weightPlus-=4;
            if (ch == '-' && flag == 1 || ch == '+' || ch == '×'|| ch == '÷' ||
                    ch == 's' ||ch == 'c' || ch == 't' || ch == 'g' ) {
                switch (ch) {
                    //+-的优先级最低，为1
                    case '+':
                    case '-':
                        weightTemp = 1 + weightPlus;
                        break;
                    //x÷的优先级稍高，为2
                    case '×':
                    case '÷':
                        weightTemp = 2 + weightPlus;
                        break;
                    //sincos之类优先级为3
                    case 's':
                    case 'c':
                    case 't':

                        weightTemp = 3 + weightPlus;
                        break;
                    //其余优先级为4

                    default:
                        weightTemp = 4 + weightPlus;
                        break;
                }
                //如果当前优先级大于堆栈顶部元素，则直接入栈
                if (topOp == 0 || weight[topOp-1] < weightTemp) {
                    weight[topOp] = weightTemp;
                    operator[topOp] = ch;
                    topOp++;
                    //否则将堆栈中运算符逐个取出，直到当前堆栈顶部运算符的优先级小于当前运算符
                }else {
                    while (topOp > 0 && weight[topOp-1] >= weightTemp) {
                        switch (operator[topOp-1]) {
                            //取出数字数组的相应元素进行运算
                            case '+':
                                number[topNum-2]+=number[topNum-1];
                                break;
                            case '-':
                                number[topNum-2]-=number[topNum-1];
                                break;
                            case '×':
                                number[topNum-2]*=number[topNum-1];
                                break;
                            //判断除数为0的情况
                            case '÷':
                                if (number[topNum-1] == 0) {

                                    return showError(1 );
                                }
                                number[topNum-2]/=number[topNum-1];
                                break;

                            //计算时进行角度弧度的判断及转换
                            //sin
                            case 's':
                                if(drg_flag == true) {
                                    number[topNum-1] = Math.sin((number[topNum-1]/180)*pi);
                                } else {
                                    number[topNum-1] = Math.sin(number[topNum-1]);
                                }
                                topNum++;
                                break;
                            //cos
                            case 'c':
                                if(drg_flag == true) {
                                    number[topNum-1] = Math.cos((number[topNum-1]/180)*pi);
                                } else {
                                    number[topNum-1] = Math.cos(number[topNum-1]);
                                }
                                topNum++;
                                break;
                            //tan
                            case 't':
                                if(drg_flag == true) {
                                    if((Math.abs(number[topNum-1])/90)%2 == 1) {

                                        return showError(2 );
                                    }
                                    number[topNum-1] = Math.tan((number[topNum-1]/180)*pi);
                                } else {
                                    if((Math.abs(number[topNum-1])/(pi/2))%2 == 1) {

                                        return showError(2 );
                                    }
                                    number[topNum-1] = Math.tan(number[topNum-1]);
                                }
                                topNum++;
                                break;


                        }
                        //继续取堆栈的下一个元素进行判断
                        topNum--;
                        topOp--;
                    }
                    //将运算符如堆栈
                    weight[topOp] = weightTemp;
                    operator[topOp] = ch;
                    topOp++;
                }
            }
            i++;
        }
        //依次取出堆栈的运算符进行运算

        while (topOp>0) {
            //+-x直接将数组的后两位数取出运算
            switch (operator[topOp-1]) {
                case '+':
                    number[topNum-2]+=number[topNum-1];
                    break;
                case '-':
                    number[topNum-2]-=number[topNum-1];
                    break;
                case '×':
                    number[topNum-2]*=number[topNum-1];
                    break;
                //涉及到除法时要考虑除数不能为零的情况
                case '÷':
                    if (number[topNum-1] == 0) {

                        return showError(1 );
                    }
                    number[topNum-2]/=number[topNum-1];
                    break;

                //sin
                case 's':
                    if(drg_flag == true) {
                        number[topNum-1] = Math.sin((number[topNum-1]/180)*pi);
                    } else {
                        number[topNum-1] = Math.sin(number[topNum-1]);
                    }
                    topNum++;
                    break;
                //cos
                case 'c':
                    if(drg_flag == true) {
                        number[topNum-1] = Math.cos((number[topNum-1]/180)*pi);
                    } else {
                        number[topNum-1] = Math.cos(number[topNum-1]);
                    }
                    topNum++;
                    break;
                //tan
                case 't':
                    if(drg_flag == true) {
                        if((Math.abs(number[topNum-1])/90)%2 == 1) {

                            return showError(2 );
                        }
                        number[topNum-1] = Math.tan((number[topNum-1]/180)*pi);
                    } else {
                        if((Math.abs(number[topNum-1])/(pi/2))%2 == 1) {

                            return showError(2 );
                        }
                        number[topNum-1] = Math.tan(number[topNum-1]);
                    }
                    topNum++;
                    break;

            }
            //取堆栈下一个元素计算
            topNum--;
            topOp--;
        }
        //如果是数字太大，提示错误信息
        if(number[0] > 7.3E306) {

            return showError(3 );
        }
        //输出最终结果
        System.out.println(String.valueOf(FP(number[0])));
            return String.valueOf(FP(number[0]));
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println( "表达式错误");
            return "表达式错误";
        }
    }
    /*
         * FP = floating point 控制小数位数，达到精度
         * 否则会出现 0.6-0.2=0.39999999999999997的情况，用FP即可解决，使得数为0.4
         * 本格式精度为15位
         */
    public double FP(double n) {
        //NumberFormat format=NumberFormat.getInstance();  //创建一个格式化类f
        //format.setMaximumFractionDigits(18);    //设置小数位的格式
        DecimalFormat format = new DecimalFormat("0.#############");
        return Double.parseDouble(format.format(n));
    }



    /*
     * 错误提示，按了"="之后，若计算式在process()过程中，出现错误，则进行提示
     */
    public String  showError(int code ) {
        String message="";
        switch (code) {
            case 1:
                message = "零不能作除数";
                break;
            case 2:
                message = "函数格式错误";
                break;
            case 3:
                message = "值太大了，超出范围";
        }
        System.out.println(message);
        return message;
    }



    public static void main(String[] args) {
        Calculatorv2 calculatorv2=new Calculatorv2();
//        calculatorv2.process("1+2×3+sn90");
    }


}

