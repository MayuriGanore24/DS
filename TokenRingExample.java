import java.util.Scanner;

class TokenRing
{
	private int token;
	private int processes;
	private int rounds;
	private boolean[] criticalSectionFlag;
	private final Object lock=new Object();  //lock for token passing
	
	public TokenRing(int rounds,int processes)
	{
		this.token=0;
		this.processes=processes;
		this.rounds=rounds;
		this.criticalSectionFlag=new boolean[processes];
		
		for(int i=0;i<processes;i++)
		{
			criticalSectionFlag[i]=false;
		}
	}
	
	public void EnterCriticalSection(int processId,int currentRound)
	{
		synchronized(lock)
		{
			while(token!=processId)
			{
				try{
					lock.wait();
				}catch(InterruptedException e){
					Thread.currentThread().interrupt();
				}
			}
		System.out.println("Process "+processId+" entering in critical section! (Round "+currentRound+" )");
		criticalSectionFlag[processId]=true;
		
		try{
			Thread.sleep(2000);//simulate to do work 
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}
		
		System.out.println("Process "+processId+" leaving the critical section");
		criticalSectionFlag[processId]=false;
		
		//pass the token
		token=(token+1)%processes;
		lock.notifyAll();  //for waiting threads
		}
	}
	
	//Simulation of Processes
	public void runProcesses(int processId)
	{
		int currentRound=1;
		while(currentRound<=rounds)
		{
			EnterCriticalSection(processId,currentRound);
			currentRound++;
		}
	}
	
	//start simulation
	public void StartTokenRing()
	{
		for (int i=0;i<processes;i++)
		{
			final int processId=i;
			Thread thread=new Thread(()->runProcesses(processId));
			thread.start();
		}
	}
}

public class TokenRingExample
{
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter the NO. of Rounds:");
		int Rounds=sc.nextInt();
		
		System.out.println("Enter the No. of Processes:");
		int processes=sc.nextInt();
		
		if(Rounds<1)
		{
			System.out.println("There must be atlease 1 round");
		}
		
		if(processes<2)
		{
			System.out.println("There must be atleast 2 processes for mutual Exclusion!!");
		}
		TokenRing obj=new TokenRing(Rounds,processes);
		obj.StartTokenRing();
	}
}

		
