package Classes;


public class NoWork {

	private NoWork() {}
	
    private static class SingletonHolder { 
        public static final NoWork INSTANCE = new NoWork();
    }
    
	public static NoWork getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	
	synchronized public void wake() {
		notifyAll();
	}
	
	synchronized public void suspend() throws InterruptedException {
		wait();
	}

}
