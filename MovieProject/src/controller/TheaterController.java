package controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import model.MovieDTO;
import model.TheaterDTO;
import util.ScannerUtil;

public class TheaterController {
    private static ArrayList<TheaterDTO> theaterList = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private MovieController movieController;
	private int nextId;
    
	public void theaterControllerConnection() {
		movieController = new MovieController();
		nextId = 1;
		
		for (int i = 1; i <= 7; i++) {
			TheaterDTO temp = new TheaterDTO();
			
			temp.setLocation("인천"+i);
			temp.setName("영화관이름"+i);
			temp.setPhoneNumber("032-3333-333"+i);
			temp.setTheaterId(nextId++);
			
			// random으로 영화를 뽑아서 저장.(영화관에서 상영될 영화들) 중복 o
			int [] randomId = new int[6];
			for(int j = 0 ; j < 6 ; j++ ) {
				Random random = new Random();
				randomId[j] = random.nextInt(5)+1;
			}
			temp.setTheaterMovies(randomId);
			
			theaterList.add(temp);
		}
	}
    
    public TheaterDTO selectOne(int theaterId) {
    	for(TheaterDTO temp : theaterList) {
    		if(temp.getTheaterId() == theaterId) {
    			return temp;
    		}
    	}
    	return null;
    }
    
    public void printOne(TheaterDTO temp) {
    	System.out.printf("[%s] %s에 위치한 %s \n",temp.getTheaterId(),temp.getLocation(),temp.getName());
    	System.out.println(" 	번호 : " + temp.getPhoneNumber());
    }
    	
	public void printAll() {
		System.out.println("============= 영화관 목록 =============");
		for(TheaterDTO temp : theaterList) {
			printOne(temp);
		}
		System.out.println("====================================");
	}

	public void printTheaterInfo(int theaterId) {
		TheaterDTO temp = selectOne(theaterId);
		if(temp.getTheaterMovies() == null) {
			System.out.println("해당 극장에는 예정된 상영영화가 존재하지 않습니다.");
		}else {
			ArrayList<MovieDTO> list = new ArrayList<>();
			int [] movies = temp.getTheaterMovies();
			for (int i = 0; i < movies.length; i++) {
				int movieId = movies[i];
				if(movieId == -1) {
					// 해당시간은 비어있음
					MovieDTO temp2 = new MovieDTO();
					list.add(temp2);
				}else {
					MovieDTO temp2 = movieController.selectOne(movieId);
					list.add(temp2);
				}
			}
			System.out.println("==========================================");
			printOne(temp);
			System.out.println("	09~11시 "+ list.get(0).getTitle());
			System.out.println("	11~13시 "+ list.get(1).getTitle());
			System.out.println("	13~15시 "+ list.get(2).getTitle());
			System.out.println("	15~17시 "+ list.get(3).getTitle());
			System.out.println("	17~19시 "+ list.get(4).getTitle());
			System.out.println("	19~21시 "+ list.get(5).getTitle());
			System.out.println("==========================================\n");
		}
			
	}
	public void editTheater(int theaterId) {
		TheaterDTO temp = selectOne(theaterId);
		int index = theaterList.indexOf(temp);
		
		String message = "수정할 로케이션의 정보를 입력해주세요";
		String location = ScannerUtil.nextLine(scanner, message);
		
		message = "수정할 영화관의 이름정보를 입력해주세요";
		String name = ScannerUtil.nextLine(scanner, message);

		message = "수정할 phone Number 정보를 입력해주세요";
		String phone = ScannerUtil.nextLine(scanner, message);
		
		temp.setLocation(location);
		temp.setName(name);
		temp.setPhoneNumber(phone);
		
		theaterList.set(index, temp);
		System.out.println("수정 완료되었습니다.");
	}

	public void removeTheater(int theaterId) {
		TheaterDTO temp = selectOne(theaterId);
		int index = theaterList.indexOf(temp);
		
		String message = "정말로 삭제하시겠습니까? y/n";
		String yesNo = ScannerUtil.nextLine(scanner, message);
		if(yesNo.equalsIgnoreCase("y")) {
			theaterList.remove(index);
			System.out.println("삭제되었습니다.");
		}else {
			System.out.println("삭제가 취소되었습니다.");
		}
	}
	
	// 기존 상영정보 전부삭제
	public void removeAllTheaterMovies(int theaterId) {
		TheaterDTO temp = selectOne(theaterId);
		int index = theaterList.indexOf(temp);
		temp.setTheaterMovies(null);
		theaterList.set(index, temp);
	}
	// 기존 상영정보 부분삭제
	public void removeTheaterMovie(int theaterId) {
		TheaterDTO temp = selectOne(theaterId);
		int max = temp.getTheaterMovies().length;
		int index = theaterList.indexOf(temp);
		ArrayList<Integer> deleteList = new ArrayList<>();
		
		while(true) {
			String message = "삭제하고자 하는 번호를 하나씩 입력해주세요. 없으시면 0을 입력해주세요.";
			int userChoice = ScannerUtil.nextInt(scanner, message,0,max);
			if(userChoice != 0) {
				deleteList.add(userChoice-1);
			}else if(userChoice == 0){
				break;
			}
		}
		
		int [] arr = temp.getTheaterMovies();
		
		for (int i = 0; i < deleteList.size(); i++) {
			int remove = deleteList.get(i);
			arr[remove] = -1;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
		temp.setTheaterMovies(arr);
		theaterList.set(index, temp);
	}

	public void addTheaterMovie(int theaterId, int movieId, int time) {
		TheaterDTO temp = selectOne(theaterId);
		int index = theaterList.indexOf(temp);
		temp.getTheaterMovies()[time] = movieId;
		theaterList.set(index, temp);
	}
}
