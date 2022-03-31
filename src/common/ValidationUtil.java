package common;

import bean.UserBean;
import bean.ValidationBean;

public class ValidationUtil {

	private ValidationUtil() {
		// インスタンス化できないように設定
	}


	/**
	 * userListの要素を判別し、バリデーションチェックに引き渡す
	 * バリデーションチェックの結果とエラーを返すメソッド
	 */
	public static ValidationBean validInputAllStatus(UserBean userBean) {

		//バリデーションチェックの戻り値を受け取る
		ValidationBean bean = new ValidationBean();

		//バリデーションチェックの結果(bean)をsetし、返す
		ValidationBean valiBean = new ValidationBean();


		//userListにidがあればバリデーションチェック
		if(userBean.getId() != null) {

			String id = userBean.getId();
			bean = ValidationUtil.validIdStatus(id);

			valiBean.setErId(bean.getErId());
			valiBean.setValiId(bean.isValiId());
		}


		//userListにnameがあればバリデーションチェック
		if(userBean.getName() != null) {

			String name = userBean.getName();
			bean = ValidationUtil.validNameStatus(name);

			valiBean.setErName(bean.getErName());
			valiBean.setValiName(bean.isValiName());
		}


		//userListにnameKanaがあればバリデーションチェック
		if(userBean.getNameKana() != null) {

			String nameKana = userBean.getNameKana();
			bean = ValidationUtil.validNameKanaStatus(nameKana);

			valiBean.setErNameKana(bean.getErNameKana());
			valiBean.setValiNameKana(bean.isValiNameKana());
		}


		//userListにgenderがあればバリデーションチェック
		if(userBean.getGender() != null) {

			String gender = userBean.getGender();
			bean = ValidationUtil.validGenderStatus(gender);

			valiBean.setErGender(bean.getErGender());
			valiBean.setValiGender(bean.isValiGender());
		}


		//userListにpasswordがあればバリデーションチェック
		if(userBean.getPassword() != null) {

			String password = userBean.getPassword();
			bean = ValidationUtil.validPasswordStatus(password);

			valiBean.setErPassword(bean.getErPassword());
			valiBean.setValiPassword(bean.isValiPassword());
		}


		//userListにaddressがあればバリデーションチェック
		if(userBean.getAddress() != null) {

			String address = userBean.getAddress();
			bean = ValidationUtil.validAddressrStatus(address);

			valiBean.setErAddress(bean.getErAddress());
			valiBean.setValiAddress(bean.isValiAddress());
		}


		//userListにtelがあればバリデーションチェック
		if(userBean.getTel() != null) {

			String tel = userBean.getTel();
			bean = ValidationUtil.validTelStatus(tel);

			valiBean.setErTel(bean.getErTel());
			valiBean.setValiTel(bean.isValiTel());
		}


		//userListにemailがあればバリデーションチェック
		if(userBean.getEmail() != null) {

			String email = userBean.getEmail();
			bean = ValidationUtil.validEmailStatus(email);

			valiBean.setErEmail(bean.getErEmail());
			valiBean.setValiEmail(bean.isValiEmail());
		}


		//userListにnoteがあればバリデーションチェック
		if(userBean.getNote() != null) {

			String note = userBean.getNote();
			bean = ValidationUtil.validNoteStatus(note);

			valiBean.setErNote(bean.getErNote());
			valiBean.setValiNote(bean.isValiNote());
		}


		//userListにdelFlagがあればバリデーションチェック
		if(userBean.getDelFlag() != null) {

			String delFlag = userBean.getDelFlag();
			bean = ValidationUtil.validDelFlagStatus(delFlag);

			valiBean.setErDelFlag(bean.getErDelFlag());
			valiBean.setValiDelFlag(bean.isValiDelFlag());
		}

		return valiBean;
	}





