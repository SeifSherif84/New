package com.example.se2project.ExceptionHandlingAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Aspect
@Component
public class Exception_Aspect {

    private final String LOG_FILE = "D:/wageh/Logs/logs.txt";

    @Around("execution(* com.example.se2project..*(..))")
    public Object handleExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        }
        catch (NumberFormatException e) {
            logExceptionToFile(e, joinPoint.getSignature().toShortString());
            return ResponseEntity.badRequest().body("Invalid number format. Please check your input.");
        }
        catch (NullPointerException e) {
            logExceptionToFile(e, joinPoint.getSignature().toShortString());
            return ResponseEntity.status(500).body("Missing required data.");
        }
        catch (NoSuchElementException e) {
            logExceptionToFile(e, joinPoint.getSignature().toShortString());
            return ResponseEntity.status(404).body("Resource not found.");
        }
        catch (IllegalArgumentException e) {
            logExceptionToFile(e, joinPoint.getSignature().toShortString());
            return ResponseEntity.badRequest().body("Invalid argument. Please check your input values.");
        }
        catch (Exception e) {
            logExceptionToFile(e, joinPoint.getSignature().toShortString());
            return ResponseEntity.status(500).body("Something went wrong. Please try again.");
        }
    }

    private void logExceptionToFile(Exception e, String methodName) {
        try {
            java.io.File logFile = new java.io.File(LOG_FILE);
            if (!logFile.exists())
                logFile.createNewFile();

            try (FileWriter writer = new FileWriter(logFile, true)) {
                writer.write(LocalDateTime.now() + " - Exception in " + methodName + ": " +
                        e.getClass().getSimpleName() + " - " + e.getMessage() + "\n");
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}

