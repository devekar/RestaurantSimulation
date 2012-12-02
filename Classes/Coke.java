package Classes;


public class Coke {
	static boolean busy=false;
	private Coke() {}
	
    private static class SingletonHolder { 
        public static final Coke INSTANCE = new Coke();
    }
    
	public static Coke getInstance()
	{
		return SingletonHolder.INSTANCE;
	}

	synchronized public boolean getmc() throws InterruptedException {
		if(busy) return false;
		else {busy=true;return true;}
	}

	synchronized public void releasemc(NoWork nw) {
		busy=false;
		nw.wake();
		notifyAll();
	}
	
	synchronized public void wake()
	{
		notifyAll();
	}
}
