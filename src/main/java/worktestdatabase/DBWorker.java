package worktestdatabase;

import java.sql.*;


public class DBWorker {

    private static final String INSERT = "INSERT INTO tblTest VALUES (%s)";
    private static final String DELETE = "TRUNCATE TABLE testdatabase.tbltest";
    private static final String SELECT = "SELECT * FROM testdatabase.tbltest";


    private String host;
    private String username;
    private String password;
    private int count;

    private Connection connection;
    private Statement statement;

    public DBWorker(){

    }

    public DBWorker(String host, String username, String password, int count){
        this.host = host;
        this.username = username;
        this.password = password;
        this.count = count;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void connect(){
        try{
            connection = DriverManager.getConnection(host, username, password);
            statement = connection.createStatement();

            delete();
            insert();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insert() {
        try {
            for (int i = 1; i <= count; i++) {
                statement.execute(String.format(INSERT, i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void delete(){
        try {
            statement.execute(DELETE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet get(){
        try {
            return statement.executeQuery(SELECT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
