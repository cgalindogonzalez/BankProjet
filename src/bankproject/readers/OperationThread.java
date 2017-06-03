package bankproject.readers;

public class OperationThread extends AbstractReaderThread {

	public void run () {
		
		readInputFile("operation");
		try {
			Thread.sleep(660000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		OperationThread ot = new OperationThread();
//		ot.start();
//		
//	}
}
