package com.example.se2project.LoggingAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Aspect
@Component
public class Logging_Aspect {

    private final String LOG_FILE = "C:/logs/execution_log.txt";

    @Around("execution(* com.example.se2project.Event.Event_Controller.Add_Event(..)) || " +
            "execution(* com.example.se2project.Event.Event_Controller.getAllEvents(..)) || " +
            "execution(* com.example.se2project.Event.Event_Controller.Edit_Event(..)) || " +
            "execution(* com.example.se2project.Event.Event_Controller.Delete_Event(..)) || " +
            "execution(* com.example.se2project.Invitation.Invitation_Controller.Add_Invitation(..)) || " +
            "execution(* com.example.se2project.Invitation.Invitation_Controller.Get_All_InvitationsSended(..)) || " +
            "execution(* com.example.se2project.Invitation.Invitation_Controller.Get_All_InvitationsRecevied(..)) || " +
            "execution(* com.example.se2project.Notification.Notification_Controller.Get_All_Notification(..)) || " +
            "execution(* com.example.se2project.Participation.Participation_Controller.Accept_Invitation(..)) || " +
            "execution(* com.example.se2project.Reminder.Reminder_Controller.Add_Reminder(..)) || " +
            "execution(* com.example.se2project.User.Login_Service.login(..)) || " +
            "execution(* com.example.se2project.User.Logout_Controller.Logout_User(..)) || " +
            "execution(* com.example.se2project.User.Register_Service.Register(..))")

    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result;

        try {
            result = joinPoint.proceed();
        }
        catch (Exception e) {
            logToFile("Exception in " + joinPoint.getSignature().getName() + ": " + e.getMessage());
            throw e;
        }

        long timeTaken = System.currentTimeMillis() - start;
        logToFile("Method " + joinPoint.getSignature().getName() +
                "() executed successfully in " + timeTaken + " ms.");

        return result;
    }

    private void logToFile(String message) {
        try {
            java.io.File logFile = new java.io.File(LOG_FILE);
            if (!logFile.exists())
                logFile.createNewFile();

            try (FileWriter writer = new FileWriter(logFile, true)) {
                writer.write(LocalDateTime.now() + " - " + message + "\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

