package day0923;

import classEx.DBConnector;
import classEx.MySqlConnector;
import classEx.OracleConnector;
import util.ScannerUtil;

public class Ex04Interface {
	static DBConnector conn;
	
	public static void main(String[] args) {
		conn = new OracleConnector();
		showMenu();
	}
	
	private static void showMenu() {
		String messge = "1. DB연결 2. 데이터 입력  3. 데이터 전체 출력  4. 데이터 개별 출력  5. 데이터 수정  6. 데이터 삭제  7. 프로그램 종료"; 
		while(true) {
			int userChoice = ScannerUtil.nextInt(messge,1,7);
			if(userChoice == 1) {
				conn.makeConnection();
			}else if(userChoice == 2) {
				conn.insert();
			}else if(userChoice == 3) {
				conn.selectAll();
			}else if(userChoice == 4) {
				conn.selectOne();
			}else if(userChoice == 5) {
				conn.update();
			}else if(userChoice == 6) {
				conn.delete();
			}else if(userChoice == 7) {
				System.out.println("사용해주셔서 감사합니다.");
				break;
			}
		}
	}
}
