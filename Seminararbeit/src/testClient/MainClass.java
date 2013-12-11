package testClient;

public class MainClass {
	
	
	public static void main(String[] args) 
	{
		
		TestClient[] dummy = new TestClient[10];
		
		for(int i = 0; i < dummy.length; i++)
		{
			dummy[i] = new TestClient();
		}
		
		for(int i = 0; i < dummy.length; i++)
		{
			dummy[i].start();
			System.out.println("Dummy" + (i+1) + " gestartet.");
		}
		
		
	}

}
