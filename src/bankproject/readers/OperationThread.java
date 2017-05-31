package bankproject.readers;

public class OperationThread extends Thread {

	public OperationThread() {
		// TODO Auto-generated constructor stub
	}

	public OperationThread(Runnable target) {
		super(target);
		// TODO Auto-generated constructor stub
	}

	public OperationThread(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public OperationThread(ThreadGroup group, Runnable target) {
		super(group, target);
		// TODO Auto-generated constructor stub
	}

	public OperationThread(ThreadGroup group, String name) {
		super(group, name);
		// TODO Auto-generated constructor stub
	}

	public OperationThread(Runnable target, String name) {
		super(target, name);
		// TODO Auto-generated constructor stub
	}

	public OperationThread(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		// TODO Auto-generated constructor stub
	}

	public OperationThread(ThreadGroup group, Runnable target, String name, long stackSize) {
		super(group, target, name, stackSize);
		// TODO Auto-generated constructor stub
	}

}
