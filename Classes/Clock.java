package Classes;

public class Clock {
	static int time=0;
	static boolean []dinerlog;
	static boolean []cooklog;
	static int ctotal;
	static int dtotal;
	static int activediners;
	
	static void init(int d,int c)
	{
		ctotal=c; dtotal=d;
		activediners=0;
		
		dinerlog=new boolean[dtotal];
		for(int i=0;i<dtotal;i++) dinerlog[i]=false;
		
		cooklog=new boolean[ctotal];
		for(int i=0;i<ctotal;i++) cooklog[i]=false;
	}
	
	synchronized static void addD()
	{
		activediners++;
	}
	
	synchronized static void removeD()
	{
		activediners--;
	}
	
	synchronized static int tick()
	{
		for(int i=0;i<dtotal;i++) dinerlog[i]=false;
		
		for(int i=0;i<ctotal;i++) cooklog[i]=false;
		
		time++;
		return time;
	}
	
	synchronized static int getTime()
	{
		return time;
	}
	
	synchronized static void dinerLog(int d)
	{
		dinerlog[d]=true;
	}
	
	synchronized static void cookLog(int c)
	{
		cooklog[c]=true;
	}
	
	synchronized static boolean allLogged()
	{
		for(int i=0;i<ctotal;i++)
			if( !cooklog[i]) return false;
		
		int count=0;
		for(int j=0;j<dtotal;j++)
		{
			if(dinerlog[j]) count++; 
		}
		
		if(count>=activediners) return true;
		else return false;
	}
}
