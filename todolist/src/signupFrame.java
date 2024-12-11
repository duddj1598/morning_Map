import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.security.*;

public class signupFrame extends JFrame {

	public static String getSHA256Hash(String data) {
		try {
			// MessageDigest 인스턴스를 SHA-256 알고리즘으로 초기화
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			// 데이터의 바이트 배열을 얻음
			byte[] byteData = data.getBytes();

			// 해시 값을 계산
			byte[] hashBytes = digest.digest(byteData);

			// 바이트 배열을 16진수 문자열로 변환
			StringBuilder hexString = new StringBuilder();
			for (byte b : hashBytes) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString().toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	signupFrame(loginFrame lf) {
		super("회원가입");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel nPanel = new JPanel();
		nPanel.setLayout(null);
		setContentPane(nPanel);

		// 아이디
		JLabel lbl = new JLabel("아이디");
		lbl.setSize(100, 30);
		lbl.setLocation(50, 40);
		nPanel.add(lbl);

		// 아이디 텍스트 상자
		JTextField tf1 = new JTextField(10);
		tf1.setSize(200, 30);
		tf1.setLocation(110, 40);
		nPanel.add(tf1);

		// 비밀번호
		JLabel lbl2 = new JLabel("비밀번호");
		lbl2.setSize(100, 30);
		lbl2.setLocation(50, 90);
		nPanel.add(lbl2);

		// 비밀번호 텍스트 상자
		JTextField tf2 = new JTextField(10);
		tf2.setSize(200, 30);
		tf2.setLocation(110, 90);
		nPanel.add(tf2);

		// 가입하기 버튼
		JButton signUpButton = new JButton("가입하기");
		signUpButton.setSize(90, 30);
		signUpButton.setLocation(140, 160);
		nPanel.add(signUpButton);

		// 돌아가기 버튼
		JLabel backLabel = new JLabel("돌아가기");
		backLabel.setSize(100, 30);
		backLabel.setLocation(235, 165);
		nPanel.add(backLabel);

		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = tf1.getText();
				String password = getSHA256Hash(tf2.getText());
				String url = "http://127.0.0.1:8000/register/" + userId + "/" + password;

				try {
					// HTTP GET 요청 전송
					HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
					connection.setRequestMethod("GET");
					connection.setRequestProperty("Accept", "application/json");

					int responseCode = connection.getResponseCode();
					if (responseCode == 200) { // HTTP OK
						BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						StringBuilder response = new StringBuilder();
						String line;
						while ((line = in.readLine()) != null) {
							response.append(line);
						}
						in.close();

						// 응답 파싱
						JSONParser parser = new JSONParser();
						JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());
						boolean result = (boolean) jsonResponse.get("result");

						if (result) {
							JOptionPane.showMessageDialog(null, "회원가입 성공!");
							// 로그인 화면으로 복귀
							lf.setVisible(true);
							setVisible(false);
						} else {
							JOptionPane.showMessageDialog(null, "회원가입 실패. 이미 존재하는 아이디입니다.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "서버 오류가 발생했습니다.");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "회원가입 요청 중 오류가 발생했습니다.");
				}
			}
		});

		backLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 돌아가기 클릭 시 로그인 화면으로 전환
				setVisible(false);
				lf.setVisible(true);
			}
		});
	}
}
