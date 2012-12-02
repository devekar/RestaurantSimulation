package Classes;


public class Burger {
	static boolean busy=false;

	private Burger() {}
	
    private static class SingletonHolder { 
        public static final Burger INSTANCE = new Burger();
    }
    
	public static Burger getInstance()
	{
		return SingletonHolder.INSTANCE;
	}

	
	synchronized public void wake() {
		notifyAll();
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
}
