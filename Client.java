import java.util.Scanner;
import java.rmi.Naming;

public class Client
{
	public static void main(String[] args)
	{
		try
		{
			Scanner sc=new Scanner(System.in);
			System.out.println("Enter the Server Address First:");
			String server=sc.nextLine();
			ServerInterface si=(ServerInterface) Naming.lookup("rmi://"+server+"/Server");
			System.out.println("Enter First String:");
			String first=sc.nextLine();
			System.out.println("Enter Second String:");
			String second=sc.nextLine();
			System.out.println("Concatenated String: "+si.concat(first,second));
			sc.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
	
