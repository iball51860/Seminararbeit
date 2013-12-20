package testClient;

public class MainClass {
	
	
	public static void main(String[] args) 
	{
		
		TestClient[] dummy = new TestClient[60];
		
		for(int i = 0; i < dummy.length; i++)
		{
			dummy[i] = new TestClient();
			System.out.println("X" + i);
		}
		
		for(int i = 0; i < dummy.length; i++)
		{
			dummy[i].start();
			System.out.println("Dummy" + (i+1) + " started.");
		}
		
		
	}

}
