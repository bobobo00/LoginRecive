package TCP;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ģ�ⵥ���¼
 * @author dell
 *
 */

public class LoginServer {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.out.println("--------Server-----------");
		
		ServerSocket server=new ServerSocket(8888);
		boolean flag=true;
		int num=0;
		while(flag) {
			Socket client=server.accept();
			num++;
			System.out.println(num+"���û��ɹ�����");
			new Thread(new Channel(client)).start();
		}
		
	}
	static class Channel implements Runnable{
		private Socket client;
		private ObjectInputStream ois;
		private BufferedWriter bw;
		private User person=new User("danni","0813123");
		public Channel(Socket client) {
			this.client = client;
			try {
				ois=new ObjectInputStream(client.getInputStream());
				bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.release();
			}
			
		}
		private User recive() {
			User user=new User();
			try {
				user = (User)ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return user;
		}
		private void send(User user) {
			if(user.equals(person)) {
				try {
					bw.write("��½�ɹ���");
					bw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}else {
				try {
					bw.write("��½ʧ�ܣ������û����������Ƿ���ȷ��");
					bw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}
		private void release() {
			try {
				if(null!=bw) {
					bw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(null!=ois) {
					ois.close();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(null!=client) {
					client.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void run(){
			User p=this.recive();
			System.out.println("�û�����"+p.getName());
			System.out.println("��½���룺"+p.getPassword());
			this.send(p);
			this.release();
			
		}
	}

}
