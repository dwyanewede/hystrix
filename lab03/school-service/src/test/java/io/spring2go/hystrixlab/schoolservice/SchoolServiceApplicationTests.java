package io.spring2go.hystrixlab.schoolservice;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import rx.Observable;
import rx.internal.util.InternalObservableUtils;
import java.util.concurrent.TimeUnit;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SchoolServiceApplicationTests {

	@Test
	public void contextLoads() {
	}

	// 滑动窗口协议在RxJava中的实现
	@Test
	public void timeWindowTest() throws Exception{
		Observable<Integer> source = Observable.interval(50, TimeUnit.MILLISECONDS).map(i -> RandomUtils.nextInt(2));
		source.window(1, TimeUnit.SECONDS).subscribe(window -> {
			int[] metrics = new int[2];
			window.subscribe(i -> metrics[i]++,
					InternalObservableUtils.ERROR_NOT_IMPLEMENTED,
					() -> System.out.println("窗口Metrics:" + JSON.toJSONString(metrics)));
		});
		TimeUnit.SECONDS.sleep(5);
	}

}
