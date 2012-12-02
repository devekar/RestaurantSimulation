package Classes;


public class DinerInfo {
	int dinerid,cookid,tableid;
	int burno,friesno,cokeno;
	int burtime,friestime,coketime;
	int arrival,seated,served,leave;
	
	
	public DinerInfo(int did,int arrivetime,int bur,int fries,int coke)
	{
		dinerid=did;
		cookid=tableid=-1;
		burno=bur; friesno=fries; cokeno=coke;
		arrival=arrivetime;
		burtime=friestime=coketime=-1;
		seated=served=leave=-1;
	}
}
