package bl;

import java.util.ArrayList;
import java.util.List;

import bean.NewsBean;
import dao.NewsDao;

/**
 * @author saito
 *
 */
public class NewsBl extends BaseBl {


	/**
	 * 現在日までお知らせ取得処理
	 *
	 * <p>現在までの日付のお知らせを最大5件まで現在日に近い順で取得する</p>
	 *
	 * @param nowYmd 現在の日付(YYYYMMDD)
	 * @return List<NewsBean> お知らせの日付(ymd), お知らせのタイトル(title), お知らせの詳細(content)
	 */
	public List<NewsBean> selectNewsBeforeNowByNowYmd(String nowYmd) {

		//DAOの戻り値をnewsListで受け取る
		NewsDao dao = new NewsDao();
		List<NewsBean> newsList = new ArrayList<>();

		newsList = dao.selectNewsBeforeNowByNowYmd(nowYmd);

		return newsList;
	}


	/**
	 * 登録済みお知らせ取得処理
	 *
	 * <p>現在日以降に登録されているお知らせを全て日付順で取得する</p>
	 *
	 * @param nowYmd 現在の日付(YYYYMMDD)
	 * @return List<NewsBean> お知らせの日付(ymd), お知らせのタイトル(title), お知らせの詳細(content)
	 */
	public List<NewsBean> selectAllNewsAfterNowByNowYmd(String nowYmd) {

		//DAOの戻り値をnewsListで受け取る
		NewsDao dao = new NewsDao();
		List<NewsBean> newsList = new ArrayList<>();

		newsList = dao.selectAllNewsAfterNowByNowYmd(nowYmd);

		return newsList;
	}


	/**
	 * お知らせ新規登録処理
	 *
	 * <p>指定された日付に表示するお知らせを新規登録する</p>
	 *
	 * @param newsBean お知らせの日付(ymd), お知らせのタイトル(title), お知らせの詳細(content)
	 * @return boolean true:お知らせの追加が成功したとき false:お知らせの追加が失敗したとき
	 */
	public boolean insertNewNews(NewsBean newsBean) {

		//DAOの戻り値をbooleanで受け取る
		NewsDao dao = new NewsDao();

		boolean isResult = dao.insertNewNews(newsBean);

		return isResult;
	}


	/**
	 * お知らせ修正処理
	 *
	 * <p>指定された日付のお知らせを修正する</p>
	 *
	 * @param newsBean お知らせの日付(ymd), お知らせのタイトル(title), お知らせの詳細(content)
	 * @return boolean true:お知らせの修正が成功したとき false:お知らせの修正が失敗したとき
	 */
	public boolean updateNews(NewsBean newsBean) {

		//DAOの戻り値をbooleanで受け取る
		NewsDao dao = new NewsDao();

		boolean isResult = dao.updateNews(newsBean);

		return isResult;
	}
}
