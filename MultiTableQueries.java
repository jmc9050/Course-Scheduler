/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author jakec
 */
public class MultiTableQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement getAllClassDescriptions;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getScheduledByCourse;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static ResultSet resultSet;
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> descriptions = new ArrayList<ClassDescription>();
        try
        {
            getAllClassDescriptions = connection.prepareStatement("select app.class.courseCode, description, seats from app.class, app.course where semester = ? and app.class.courseCode = app.course.courseCode order by app.class.courseCode");
            getAllClassDescriptions.setString(1, semester);
            resultSet = getAllClassDescriptions.executeQuery();
            
            while(resultSet.next())
            {
                descriptions.add(new ClassDescription(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return descriptions;
        
    }
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            getScheduledStudentsByClass = connection.prepareStatement("select app.student.firstname, app.student.lastname, app.student.studentid, timestamp from app.student, app.schedule where semester = ? and courseCode = ? and app.student.studentid = app.schedule.studentid and status = ? order by timestamp");
            getScheduledStudentsByClass.setString(1, semester);
            getScheduledStudentsByClass.setString(2, courseCode);
            getScheduledStudentsByClass.setString(3, "S");
            resultSet = getScheduledStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {
                students.add(new StudentEntry(resultSet.getString(3), resultSet.getString(1), resultSet.getString(2)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
    }
    
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            getScheduledStudentsByClass = connection.prepareStatement("select app.student.firstname, app.student.lastname, app.student.studentid, timestamp from app.student, app.schedule where semester = ? and courseCode = ? and app.student.studentid = app.schedule.studentid and status = ? order by timestamp");
            getScheduledStudentsByClass.setString(1, semester);
            getScheduledStudentsByClass.setString(2, courseCode);
            getScheduledStudentsByClass.setString(3, "W");
            resultSet = getScheduledStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {
                students.add(new StudentEntry(resultSet.getString(3), resultSet.getString(1), resultSet.getString(2)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
    }
}
