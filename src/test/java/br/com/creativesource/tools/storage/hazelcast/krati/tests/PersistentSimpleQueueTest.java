package br.com.creativesource.tools.storage.hazelcast.krati.tests;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class PersistentSimpleQueueTest {

	public static final int VALUE_SIZE = 1000;
	public static final int STATS_SECONDS = 10;
	private static HazelcastInstance hazel = null;

	public static void main(String[] args) {
		int threadCount = 20;
		final Stats stats = new Stats();

		Config cfg = new Config();
		cfg.setPort(5900);
		cfg.setPortAutoIncrement(false);

		MapConfig mapCfg = new MapConfig();
		mapCfg.setName("testKratiPersistence");
		mapCfg.setBackupCount(2);
		mapCfg.getMaxSizeConfig().setSize(10000);
		mapCfg.setTimeToLiveSeconds(300);

		MapStoreConfig mapStoreCfg = new MapStoreConfig();
		mapStoreCfg
				.setClassName(
						"br.com.creativesource.tools.storage.hazelcast.krati.tests.KratiHazelcastDS")
				.setEnabled(true);
		mapCfg.setMapStoreConfig(mapStoreCfg);

		cfg.addMapConfig(mapCfg);
		hazel = Hazelcast.init(cfg);

		ExecutorService es = Executors.newFixedThreadPool(threadCount);
		for (int i = 0; i < threadCount; i++) {
			es.submit(new Runnable() {
				public void run() {
					Queue<byte[]> queue = hazel.getQueue("default");
					while (true) {
						for (int j = 0; j < 1000; j++) {
							queue.offer(new byte[VALUE_SIZE]);
							stats.offers.incrementAndGet();
						}
						for (int j = 0; j < 1000; j++) {
							queue.poll();
							stats.polls.incrementAndGet();
						}
					}
				}
			});
		}
		Executors.newSingleThreadExecutor().submit(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(STATS_SECONDS * 1000);
						System.out.println("cluster size:"
								+ hazel.getCluster().getMembers().size());
						Stats currentStats = stats.getAndReset();
						System.out.println(currentStats);
						System.out.println("Operations per Second : "
								+ currentStats.total() / STATS_SECONDS);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public static class Stats {
		public AtomicLong offers = new AtomicLong();
		public AtomicLong polls = new AtomicLong();

		public Stats getAndReset() {
			long offersNow = offers.getAndSet(0);
			long pollsNow = polls.getAndSet(0);
			Stats newOne = new Stats();
			newOne.offers.set(offersNow);
			newOne.polls.set(pollsNow);
			return newOne;
		}

		public long total() {
			return offers.get() + polls.get();
		}

		public String toString() {
			return "total= " + total() + ", offers:" + offers.get()
					+ ", polls:" + polls.get();
		}
	}
}