	/**
	 * idをバリデーションチェック(必須入力)
	 * valiBeanに結果とエラーテキストをセットし返す
	 */
	public static ValidationBean validIdStatus(String id) {

		//valiBeanを戻り値として返す
		ValidationBean valiBean = new ValidationBean();

		boolean isVali = false;
		String er = "";

		//半角英数字
		String   pattern = "^[" + Const.PATTERN_ALPHA + Const.PATTERN_NUM + "]+$";

		try {
			//idが4文字であればバリデーションチェック
			if(id.length() == 4) {
				isVali = id.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!isVali) {
			er = "半角英数字(4文字)で入力してください";
		}

		//バリデーションチェックの結果とエラーテキストをセット
		valiBean.setValiId(isVali);
		valiBean.setErId(er);

		return valiBean;
	}

	/**
	 * nameをバリデーションチェック(必須入力)
	 * valiBeanに結果とエラーテキストをセットし返す
	 */
	public static ValidationBean validNameStatus(String name) {

		//valiBeanを戻り値として返す
		ValidationBean valiBean = new ValidationBean();

		boolean isInKigou = false;
		boolean isVali = false;
		String er = "全角または半角英数字(20文字以内)で入力してください";

		//半角英字,全角かなカナ漢字,半角全角スペース,数字
		String pattern = "^[" + Const.PATTERN_KIGOU_NOT＿VER + "]+$";

		try {
			 //nameが20文字未満であればバリデーションチェック
			if(name.length() <= 20) {
				isInKigou = name.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}


		//nameが空文字でないかつ記号が含まれていないとき、isValiをtrue
		if(!isInKigou && !name.isEmpty()) {
			isVali = true;
			er = "";
		}

		//バリデーションチェックの結果とエラーテキストをセット
		valiBean.setValiName(isVali);
		valiBean.setErName(er);

		return valiBean;
	}

	/**
	 * nameKanaをバリデーションチェック
	 * valiBeanに結果とエラーテキストをセットし返す
	 */
	public static ValidationBean validNameKanaStatus(String nameKana) {

		//valiBeanを戻り値として返す
		ValidationBean valiBean = new ValidationBean();

		boolean isVali = false;
		String er = "";

		//全角カタカナ
		String pattern = "^[" + Const.PATTERN_KANA + "]+$";

		try {
			//nameKanaが40文字未満であればバリデーションチェック
			if(nameKana.length() <= 40) {
				isVali = nameKana.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//nameKanaが""であればtrue(任意のため)
		if(nameKana.isEmpty()) {
			isVali = true;
		}


		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!isVali) {
			er = "全角カタカナ(40文字以内)で入力してください";
		}

		//バリデーションチェックの結果とエラーテキストをセット
		valiBean.setValiNameKana(isVali);
		valiBean.setErNameKana(er);

		return valiBean;
	}

	/**
	 * genderをバリデーションチェック
	 * valiBeanに結果とエラーテキストをセットし返す
	 */
	public static ValidationBean validGenderStatus(String gender) {

		//valiBeanを戻り値として返す
		ValidationBean valiBean = new ValidationBean();

		boolean isVali = false;
		String er = "";

		//1または2
		String pattern = Const.PATTERN_GENDER;

		try {
			//genderが1文字であればバリデーションチェック
			if(gender.length() == 1) {
				isVali = gender.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!isVali) {
			er = "不正な値を検知しました";
		}

		//バリデーションチェックの結果とエラーテキストをセット
		valiBean.setValiGender(isVali);
		valiBean.setErGender(er);

		return valiBean;
	}

	/**
	 * passwordをバリデーションチェック
	 * valiBeanに結果とエラーテキストをセットし返す
	 */
	public static ValidationBean validPasswordStatus(String password) {

		//valiBeanを戻り値として返す
		ValidationBean valiBean = new ValidationBean();

		boolean isVali = false;
		String er = "";

		//半角英数字
		String pattern = "^[" + Const.PATTERN_NUM + Const.PATTERN_ALPHA + "]+$";

		try {
			//passwordが8文字未満であればバリデーションチェック
			if(password.length() <= 8) {
				isVali = password.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//passwordは任意のため、""のときはtrue
		if(password.isEmpty()) {
			isVali = true;
		}


		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!isVali) {
			er = "半角英数字(8文字未満)で入力してください";
		}

		//バリデーションチェックの結果とエラーテキストをセット
		valiBean.setValiPassword(isVali);
		valiBean.setErPassword(er);

		return valiBean;
	}

	/**
	 * addressをバリデーションチェック
	 * valiBeanに結果とエラーテキストをセットし返す
	 */
	public static ValidationBean validAddressrStatus(String address) {

		//valiBeanを戻り値として返す
		ValidationBean valiBean = new ValidationBean();

		boolean isInKigou = false;
		boolean isVali = false;
		String er = "使用できない文字が含まれています";

		//半角英数字,全角かなカナ漢字,半角全角スペース,-
		String pattern = "^["  + Const.PATTERN_KIGOU_NOT＿VER + "]*$";

		try {
			//addressが100文字未満であればバリデーションチェック
			if(address.length() <= 100) {
				isInKigou = address.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//記号が含まれているとき、isValiをfalse,エラ―テキストを代入
		if(isInKigou) {
			isVali = false;
		}

		//addressが空文字または記号が含まれていないとき、isValiをtrue(emailは任意)
		if(address.isEmpty() ||  !isInKigou) {
			isVali = true;
			er = "";
		}


		//バリデーションチェックの結果とエラーテキストをセット
		valiBean.setValiAddress(isVali);
		valiBean.setErAddress(er);

		return valiBean;
	}

	/**
	 * telをバリデーションチェック
	 * valiBeanに結果とエラーテキストをセットし返す
	 */
	public static ValidationBean validTelStatus(String tel) {

		//valiBeanを戻り値として返す
		ValidationBean valiBean = new ValidationBean();

		boolean isVali = false;
		String er = "";

		//半角数字,-
		String pattern = "^[" + Const.PATTERN_NUM + Const.PATTERN_ALPHA + "]+$";

		try {
			//telが15文字未満であればバリデーションチェック
			if(tel.length() <= 15) {
				isVali = tel.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//telは任意のため、""のときはtrue
		if(tel.isEmpty()) {
			isVali = true;
		}


		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!isVali) {
			er = "半角数字(15文字以内)で入力してください";
		}

		//バリデーションチェックの結果とエラーテキストをセット
		valiBean.setValiTel(isVali);
		valiBean.setErTel(er);

		return valiBean;
	}

	/**
	 * emailをバリデーションチェック
	 * valiBeanに結果とエラーテキストをセットし返す
	 */
	public static ValidationBean validEmailStatus(String email) {

		//valiBeanを戻り値として返す
		ValidationBean valiBean = new ValidationBean();

		boolean isVali = false;
		String er = "";

		//英数字記号＠英数字記号
		String pattern = Const.PATTERN_EMAIL;

		try {
			//emailが20文字未満であればバリデーションチェック
			if(email.length() <= 20) {
				isVali = email.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//emailは任意のため、""のときはtrue
		if(email.isEmpty()) {
			isVali = true;
		}


		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!isVali) {
			er = "使用できない文字が含まれています";
		}

		//バリデーションチェックの結果とエラーテキストをセット
		valiBean.setValiEmail(isVali);
		valiBean.setErEmail(er);

		return valiBean;
	}

	/**
	 * noteをバリデーションチェック
	 * valiBeanに結果とエラーテキストをセットし返す
	 */
	public static ValidationBean validNoteStatus(String note) {

		//valiBeanを戻り値として返す
		ValidationBean valiBean = new ValidationBean();

		boolean isVali = false;
		String er = "";

		//noteが400文字未満であればtrue
		if(note.length() <= 400) {
			isVali = true;
		}


		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!isVali) {
			er = "400文字以内で入力してください";
		}

		//バリデーションチェックの結果とエラーテキストをセット
		valiBean.setValiNote(isVali);
		valiBean.setErNote(er);

		return valiBean;
	}

	/**
	 * delFlagをバリデーションチェック
	 * valiBeanに結果とエラーテキストをセットし返す
	 */
	public static ValidationBean validDelFlagStatus(String delFlag) {

		//valiBeanを戻り値として返す
		ValidationBean valiBean = new ValidationBean();

		boolean isVali = false;
		String er = "";

		// 1
		String pattern = Const.PATTERN_DELFLAG;

		try {
			 //delFlagが1文字であればバリデーションチェック
			if(delFlag.length() == 1) {
				isVali = delFlag.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//delFlagは任意のため、""のときはtrue
		if(delFlag.isEmpty()) {
			isVali = true;
		}

		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!isVali) {
			er = "不正な値を検知しました";
		}


		//バリデーションチェックの結果とエラーテキストをセット
		valiBean.setValiDelFlag(isVali);
		valiBean.setErDelFlag(er);

		return valiBean;
	}
}
