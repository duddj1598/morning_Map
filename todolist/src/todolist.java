import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class todolist {
    private String[] todolist;
    private JSONArray todolistarray;

    todolist(String id) throws IOException, ParseException {
        fetchTodolistFromAPI(id);
    }

    private void fetchTodolistFromAPI(String id) throws IOException, ParseException {
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
            throw new IOException("Failed to fetch data from API. HTTP response code: " + connection.getResponseCode());
        }
    }

    public String[] getTodolist(String id) throws IOException, ParseException {
        fetchTodolistFromAPI(id);
        return this.todolist;
    }

    public boolean[] getdone(String id) throws IOException, ParseException {
        fetchTodolistFromAPI(id);
        boolean[] done = new boolean[todolistarray.size()];
        for (int i = 0; i < todolistarray.size(); i++) {
            JSONObject todo = (JSONObject) todolistarray.get(i);
            done[i] = (boolean) todo.get("done");
        }
        return done;
    }

    public void addTodolist(String title) {
        System.out.println("Adding a new todo is not supported directly via this client.");
    }

    public void checkTodolist(int finalI) {
        System.out.println("Updating todo status is not supported directly via this client.");
    }

    public void uncheckTodolist(int finalI) {
        System.out.println("Updating todo status is not supported directly via this client.");
    }
}
