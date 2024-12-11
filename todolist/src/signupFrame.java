import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class signupFrame extends JFrame{
	signupFrame(loginFrame lf){
		super("회원가입");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel nPanel = new JPanel();
		nPanel.setLayout(null);
		setContentPane(nPanel);
		
		//아이디
		JLabel lbl = new JLabel("아이디");
		lbl.setSize(100, 30);
		lbl.setLocation(50, 40);
		nPanel.add(lbl);
		
		//아이디 텍스트 상자
		JTextField tf1 = new JTextField(10);
		tf1.setSize(200, 30);
		tf1.setLocation(110, 40);
		nPanel.add(tf1);
		
		//비밀번호 
		JLabel lbl2 = new JLabel("비밀번호");
		lbl2.setSize(100, 30);
		lbl2.setLocation(50, 90);		
		nPanel.add(lbl2);
		
		//비밀번호 텍스트 상자
		JTextField tf2 = new JTextField(10);
		tf2.setSize(200, 30);
		tf2.setLocation(110, 90);
		nPanel.add(tf2);
		
		//가입하기 버튼
		JButton signUpButton = new JButton("가입하기");
		signUpButton.setSize(90, 30);
		signUpButton.setLocation(140, 160);
		nPanel.add(signUpButton);
		
		//돌아가기 버튼
		JLabel backLabel = new JLabel("돌아가기");
		backLabel.setSize(100, 30);
		backLabel.setLocation(235, 165);
        nPanel.add(backLabel);

		signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				//가입하기 버튼 클릭시 ./user/user.json에 있는 회원정보 추가 저장
				JSONObject user = new JSONObject();
				user.put("id", tf1.getText());
				user.put("password", tf2.getText());
				JSONArray todolist = new JSONArray();
				user.put("todolist", todolist);
				JSONParser parser = new JSONParser();
				try {
					Reader reader = new FileReader("./user/user.json");
					JSONObject jsonObject = (JSONObject) parser.parse(reader);
					jsonObject.put(tf1.getText(), user);
					FileWriter file = new FileWriter("./user/user.json");
					file.write(jsonObject.toJSONString());
					file.flush();
					file.close();
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				}
				//로그인 화면으로 복귀
				lf.setVisible(true);
				setVisible(false);

			}
		});

        backLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	// 돌아가기 클릭 시 startFrame으로 전환
                setVisible(false);
                lf.setVisible(true);
            }
        });
        
	}

}
