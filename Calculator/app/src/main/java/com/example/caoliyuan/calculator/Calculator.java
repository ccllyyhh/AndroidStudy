package com.example.caoliyuan.calculator;

import java.util.ArrayList;
import java.util.Stack;
import java.math.BigDecimal;

import static java.lang.Math.floor;
import static java.math.BigDecimal.ROUND_HALF_DOWN;

public class Calculator {
	//字符串分割
	public ArrayList<String> inputCut(String input) {
		ArrayList<String> inputAry=new ArrayList<String>();
		int left=0,right=0;
		char[] inputchar =new char[input.length()];
		input.getChars(0, input.length(), inputchar, 0);
		for (int i = 0; i < inputchar.length; i++) {

			//System.out.println(inputchar[i]);
			if(inputchar[i]=='+'||inputchar[i]=='-'||inputchar[i]=='*'
					||inputchar[i]=='/'||inputchar[i]=='('||inputchar[i]==')'){
				right=i;
				//System.out.println(input.substring(left, right));
				if(left!=right)inputAry.add(input.substring(left, right));
				inputAry.add(input.substring(right,right+1));
				left=i+1;
			}
		}
		if(left!=inputchar.length)inputAry.add(input.substring(left));
		return inputAry;
	}

	public boolean isOpt(String s) {
		if(s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/")){
			return true;
		}else {
			return false;
		}
	}
	//表达式检测
	public boolean ExpressionCheck(ArrayList<String> expression) {
		//判断符号左右是否有数
		for (int i = 0; i < expression.size(); i++) {
			String s=expression.get(i);
			//对于减号，左边可以不是数字，直接加零
			if(s.equals("-")){
				if(i==0||isOpt(expression.get(i-1))){
					expression.add(i,"0");
				}
			}
			s=expression.get(i);
			if(s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/")){
				if (i==0||i==expression.size()-1) {
					return false;
				}else {
					String left=expression.get(i-1);
					String right=expression.get(i+1);
					if(isOpt(left)||isOpt(right)){
						return false;
					}
				}
			}
		}
		//括号检测
//		int lastBindex=-1;
//		for(int i=0;i<expression.size();i++){
//			if(expression.get(i).equals("(")){
//				lastBindex=i;
//			};
//		}
		return true;
	}
	//中缀转后缀
	public ArrayList<String> infixExpression2PostfixExpression(ArrayList<String> infixExp) {
		ArrayList<String> postfixExp=new ArrayList<String>();
		Stack<String> stack=new Stack<String>();
		//栈顶元素
		String top;
		String e;
		//'('的数量
		int leftBrackets=0;
		//遍历
		for (int i = 0; i < infixExp.size(); i++) {
			String j=infixExp.get(i);
			//非符号(即数字)不用进栈，只需依次记录即可
			if(!(j.equals("+")||j.equals("-")||j.equals("*")||j.equals("/")||j.equals("(")||j.equals(")"))){
//	    			System.out.println("number:"+j);
				postfixExp.add(j);
			}
			//拿栈顶元素
			top=stack.empty()?"":stack.peek();
			switch (j) {
				case "+":
					//同级则只出栈一个
					if(top.equals("+")||top.equals("-")){
						postfixExp.add(stack.pop());
					}
					//符号等级低于栈顶的,则除自己外其余全部出栈
					else if(top.equals("*")||top.equals("/")){
						e=stack.peek();
						while (!stack.empty()&&!e.equals("(")) {
							postfixExp.add(stack.pop());
							if(!stack.empty()){
								e=stack.peek();
							}
						}
					}
					//该运算符入栈
					stack.push(j);
					break;
				case "-":
					//同级则只出栈一个
					if(top.equals("+")||top.equals("-")){
						postfixExp.add(stack.pop());
					}
					//符号等级低于栈顶的,则除自己外其余全部出栈
					else if(top.equals("*")||top.equals("/")){
						e=stack.peek();
						while (!stack.empty()&&!e.equals("(")) {
							postfixExp.add(stack.pop());
							if(!stack.empty()){
								e=stack.peek();
							}
						}
					}
					//该运算符入栈
					stack.push(j);
					break;
				case "*":
					//同级则只出栈一个
					if(top.equals("*")||top.equals("/")){
						postfixExp.add(stack.pop());
					}
					//该运算符入栈
					stack.push(j);
					break;
				case "/":
					//同级则只出栈一个
					if(top.equals("*")||top.equals("/")){
						postfixExp.add(stack.pop());
					}
					//该运算符入栈
					stack.push(j);
					break;
				case "(":
					stack.push(j);
					leftBrackets++;
					break;
				case ")":
					//如果左右括号不匹配则当无效字符处理
					if(leftBrackets<=0){
						break;
					}
					//若出现左右括号，则括号内的内容全部出栈
					String ee;
					ee=stack.peek();
					while (!ee.equals("(")) {
						postfixExp.add(stack.pop());
						ee=stack.peek();
					}
					//弹出'(',并将数量减一
					e=stack.pop();
					leftBrackets--;
					break;

				default:
					break;
			}
		}
		//遍历完则弹出所有操作符
		while(!stack.empty()){
			postfixExp.add(stack.pop());
		}
		return postfixExp;
	}

	//计算表达式
	public double calculateExpression(ArrayList<String> postfixExp) {
		double result=0;
		Stack<BigDecimal> stack=new Stack<BigDecimal>();
		for (int i = 0; i <postfixExp.size(); i++) {
			String j=postfixExp.get(i);
			if(!(j.equals("+")||j.equals("-")||j.equals("*")||j.equals("/")||j.equals("(")||j.equals(")"))){
				stack.push(new BigDecimal(j));
			}else {
				BigDecimal number1,number2,re;
				re=new BigDecimal(0);
				number2=stack.pop();
				number1=stack.pop();
				switch (j) {
					case "+":

						re=number1.add(number2);
						break;
					case "-":
						re=number1.subtract(number2);
						break;
					case "*":
						re=number1.multiply(number2);
						break;
					case "/":
						re=number1.divide(number2,10,ROUND_HALF_DOWN);
						break;

					default:
						break;
				}
				stack.push(re);
			}
		}
		result=Double.valueOf(stack.peek().toString());
		return result;
	}

	public String calculate(String input) {
		//分割
		ArrayList<String> infixExpression=this.inputCut(input);
		System.out.println("infixExpression"+infixExpression);
		//排查
		if(!ExpressionCheck(infixExpression)){
			return "表达式错误";
		}
		try {
		System.out.println("after infixExpression"+infixExpression);
		//转后缀
		ArrayList<String> PostfixExpression=this.infixExpression2PostfixExpression(infixExpression);
		System.out.println("PostfixExpression"+PostfixExpression);
		//计算

			double result=this.calculateExpression(PostfixExpression);
			System.out.println("result"+result);
			//整数检测
			double eps = 1e-10;
			if (result - floor(result) < eps){
				return Integer.toString(((int)result));
			}else {
				return Double.toString(result);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			return "表达式错误";
		}
	}

//	public static void main(String[] args) {
//		String teString="1+2-6*9+";
//		Calculator calculator=new Calculator();
//		System.out.println(calculator.calculate(teString));
//	}

}
