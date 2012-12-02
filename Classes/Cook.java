package Classes;



public class Cook implements Runnable {
	int cookid;
	
	Thread t;
	Table table;
	Burger burger;
	Fries fries;
	Coke coke;
	NoWork nw;
	DinerInfo dref;


	public Cook(int id,Table tbl) {
		this.table=tbl;
		this.burger=Burger.getInstance();
		this.fries=Fries.getInstance();
		this.coke=Coke.getInstance();
		this.nw=NoWork.getInstance();
		cookid=id;
		
		t=new Thread(this,"C"+cookid);
		t.start();
	}

	@Override
	public void run() {

		try {
			while(Diner.remaining()>0){

				dref=null;
				dref=table.getTableC(cookid);	//Get reference of DinerInfo of diner bein served
				if(dref==null) {Clock.cookLog(cookid);continue;}   


				boolean Bready=false, Fready=false, Cready=false;
				if(dref.friesno==0) Fready=true;
				if(dref.cokeno==0) Cready=true;
		
				
				while(!Bready || !Fready || !Cready){
				

					if(!Bready)
					{
						if(burger.getmc()){
							dref.burtime=Clock.getTime();
							while(Clock.getTime()<dref.burtime+ 5*dref.burno)
							{								
								Clock.cookLog(cookid);
								nw.suspend(); 								
							}
							burger.releasemc(nw);
							Bready=true;
						}
					}


					if(!Fready)
					{
						if(fries.getmc()){
							dref.friestime=Clock.getTime();
							while(Clock.getTime()<dref.friestime+ 3*dref.friesno)
							{							
								Clock.cookLog(cookid);
								nw.suspend(); 					
							}							
							fries.releasemc(nw);
							Fready=true;
						}
					}

					if(!Cready)
					{
						if(coke.getmc()){
							dref.coketime=Clock.getTime();
							while(Clock.getTime()<dref.coketime + dref.cokeno)
							{							
								Clock.cookLog(cookid);
								nw.suspend(); 								
							}
							coke.releasemc(nw);
							Cready=true;
						}
					}

					
					if(!Bready || !Fready || !Cready) {
						Clock.cookLog(cookid);
						nw.suspend();			
					}


				}

				table.serveFoodleave(cookid,dref.tableid);
			}	

		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
}
