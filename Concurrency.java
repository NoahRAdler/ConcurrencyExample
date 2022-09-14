//Noah Adler 10/19/2020
//handles the threads outside of main
class MessageLoop implements Runnable
{
	private int arraySize; // important for copying the string array and run's for loop
	private String[] message;
	
	//takes a string array and associated size for args
	MessageLoop(String[] mes, int sz)
	{
		arraySize = sz;
		message = new String[arraySize];
		System.arraycopy(mes, 0, message, 0, arraySize);
	}
	
	// handles each new thread that prints the message array
	public void run()
	{
		try
		{
			for(int i = 0; i < arraySize ; i++)
			{
				Thread.sleep(850);
				System.out.println(Thread.currentThread().getName()
					+ ": " + message[i]);
			}			
		}
		catch(InterruptedException e)
		{
			return; //intentionally blank, add to later if requirements change
		}
	}
}

class Concurrency 
{	
	public static void main(String[] args) throws InterruptedException  
	{
		int trueMaxWait = 4; //upper bound of time waited
		
		//messages to print
		String[] messages = {"1. Ring once ",
				"2. Ring twice ",
				"3. Ring three times ",
				"4. Ring four "};
		MessageLoop threadOb1 = new MessageLoop(messages, 4);
		
		for(int maxWaitTime = 1; maxWaitTime <= trueMaxWait; maxWaitTime++)
		{
			
			//creating each new thread
			Thread msgLoopThread1 = new Thread(threadOb1);
			
			System.out.println("maxWaitTime: " 
					+ maxWaitTime + " second(s)");
			System.out.println("Main: Starting MessageLoop thread");
			
			msgLoopThread1.start();
			
			System.out.println("Main: Waiting for MessageLoop to finish");
			
			//  timer for each thread based off current wait time.
			//  purposely interrupt instances while increasing maxWaitTime with a new instance until the last
			for(int i = 0; i < maxWaitTime; i++)
			{
					Thread.sleep(500);
					if(msgLoopThread1.isAlive())
					{
						System.out.println("Main: Continuing to wait...");
						Thread.sleep(500);
						System.out.println("Main: Continuing to wait...");
					}
			}
			if(msgLoopThread1.isAlive())
			{
				System.out.println("Main: MessageLoop interrupted");
				msgLoopThread1.interrupt();
			}
		}
		System.out.println("main: Done!");
	}
}
