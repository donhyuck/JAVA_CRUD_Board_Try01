package board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.util.Util;

public class BoardMain {
	// makeTestDate 메서드를 통해 테스트를 만들기 위함
	// main 메서드 외부로 보내서 해결

	private static List<Article> articles;

	static {
		articles = new ArrayList<>();
	}

	public static void main(String[] args) {

		System.out.println("== 프로그램 시작 ==");

		makeTestDate();

		Scanner sc = new Scanner(System.in);

		int lastId = 1;

		while (true) {
			System.out.print("명령어 : ");
			String command = sc.nextLine();

			command.trim();

			if (command.length() == 0) {
				continue;
			}

			if (command.equals("article list")) {
				System.out.println("== 게시물 목록 ==");
				System.out.println(" 번호 |     날짜    |  제목  |  조회수");

				for (int i = articles.size() - 1; i >= 0; i--) {
					Article currentArticle = articles.get(i);
					System.out.printf("%3d  | %4s | %4s  | %4d\n", currentArticle.id, currentArticle.regDate,
							currentArticle.title, currentArticle.hit);
				}
			}

			else if (command.equals("article write")) {

				int id = lastId++;

				System.out.print("제목 : ");
				String title = sc.nextLine();

				System.out.print("내용 : ");
				String body = sc.nextLine();

				String currentDate = Util.getCurrentDate();

				Article article = new Article(id, currentDate, title, body);

				articles.add(article);

				System.out.printf("%d번 게시물 등록이 완료되었습니다.\n", id);
			}

			else if (command.startsWith("article detail")) {

				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]);

				Article targetArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article currentArticle = articles.get(i);

					if (currentArticle.id == id) {
						targetArticle = currentArticle;
					}
				}

				if (targetArticle == null) {
					System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
					continue;
				}

				targetArticle.increaseHit();

				System.out.printf("번 호 : %d\n", targetArticle.id);
				System.out.printf("작성일 : %s\n", targetArticle.regDate);
				System.out.printf("제 목 : %s\n", targetArticle.title);
				System.out.printf("내 용 : %s\n", targetArticle.body);
				System.out.printf("조회수 : %d\n", targetArticle.hit);

			}

			else if (command.startsWith("article delete")) {

				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]);

				Article targetArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article currentArticle = articles.get(i);

					if (currentArticle.id == id) {
						targetArticle = currentArticle;
					}
				}

				if (targetArticle == null) {
					System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
					continue;
				}

				articles.remove(targetArticle);
				System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

			}

			else if (command.startsWith("article modify")) {

				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]);

				Article targetArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article currentArticle = articles.get(i);

					if (currentArticle.id == id) {
						targetArticle = currentArticle;
					}
				}

				if (targetArticle == null) {
					System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
					continue;
				}

				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();

				targetArticle.title = title;
				targetArticle.body = body;

				System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
			}

			else if (command.equals("system exit")) {
				break;
			}

			else {
				System.out.printf("%s는 존재하지 않는 명령어 입니다.\n", command);
			}
		}

		System.out.println("== 프로그램 종료 ==");

	}

	private static void makeTestDate() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");

		articles.add(new Article(1, Util.getCurrentDate(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getCurrentDate(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getCurrentDate(), "제목3", "내용3", 33));
	}
}
