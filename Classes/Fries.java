package Classes;


public class Fries {
	static boolean busy=false;
	
	private Fries() {}
	
    private static class SingletonHolder { 
        public static final Fries INSTANCE = new Fries();
    }
    
	public static Fries getInstance()
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
