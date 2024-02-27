package zti.projekt.aspect;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import zti.projekt.model.AnnouncementModel;
import zti.projekt.repository.AnnouncementRepository;
import zti.projekt.model.AnnouncementModel;

@Aspect
@Component
public class AspectLogger {

    @Pointcut("execution(* zti.projekt.controller.AnnouncementController.getAnnouncementById(Integer)) && args(id)")
    public void getAnnouncementByIdPointcut(Integer id) {
    }

    @Before("getAnnouncementByIdPointcut(id)")
    public void logBeforeGetAnnouncementById(Integer id) {
        System.out.println("Attempting to get announcement with id: " + id);
    }

    @AfterReturning(pointcut = "getAnnouncementByIdPointcut(id)", returning = "result")
    public void logAfterReturningGetAnnouncementById(Integer id, AnnouncementModel result) {
        if (result != null) {
            System.out.println("Successfully retrieved announcement with id: " + id);
        } else {
            System.out.println("Announcement not found with id: " + id);
        }
    }
    @Pointcut("execution(* zti.projekt.controller.AnnouncementController.createAnnouncement(..))")
    public void createAnnouncementPointcut() {
    }
    @Before("createAnnouncementPointcut()")
    public void beforeCreateAnnouncement(JoinPoint joinPoint) {
        System.out.println("Before creating announcement");
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            System.out.println("Argument: "+ arg);
        }
    }

    @AfterReturning(pointcut = "createAnnouncementPointcut()", returning = "announcement")
    public void afterCreateAnnouncement(JoinPoint joinPoint, AnnouncementModel announcement) {
        System.out.println("After creating announcement");
        System.out.println("Created announcement: "+ announcement);
    }


}