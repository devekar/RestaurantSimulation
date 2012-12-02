package Classes;


import java.io.IOException;
import java.util.Formatter;




public class Diner implements Runnable{
	Thread t;
	Table table;
	DinerInfo di;
	NoWork nw;
	static int count; //count of diners who have not yet left the restaurant
	int dinerid;

	Diner(int id,int[] a, Table tbl) {

		this.dinerid=id;
		this.table=tbl;
		di=new DinerInfo(id,a[0], a[1], a[2], a[3]);
		nw=NoWork.getInstance();

		t=new Thread(this,"D-"+id);
	}


	synchronized public static int remaining()
	{
		return count;
	}

	synchronized static void leaveR()
	{
		count--;
	}

	@Override
	public void run() {

		try {
			table.getTableD(di); 

			table.getFood(dinerid,di.tableid);  //get cookid and mc times

			while(Clock.getTime()<di.served+30)
			{
				Clock.dinerLog(dinerid); 
				nw.suspend();     //not needed, to prevent busy waiting 
			}

			di.leave=Clock.getTime();
			table.leaveTable(dinerid,di.tableid);
			leaveR();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	void Output(Formatter fmt) throws IOException
	{
		fmt.format("%3d: T%-3d C%-3d   ",di.arrival,di.tableid,di.cookid);
		
		
		fmt.format("%3d   ", di.seated);
		//fw.write(di.seated+"   ");
		
		fmt.format("%3d ", di.burtime);
		//fw.write(di.burtime+" ");
		
		if(di.friestime==-1) fmt.format(" -  ");
		else fmt.format("%3d ",di.friestime);
		
		if(di.coketime==-1) fmt.format(" -    ");
		else fmt.format("%3d   ",di.coketime);
		
		fmt.format("%3d %3d\n",di.served,di.leave);
		
	}
}











