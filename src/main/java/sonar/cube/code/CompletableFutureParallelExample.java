package sonar.cube.code;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Go implements  Runnable {
	
	private static final  Logger logger = LoggerFactory.getLogger(Go.class);
	
	private String URL;
	private Integer counter;
	private Set<Integer> listOfPrice;
	
	public Go(String uRL, Integer counter, Set<Integer> listOfPrice) {
		super();
		URL = uRL;
		this.setCounter(counter);
		this.listOfPrice = listOfPrice;
	}

	@Override
	public void run() {
		if(URL.equals("git")) {
			logger.info("git service called");
			listOfPrice.add(99999);
		}
		else if(URL.equals("spring")) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.debug(e.getMessage());
			}
			logger.info("spring service called");
			listOfPrice.add(22222);
		}
		else if (URL.equals("amazon"))
			listOfPrice.add(33333);
		logger.info("count down");
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}
	
	
}


class Runner implements Runnable {

	private static final  Logger logger = LoggerFactory.getLogger(Runner.class);
	
	private String URL;
	private Integer counter;
	private Set<Integer> listOfPrice;
		
	
	public Runner(String uRL, Integer counter, Set<Integer> listOfPrice) {
		URL = uRL;
		this.counter = counter;
		this.listOfPrice = listOfPrice;
	}


	@Override
	public void run() {
		if(URL.equals("google")) {
			logger.info("google service called");
			listOfPrice.add(11111);
		}
		else if(URL.equals("facebook")) {
			logger.info("facebook service called");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); 
				logger.debug(e.getMessage());
			}
			listOfPrice.add(22222);
		}
		else if (URL.equals("amazon"))
			logger.info("amazon service called");
			listOfPrice.add(44444);
			logger.info("count down+");
	}
		
	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public Integer getCounter() {
		return counter;
	}


	public void setCounter(Integer counter) {
		this.counter = counter;
	}


		
}


public class CompletableFutureParallelExample {
	
	private static final  Logger logger = LoggerFactory.getLogger(CompletableFutureParallelExample.class);
	
	public static void main(String[] args) throws InterruptedException {
		
		Set<Integer> priceList=Collections.synchronizedSet(new HashSet<Integer>());
		CompletableFuture<Void> go1=CompletableFuture.runAsync(new Runner("google",1,priceList));
		CompletableFuture<Void> go2=CompletableFuture.runAsync(new Runner("facebook",2,priceList));
		CompletableFuture<Void> go3=CompletableFuture.runAsync(new Runner("amazon",3,priceList));
		CompletableFuture<Void> go4=CompletableFuture.runAsync(new Go("git",5,priceList));
		CompletableFuture<Void> go5=CompletableFuture.runAsync(new Go("spring",3,priceList));
		try {
			CompletableFuture<Void> allTask=CompletableFuture.allOf(go1,go2,go3,go4,go5);
		 allTask.get(4,TimeUnit.SECONDS);
		} catch (ExecutionException | TimeoutException e) {
			
			logger.debug(e.getMessage());
		}
		System.out.println(priceList);
		
		
		

	}

}
