package com.itheima.ssm.controller;

import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Componet 用来把LogAop交给spring管理
 */
@Component
@Aspect
public class LogAop {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private HttpServletRequest request;

    private Date visitTime; //开始时间
    private Class clazz; //访问的类
    private Method method; //访问的方法
    //主要是获取开始时间，执行的类是哪一个，执行的是哪一个方法

    /**
     * 前置通知
     * 主要是获取开始时间，执行的类是哪一个，执行的是哪一个方法
     *
     * @param jp
     */
    @Before("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp){
        visitTime = new Date (); //当前时间就是开始访问的时间

        //获取具体执行的方法的Method对象
        clazz = jp.getTarget ().getClass (); //具体要访问的类
        //被注释的内容也可得到method但是如果参数是接口类型会报错例如Model 所以我们会下面来获取
//        String methodName = jp.getSignature ().getName (); //获取访问的方法的名称
//        Object[] args = jp.getArgs ();//获取访问的方法的参数
//        if (args == null || args.length == 0) {//说明无参数
//            method = clazz.getMethod (methodName); //只能获取无参数的方法
//        } else {
//            //有参数,就将args中所有元素遍历,获取对应Class,装入到一个Class[]
//            Class[] classArgs = new Class[args.length];
//            for (int i = 0; i < classArgs.length; i++) {
//                classArgs[i] = args[i].getClass ();
//            }
//            method = clazz.getMethod (methodName, classArgs);
//        }
        if (clazz != LogAop.class) {
            //根据拦截点获取
            Signature signature = jp.getSignature();
            MethodSignature msig = null;
            if (!(signature instanceof MethodSignature)) {
                throw new IllegalArgumentException("该注解只能用于方法");
            }
            msig = (MethodSignature) signature;
            method = msig.getMethod();
        }
    }



    /**
     * 后置通知
     * @param jp
     */
    @After("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp) {
        //获取访问的时长
        long time = System.currentTimeMillis ()-visitTime.getTime ();
        //获取url
        String url="";
        if (clazz!=null&&method!=null&&clazz!=LogAop.class){
            //1.获取类上的@RequestMapping("/orders")
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation (RequestMapping.class);
            if (classAnnotation!=null){
                String[] classValue = classAnnotation.value ();
                //2.获取方法上的@RequestMapping(xxx)
                RequestMapping methidAnnotation = method.getAnnotation (RequestMapping.class);
                if (methidAnnotation!=null){
                    String[] methodValue = methidAnnotation.value ();
                    url=classValue[0]+methodValue[0];

                    //获取访问的ip 在web.xml配置request对象
                    String ip = request.getRemoteAddr ();
                    //获取当前操作的用户,这是Spring Security提供通过securityContext来获取的,也可以从request.getSession中获取
                    SecurityContext context = SecurityContextHolder.getContext ();//从上下文中获得当前登陆的用户
                    //SecurityContext context = (SecurityContext) request.getSession ().getAttribute ("SPRING_SECURITY_CONTEXT");
                    User user = (User) context.getAuthentication ().getPrincipal ();
                    String username = user.getUsername ();

                    //将日志信息封装到SysLog对象
                    SysLog sysLog = new SysLog ();
                    sysLog.setExecutionTime (time);
                    sysLog.setIp (ip);
                    sysLog.setMethod ("[类名] "+clazz.getName ()+"[方法名 ]"+method.getName ());
                    sysLog.setUrl (url);
                    sysLog.setUsername (username);
                    sysLog.setVisitTime (visitTime);

                    //调用Service完成操作
                    sysLogService.save (sysLog);

                }
            }
        }

    }
}
