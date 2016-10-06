
public class bla {

	
	public static void main(String [] args) throws InterruptedException {
		
		Thread.sleep(6000);
		
		
		final int time = 5000;
		final int size = 100;
		
		GreatClass[] d;
		System.out.println("Step 0\treserved NO space");
		Thread.sleep(time);
		System.out.println("\t\tdone!");
		
		d = new GreatClass[size];
		System.out.println("Step 1\tReserved space");
		Thread.sleep(time);
		System.out.println("\t\tdone!");
		
		for (int i = 0; i < d.length; i++) {

			d[i] = new GreatClass();
			System.out.println("Step 2\tinit part k = " + i);
			
			Thread.sleep(time / 10);
		}
		
	}
}


class GreatClass {
	
	private byte[] by = new byte[200000];
	public GreatClass() {
	}
	
}