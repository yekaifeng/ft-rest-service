package hello;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicInteger;

import hello.CommonResponse;

import io.micrometer.core.instrument.Gauge;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.http.MediaType;
import io.swagger.annotations.*;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/greeting")
public class GreetingController {

    private Integer randomStatus = 200;
    private Integer randomDelaySeconds = 1;
    Counter myCounter;
    AtomicInteger myGauge;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    public GreetingController(MeterRegistry registry){
        // register a counter
        this.myCounter = registry.counter("my_counter");
        // register a gauge
        this.myGauge = registry.gauge("my_gauge", new AtomicInteger(0));
    }

    @ApiOperation(value="向用户打招呼", notes="参数：name")
    @RequestMapping(value={""}, method=RequestMethod.GET)
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @ApiOperation(value="创建招呼", notes="参数：name")
    //@ApiImplicitParam(name = "Greeting", value = "Greeting详细实体", required = true, dataType = "Greeting")
    @RequestMapping(value={""}, method=RequestMethod.POST)
    public String postGreeting(@RequestBody Greeting greeting) {
        return "success";
    }

    @ApiOperation(value="删除招呼", notes="参数：name")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteGreeting(@PathVariable Long id) {
        return "success";
    }

    @ApiOperation(value="正常Hi", notes="参数：name")
    @RequestMapping("/hi")
    @Timed( value = "greeting_hi",
            histogram = false,
            extraTags = {"demo", "true"}
    )
    public String hi() {
        this.myCounter.increment();
        return "Hi!";
    }

    @ApiOperation(value="随机延时Hi", notes="参数：name")
    @GetMapping(value = "/randomDelayHi")
    public String randomDelayHi() {
        int delay = new Random().nextInt(randomDelaySeconds * 1000);
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Random Delay " + String.valueOf(delay) + "ms Hi!";
    }

    @ApiOperation(value="随机返回码Hi", notes="")
    @GetMapping(value = "/randomStatusHi")
    public ResponseEntity<String> randomStatusHi() {
        int code = new Random().nextInt(100);
        Integer result = randomStatus + code;
        // 增加返回配置的状态码的概率
        if (code % 10 == 0) {
            result = randomStatus;
        }
        this.myGauge.set(code);
        return ResponseEntity.status(result).body("Random Status " + String.valueOf(result) + " Hi!");
    }


//    @RequestMapping(value = "/massHi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public CommonResponse massHi(HttpServletRequest request, HttpServletResponse response) {
//        CommonResponse resp = new CommonResponse();
//        HashMap<Integer, Object> node = new HashMap<>(jsonElements);
//        DataGenerator.run(node, jsonLevel, jsonSize, massSize * jsonElements);
//        resp.setData(node);
//        return resp;
//    }
}
