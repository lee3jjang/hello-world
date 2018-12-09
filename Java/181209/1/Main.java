package com.tutorial;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		
		// Executor : 비동기 task가 수행될 Thread 생성
		// .runAsync : runnable 넣고 로직 구현
		// .thenRun : runnable 넣고 로직 구현
		
		/*
		ExecutorService executor = Executors.newSingleThreadExecutor();
		CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(1000);
			} catch (Exception exc) {};
			System.out.println("Hello!");
		}, executor)
		.thenRun(() -> System.out.println("World!"));
		
		System.out.println("async request is ready.");
		
		executor = Executors.newSingleThreadExecutor();
		CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(10);
			} catch (Exception exec) {};
			return "result is... " + Thread.currentThread().getId();
		}, executor)
		.thenApply(str -> str + " IS GOOD")
		.thenAccept(s -> System.out.println(s));
		
		System.out.println("gogogo " + Thread.currentThread().getId());
			
		 
		
		ExecutorService exec = Executors.newCachedThreadPool();
		long startTime = System.currentTimeMillis();
		//System.out.println(startTime);
		CompletableFuture cf1 = CompletableFuture.supplyAsync(() -> {
			try { Thread.sleep(150); } catch(Exception exc) {};
			System.out.println("cf1 supplyAsync on thread " + Thread.currentThread().getId() + " now=" + (System.currentTimeMillis() - startTime));
			return 100;
		});
		
		CompletableFuture cf2 = CompletableFuture.supplyAsync(() -> {
			try { Thread.sleep(100); } catch(Exception exc) {};
			System.out.println("cf2 supplyAsync on thread " + Thread.currentThread().getId() + " now=" + (System.currentTimeMillis() - startTime));
			return 200;
		});
		
		CompletableFuture cf3 = CompletableFuture.supplyAsync(() -> {
			try { Thread.sleep(50); } catch(Exception exc) {};
			System.out.println("cf3 supplyAsync on thread " + Thread.currentThread().getId() + " now=" + (System.currentTimeMillis() - startTime));
			return 300;
		});
		
		System.out.println("thread " + Thread.currentThread().getId());
		cf3.thenComposeAsync((data1) -> cf2).thenComposeAsync((data2) -> cf1).join();
		
		
		try {
			System.out.print(cf1.get());
			System.out.print(" " + cf2.get());
			System.out.println(" " + cf3.get());
		} catch (Exception exc) {};
		
		
		
		ExecutorService exec = Executors.newCachedThreadPool();
		CompletableFuture cf1 = CompletableFuture.supplyAsync(() -> {
			try { Thread.sleep(50); } catch(Exception exc) {};
			System.out.println("cf1 supplyAsync on thread " + Thread.currentThread().getId());
			return 100;
		}, exec);
		
		CompletableFuture cf2 = CompletableFuture.supplyAsync(() -> {
			try { Thread.sleep(50); } catch(Exception exc) {};
			System.out.println("cf2 supplyAsync on thread " + Thread.currentThread().getId());
			return 200;
		}, exec)
		.thenCombineAsync(cf1, (Integer x, Integer y) -> {
			System.out.println("Input 1 : " + x + "\nInput 2 : " + y);
			return x+y;
		}, exec);
	
		System.out.println("thread "+ Thread.currentThread().getId() + " START!");
		System.out.println("thread "+ Thread.currentThread().getId() + " START2!");
		
		try {
			System.out.println("Output : " + cf2.get());
		} catch (Exception exc) {};
		System.out.println("thread "+ Thread.currentThread().getId() + " START3!");
		System.out.println("thread "+ Thread.currentThread().getId() + " START4!");
		
		*/
		
		ExecutorService exec = Executors.newFixedThreadPool(2);
		Stream.iterate(0, n->n+1)
			.limit(5)
			.forEach(n -> {
				exec.execute(() -> {
					System.out.println("ID : " + Thread.currentThread().getName() + ", Name : " + n + " -> START");
					try { Thread.sleep(1000); } catch(Exception exc) {};
					System.out.println("ID : " + Thread.currentThread().getName() + ", Name : " + n + " -> END");
				});
			});
		exec.shutdown();
		while (!exec.isTerminated()) {}
		System.out.println("--- Done ---");
		
	}

}


