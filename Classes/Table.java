package Classes;



public class Table {
	int free_tables; //Count of free tables
	int nonassigned; //Count of diners who have not been assigned a cook
	int tbinfo[][]; //diner,cook,food on table
	int total;		//Total no. of tables
	DinerInfo dref[]; //Store reference to DinerInfo of diner currently at table

	public Table (int t) {
		total=t;
		free_tables=total;
		nonassigned=0;

		tbinfo=new int[t][3]; //diner,cook,food
		dref=new DinerInfo[t];

		for(int i=0;i<total;i++)
			tbinfo[i][0]=tbinfo[i][1]=tbinfo[i][2]=0;
	}

	//Get table for diner
	synchronized public void getTableD(DinerInfo di) throws InterruptedException 
	{
		while(free_tables==0) {Clock.dinerLog(di.dinerid); wait();}    //Wait for a free table
		int i;

		for(i=0;i<total;i++)
		{
			if(tbinfo[i][0]==0) {
				free_tables--;
				nonassigned++;

				tbinfo[i][0]=1;   //diner seated at table i

				dref[i]=di;
				dref[i].tableid=i;
				dref[i].seated=Clock.getTime();

				notifyAll(); //for cooks waiting on getTableC()
				break;
			}
		}
	}

	//Assign cook to a table
	synchronized public DinerInfo getTableC(int cid) throws InterruptedException 
	{
		//wait for new diners
		while(nonassigned==0) {        
			if(Diner.remaining()==0) return null;
			Clock.cookLog(cid); wait(); 
		}

		int i;
		for(i=0;i<total;i++)
		{
			if(tbinfo[i][0]!=0 && tbinfo[i][1]==0 && tbinfo[i][2]==0) {
				nonassigned--;
				tbinfo[i][1]=1; //cook assigned
				dref[i].cookid=cid;
				break;
			}
		}

		if(i==total) return null;
		return dref[i];
	}



	synchronized public void serveFoodleave(int cid,int tbno)
	{
		tbinfo[tbno][2]=1; //food on table
		tbinfo[tbno][1]=0; //remove cook
		notifyAll();	   //For diner waiting on getFood()
	}

	synchronized public void getFood(int did,int tbno) throws InterruptedException
	{
		while (tbinfo[tbno][2]!=1)  //check if food present
		{
			Clock.dinerLog(did); 
			wait(); 
		}
		dref[tbno].served=Clock.getTime();
	}

	synchronized public void leaveTable(int did, int tno)
	{
		tbinfo[tno][0]=0;   //diner leaves
		tbinfo[tno][2]=0;   //food removed
		dref[tno]=null;     //clear reference to dinerinfo 
		free_tables++;

		Clock.dinerLog(did); 
		Clock.removeD(); 	//remove diner

		notifyAll();		//For diners waiting on getTableD
	}


	synchronized public void wake() {
		notifyAll();			//Wale all threads waiting on table object
	}
}
