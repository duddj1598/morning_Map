import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class todolist {
    private String[] todolist;
    private JSONArray todolistarray;
    private String id;

    todolist(String id) throws IOException, ParseException {
        this.id = id;
        update_todolist();
    }

    private void update_todolist() throws IOException, ParseException {
        String apiUrl = "http://127.0.0.1:8000/todolist/" + id;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONParser parser = new JSONParser();
            todolistarray = (JSONArray) parser.parse(response.toString());
            this.todolist = new String[todolistarray.size()];
            for (int i = 0; i < todolistarray.size(); i++) {
                JSONObject todo = (JSONObject) todolistarray.get(i);
                this.todolist[i] = (String) todo.get("title");
            }
        } else {
            throw new IOException("Failed. HTTP response code: " + connection.getResponseCode());
        }
    }

    public String[] getTodolist() throws IOException, ParseException {
        update_todolist();
        return this.todolist;
    }

    public boolean[] getdone() throws IOException, ParseException {
        update_todolist();
        boolean[] done = new boolean[todolistarray.size()];
        for (int i = 0; i < todolistarray.size(); i++) {
            JSONObject todo = (JSONObject) todolistarray.get(i);
            done[i] = (boolean) todo.get("done");
        }
        return done;
    }

    public void addTodolist(String title) throws IOException {
        String apiUrl = "http://127.0.0.1:8000/todolist/" + id + "/" + title;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() != 200) {
            throw new IOException("Failed to add todo. HTTP response code: " + connection.getResponseCode());
        }
    }

    public void updateTodolistDone(int todoId, boolean done) throws IOException {
        String apiUrl = "http://127.0.0.1:8000/todolist/" + id + "/" + todoId + "/" + done;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() != 200) {
            throw new IOException("Failed to update todo status. HTTP response code: " + connection.getResponseCode());
        }
    }

    public void checkTodolist(int todoId) throws IOException {
        updateTodolistDone(todoId, true);
    }

    public void uncheckTodolist(int todoId) throws IOException {
        updateTodolistDone(todoId, false);
    }
}
