package testClient;

public class MainClass {
	
	
	public static void main(String[] args) 
	{
		
		TestClient[] dummy = new TestClient[60];
		
		for(int i = 0; i < dummy.length; i++)
		{
			dummy[i] = new TestClient(4444);
			System.out.println("X" + i);
			dummy[i].start();
			System.out.println("Dummy" + (i+1) + " started.");
		}
		
		
	}

}
