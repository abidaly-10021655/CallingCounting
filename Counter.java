import java.util.concurrent.*;
import java.util.ArrayList;
import java.time.Instant;


public class Counter
{
	public class MiniCounter implements Callable<Integer>
	{
		public Integer call()
		{
			// returns counting numbers

			int sum = 0;
			for(int i = 1; i <= 1_000_000; i++)
			{
				sum ++;
			}
			return sum;
		}
	}

	public Counter()
	{
		// Creates thread pool, ArrayList of results, and the final sum
		ExecutorService exeService = Executors.newFixedThreadPool(1);
		ArrayList<Future<Integer>> results = new ArrayList<>();
		long sum = 0;

		// Schedules threads and creates Future object
		for(int i = 0; i < 1000; i++)
		{
			Future future = exeService.submit(new MiniCounter());
			results.add(future);
		}

		// For each result, add the value to sum
		for(Future result : results)
		{
			try
			{
				sum += ((Integer) result.get()).intValue();
			}
			catch(Exception e)
			{
				System.out.println("OHNO");
			}
		}

		// Prints result
		System.out.println(sum);

		// Shuts down executor service
		exeService.shutdown();
	}
	public static void main(String[]args)
	{
		Counter counter = new Counter();
	}
}