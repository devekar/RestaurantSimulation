import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Classes.Restaurant;


public class Main {


	public static void main(String[] args) throws IOException, InterruptedException {

		if(args.length!=1) {System.out.println("Error! Specify path for input file as command-line argument.");return;}

		args[0]="c:\\input2.txt";
		File input=new File(args[0]);
		if(!input.exists()) {
			System.out.println("Error! File not found");
			return;
		}

		Scanner s=new Scanner(input);

		int N,T,C;		 
		N=s.nextInt();		//no of diners
		T=s.nextInt();		//no. of tables
		C=s.nextInt();		//no. of cooks

		int A[][]=new int[N][4];
		int i=0;

		while(i<N){
			A[i][0]=s.nextInt(); //arrival time
			A[i][1]=s.nextInt(); //no. of burgers
			A[i][2]=s.nextInt(); //no. of fries
			A[i][3]=s.nextInt(); //no. of cokes
			i++;
		}
		s.close();

		Restaurant r=new Restaurant(N,T,C,A);
		r.simulate();
		
		FileWriter fw=new FileWriter(args[0]+".output");
		
		r.output(fw);
		
		fw.close();
	}

}
