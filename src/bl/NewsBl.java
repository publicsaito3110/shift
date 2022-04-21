package bl;

import java.util.ArrayList;
import java.util.List;

import bean.NewsBean;
import dao.NewsDao;

public class NewsBl {


	/**
	 *指定された日付の範囲内のお知らせを取得するメソッド
	 */
	public List<NewsBean> selectNews(String nowYmd) {

		//DAOの戻り値をnewsListで受け取る
		NewsDao dao = new NewsDao();
		List<NewsBean> newsList = new ArrayList<>();

		newsList = dao.selectNews(nowYmd);

		return newsList;
	}


	/**
	 *登録しているお知らせを表示するメソッド
	 */
	public List<NewsBean> selectAllNews(String nowYmd) {

		//DAOの戻り値をnewsListで受け取る
		NewsDao dao = new NewsDao();
		List<NewsBean> newsList = new ArrayList<>();

		newsList = dao.selectAllNews(nowYmd);

		return newsList;
	}


	/**
	 *指定された日付のお知らせを登録するメソッド
	 */
	public boolean insertNews(NewsBean newsBean) {

		//DAOの戻り値をbooleanで受け取る
		NewsDao dao = new NewsDao();

		boolean isResult = dao.insertNews(newsBean);

		return isResult;
	}
 }
