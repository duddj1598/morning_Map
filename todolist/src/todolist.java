import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class todolist{
    private String [] todolist;
    private JSONArray todolistarray;
    private JSONObject jsonObject;
    todolist(String id) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("./src/user/user.json");
        jsonObject = (JSONObject) parser.parse(reader);
        JSONObject user = (JSONObject) jsonObject.get(id);
        todolistarray = (JSONArray) user.get("todolist");
        this.todolist = new String[todolistarray.size()];
        for (int i = 0; i < todolistarray.size(); i++) {
            JSONObject todo = (JSONObject) todolistarray.get(i);
            this.todolist[i] = (String) todo.get("title");
        }
    }
    public String[] getTodolist(String id) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("./src/user/user.json");
        jsonObject = (JSONObject) parser.parse(reader);
        JSONObject user = (JSONObject) jsonObject.get(id);
        todolistarray = (JSONArray) user.get("todolist");
        this.todolist = new String[todolistarray.size()];
        for (int i = 0; i < todolistarray.size(); i++) {
            JSONObject todo = (JSONObject) todolistarray.get(i);
            this.todolist[i] = (String) todo.get("title");
        }
        return this.todolist;
    }
    public boolean[] getdone(String id) throws IOException, ParseException{
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("./src/user/user.json");
        jsonObject = (JSONObject) parser.parse(reader);
        JSONObject user = (JSONObject) jsonObject.get(id);
        todolistarray = (JSONArray) user.get("todolist");
        boolean[] done = new boolean[todolistarray.size()];
        for (int i = 0; i < todolistarray.size(); i++) {
            JSONObject todo = (JSONObject) todolistarray.get(i);
            done[i] = (boolean) todo.get("done");
        }
        return done;
    }
    public void addTodolist(String title){
        JSONObject newtodo = new JSONObject();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dtf.format(Time.get_today());
        newtodo.put("title", title);
        newtodo.put("date", date);
        newtodo.put("done", false);
        todolistarray.add(newtodo);
        try {
            FileWriter file = new FileWriter("./src/user/user.json");
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkTodolist(int finalI) {
        JSONObject todo = (JSONObject) todolistarray.get(finalI);
        todo.put("done", true);
        try {
            FileWriter file = new FileWriter("./src/user/user.json");
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uncheckTodolist(int finalI) {
        JSONObject todo = (JSONObject) todolistarray.get(finalI);
        todo.put("done", false);
        try {
            FileWriter file = new FileWriter("./src/user/user.json");
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}