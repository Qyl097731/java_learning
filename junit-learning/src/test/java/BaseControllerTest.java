//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nsec.constant.RSAConstant;
//import com.nsec.entity.JsonResult;
//import com.nsec.plugin.shiro.ShiroSessionRedisUtil;
//import com.nsec.util.RSAUtils;
//import com.nsec.util.SpringUtil;
//import org.junit.Ignore;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.Filter;
//import java.security.interfaces.RSAPublicKey;
//import java.util.Map;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * Controller测试基类
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring/ApplicationContext.xml","classpath:spring/ApplicationContext-mvc.xml"})
//@WebAppConfiguration
//@Ignore
//public class BaseControllerTest {
//
//    @Autowired
//    @Qualifier("webApplicationContext")
//    private WebApplicationContext wac;
//    /**
//     * spring依赖注入，初始化之后需要执行的方法
//     */
//    @PostConstruct
//    private void beforeInit() {
//        try {
//            init();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    /**
//     * MockMvc对象，不建议直接使用
//     */
//    protected MockMvc baseMockMvc;
//    //系统中需要传输的ajax头信息
//    private String headerSeId;
//
//    /**
//     * 初始化
//     * @throws Exception
//     */
//    public void init()  throws Exception{
//        baseMockMvc = MockMvcBuilders.webAppContextSetup(wac)
//                .addFilters((Filter) SpringUtil.getBean("shiroFilter")).build();  //初始化MockMvc对象
//        //调用rsa，再模拟登陆
//        ResultActions resultActions = baseMockMvc.perform(
//                get("/common/rsa")          //请求的url,请求的方法是get
//                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)//数据的格式
//        ).andExpect(status().isOk());    //返回的状态是200
//        MvcResult result = resultActions.andReturn();
//        MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
//        String responseString = result.getResponse().getContentAsString();   //将相应的数据转换为字符串
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonResult jsonResult = objectMapper.readValue(responseString, JsonResult.class);
//        String publicKeyExponent = (String) jsonResult.getParams().get(RSAConstant.PUBLIC_KEY);
//        String publicKeyModulus= (String) jsonResult.getParams().get(RSAConstant.PUBLIC_MODEL);
//        String redisRSA= (String) jsonResult.getParams().get("redisRSA");
//        RSAPublicKey rsaPublicKey = RSAUtils.getPublicKey(publicKeyModulus,publicKeyExponent);
//        //使用各人的账户，不能重复
//        String username = "yesi";
//        String passwd = "yhdr@2022";
//        String enPass = RSAUtils.encryptByPublicKey(passwd,rsaPublicKey);
//        //模拟post登陆
//        responseString = baseMockMvc.perform(
//                MockMvcRequestBuilders.post("/qhmuli/login")
//                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                        .param("redisRSA",redisRSA)
//                        .param("username", username)
//                        .param("password", enPass)//.session(session)
//        )
//                .andExpect(status().isOk()) //返回的状态是200
//                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
//        jsonResult = objectMapper.readValue(responseString, JsonResult.class);
//        headerSeId =(String) jsonResult.getParams().get(ShiroSessionRedisUtil.SSOTOKEN_COOKIE_KEY);
//    }
//
//    /**
//     * 测试get方法
//     * @param url
//     * @param params
//     * @return
//     */
//    public ResultActions doGetTest(String url,Map<String,String> params) throws Exception {
//        return doTest(MockMvcRequestBuilders.get(url),params);
//    }
//    /**
//     * 测试Post方法
//     * @param url
//     * @param params
//     * @return
//     */
//    public ResultActions doPostTest(String url,Map<String,String> params) throws Exception {
//        return doTest(MockMvcRequestBuilders.post(url),params);
//    }
//
//    /**
//     * 测试Post方法
//     * @param url
//     * @param params
//     * @return
//     */
//    public ResultActions doPostTest(String url,String params) throws Exception {
//        return doSingleParamTest(MockMvcRequestBuilders.post(url),params);
//    }
//
//    /**
//     * 测试Put方法
//     * @param url
//     * @param params
//     * @return
//     */
//    public ResultActions doPutTest(String url,Map<String,String> params) throws Exception {
//        return doTest(MockMvcRequestBuilders.put(url),params);
//    }
//
//    /**
//     * 测试Put方法
//     * @param url
//     * @param params
//     * @return
//     */
//    public ResultActions doPutTest(String url,String params) throws Exception {
//        return  doSingleParamTest(MockMvcRequestBuilders.put(url),params);
//    }
//
//    /**
//     * 测试Delete方法
//     * @param url
//     * @param params
//     * @return
//     */
//    public ResultActions doDeleteTest(String url,Map<String,String> params) throws Exception {
//        return doTest(MockMvcRequestBuilders.delete(url),params);
//    }
//
//    private ResultActions doSingleParamTest(MockHttpServletRequestBuilder builder,String params) throws Exception {
//        builder
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)//数据的格式
//                .content(params)
//                .header(ShiroSessionRedisUtil.SSOTOKEN_COOKIE_KEY,headerSeId);
//        return  baseMockMvc.perform(builder);
//    }
//
//    private ResultActions doTest(MockHttpServletRequestBuilder builder,Map<String,String> params) throws Exception {
//        builder.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)//数据的格式
//                .header(ShiroSessionRedisUtil.SSOTOKEN_COOKIE_KEY,headerSeId);
//        if(null!=params){
//            for(String key: params.keySet()){
//                builder.param(key,params.get(key));
//            }
//        }
//        return baseMockMvc.perform(builder);
//    }
//}
