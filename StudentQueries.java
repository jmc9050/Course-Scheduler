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
public class StudentQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?, ?, ?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<String> firstName = new ArrayList<String>();
        ArrayList<String> lastName = new ArrayList<String>();
        ArrayList<String> id = new ArrayList<String>();
        ArrayList<StudentEntry> allStudents = new ArrayList<StudentEntry>();
        ArrayList<String> full = new ArrayList<String>();
        try
        {
            getAllStudents = connection.prepareStatement("select lastname from app.student order by lastname");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                lastName.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        try
        {
            getAllStudents = connection.prepareStatement("select firstname from app.student order by lastname");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                firstName.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        try
        {
            getAllStudents = connection.prepareStatement("select studentid from app.student order by lastname");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                id.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        for (int i = 0; i <lastName.size(); i++){
            allStudents.add(new StudentEntry(id.get(i),firstName.get(i),lastName.get(i)));
        }
        return allStudents;
        
    }
    
    private static StudentEntry getStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> student = new ArrayList<StudentEntry>();
        try
        {
            getStudent = connection.prepareStatement("select firstname, lastname from app.student where studentid = ?");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            student.add(new StudentEntry(studentID, resultSet.getString(1), resultSet.getString(2)));
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student.get(0);
    }
    
    public static void dropStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("delete from app.student where studentid = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
