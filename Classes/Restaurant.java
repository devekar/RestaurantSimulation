package Classes;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;





public class Restaurant {
	int n, t, c, a[][];
	Clock clock;
	Diner[] dn;
	Cook[] ck;
	Table table;
	Burger burger;
	Fries fries;
	Coke coke;
	NoWork nw;

	public Restaurant(int n, int t, int c, int[][] a) {

		this.n=n;
		this.c=c;
		this.t=t;
		this.a=a;

		table=new Table(t);
		burger=Burger.getInstance();
		fries=Fries.getInstance();
		coke=Coke.getInstance();
		nw=NoWork.getInstance(); 

		Clock.init(n,c);
		Diner.count=n;
		
		ck=new Cook[c];
		dn=new Diner[n];
		
		for(int i=0;i<c;i++)
			ck[i]=new Cook(i,table);

		for(int i=0;i<n;i++)
			dn[i]=new Diner(i,a[i],table);

	}

	public void simulate() throws InterruptedException
	{
		int time=0; int i=0;

		while(Diner.remaining()>0)
		{

			if(i<n && a[i][0]==time) {
				dn[i].t.start(); Clock.addD(); i++;  //Start diner thread if arriving now
			}

			while(Clock.allLogged()==false)  //Wait till all running diners and cooks acknowledge 
			{									
				Thread.sleep(10);            //not needed, just to prevent busy waiting
				wakeAll();					//notify all waiting threads  
			}

			time=Clock.tick();	//Increment time
		}


	}

	void wakeAll()
	{
		table.wake();
		burger.wake();
		fries.wake();
		coke.wake();
		nw.wake();
	}

	public void output(FileWriter fw) throws IOException {
		int max=-1;
		
		
		fw.write("Restaurant Simulation\n");
		fw.write("=====================\n\n");
		fw.write("Arrival time : TableNo CookId   SeatedTime   BurgerTime FriesTime CokeTime   ServedTime LeftTime\n\n");
		
		Formatter fmt=new Formatter(fw);
		for(int i=0;i<n;i++)
		{
			dn[i].Output(fmt);
			if(dn[i].di.leave>max) {max=dn[i].di.leave;}
		}
		fmt.flush();
		
		fw.write("\nLast Diner left at "+max);
		
	}
}
