package board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.dto.Article;
import board.util.Util;

public class ArticleController {

	private Scanner sc;
	private List<Article> articles;

	public ArticleController(Scanner sc, List<Article> articles) {
		this.sc = sc;
		this.articles = articles;
	}

	public void doWrite() {

		int id = articles.size() + 1;

		System.out.print("제목 : ");
		String title = sc.nextLine();

		System.out.print("내용 : ");
		String body = sc.nextLine();

		String currentDate = Util.getCurrentDate();

		Article article = new Article(id, currentDate, title, body);

		articles.add(article);

		System.out.printf("%d번 게시물 등록이 완료되었습니다.\n", id);

	}

	public void showList(String command) {

		if (articles.size() == 0) {
			System.out.println("게시물이 없습니다.");
			return;
		}

		String searchKeyword = command.substring("article list".length()).trim();
		List<Article> forListArticles = articles;

		if (searchKeyword.length() > 0) {
			forListArticles = new ArrayList<>();

			for (Article article : articles) {
				if (article.title.contains(searchKeyword)) {
					forListArticles.add(article);
				}
			}

			if (forListArticles.size() == 0) {
				System.out.println("검색결과가 존재하지 않습니다.");
				return;
			}
		}

		System.out.println("== 게시물 목록 ==");
		System.out.println(" 번호 |     날짜    |  제목  |  조회수");

		for (int i = forListArticles.size() - 1; i >= 0; i--) {
			Article currentArticle = forListArticles.get(i);
			System.out.printf("%3d  | %4s | %4s  | %4d\n", currentArticle.id, currentArticle.regDate,
					currentArticle.title, currentArticle.hit);
		}

	}

	public void showDetail(String command) {
		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		// id로 Article 가져온다.
		Article targetArticle = getArticleById(id);

		if (targetArticle == null) {
			System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
			return;
		}

		targetArticle.increaseHit();

		System.out.printf("번 호 : %d\n", targetArticle.id);
		System.out.printf("작성일 : %s\n", targetArticle.regDate);
		System.out.printf("제 목 : %s\n", targetArticle.title);
		System.out.printf("내 용 : %s\n", targetArticle.body);
		System.out.printf("조회수 : %d\n", targetArticle.hit);

	}

	public void doModify(String command) {

		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article targetArticle = getArticleById(id);

		if (targetArticle == null) {
			System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
			return;
		}

		System.out.print("제목 : ");
		String title = sc.nextLine();
		System.out.print("내용 : ");
		String body = sc.nextLine();

		targetArticle.title = title;
		targetArticle.body = body;

		System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

	}

	public void doDelete(String command) {
		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		int foundIndex = getArticleIndexById(id);

		if (foundIndex == -1) {
			System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
			return;
		}

		articles.remove(foundIndex);
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

	}

	private int getArticleIndexById(int id) {
		int i = 0;

		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}
			i++;
		}

		return -1;
	}

	private Article getArticleById(int id) {

		int index = getArticleIndexById(id);

		if (index != -1) {
			return articles.get(index);
		}

		return null;
	}

	public void makeTestDate() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");

		articles.add(new Article(1, Util.getCurrentDate(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getCurrentDate(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getCurrentDate(), "제목3", "내용3", 33));
	}

}
