package common;

import java.util.ArrayList;
import java.util.List;

import bean.UserInfoBean;
import bean.ValidationBean;

public class ValidationUtil {

	private ValidationUtil() {
		// インスタンス化できないように設定
	}


	/**
	 * userListの要素を判別し、バリデーションチェックに引き渡す
	 * バリデーションチェックの結果とエラーを返すメソッド
	 */
	public static List<ValidationBean> validInputAllStatus(List<UserInfoBean> userList) {

		//バリデーションチェックの戻り値を受け取る
		List<ValidationBean> beanList = new ArrayList<>();
		//バリデーションの戻り値を受け取り、valiListにsetする
		ValidationBean bean = new ValidationBean();


		//userListにidがあればバリデーションチェック
		if(userList.get(0).getId() != null) {
			String id = userList.get(0).getId();
			beanList = ValidationUtil.validIdStatus(id);

			bean.setErId(beanList.get(0).getErId());
			bean.setValiId(beanList.get(0).isValiId());
		}

		//userListにnameがあればバリデーションチェック
		if(userList.get(0).getName() != null) {
			String name = userList.get(0).getName();
			beanList = ValidationUtil.validNameStatus(name);

			bean.setErName(beanList.get(0).getErName());
			bean.setValiName(beanList.get(0).isValiName());
		}

		//userListにnameKanaがあればバリデーションチェック
		if(userList.get(0).getNameKana() != null) {
			String nameKana = userList.get(0).getNameKana();
			beanList = ValidationUtil.validNameKanaStatus(nameKana);

			bean.setErNameKana(beanList.get(0).getErNameKana());
			bean.setValiNameKana(beanList.get(0).isValiNameKana());
		}

		//userListにgenderがあればバリデーションチェック
		if(userList.get(0).getGender() != null) {
			String gender = userList.get(0).getGender();
			beanList = ValidationUtil.validGenderStatus(gender);

			bean.setErGender(beanList.get(0).getErGender());
			bean.setValiGender(beanList.get(0).isValiGender());
		}

		//userListにpasswordがあればバリデーションチェック
		if(userList.get(0).getPassword() != null) {
			String password = userList.get(0).getPassword();
			beanList = ValidationUtil.validPasswordStatus(password);

			bean.setErPassword(beanList.get(0).getErPassword());
			bean.setValiPassword(beanList.get(0).isValiPassword());
		}

		//userListにaddressがあればバリデーションチェック
		if(userList.get(0).getAddress() != null) {
			String address = userList.get(0).getAddress();
			beanList = ValidationUtil.validAddressrStatus(address);

			bean.setErAddress(beanList.get(0).getErAddress());
			bean.setValiAddress(beanList.get(0).isValiAddress());
		}

		//userListにtelがあればバリデーションチェック
		if(userList.get(0).getTel() != null) {
			String tel = userList.get(0).getTel();
			beanList = ValidationUtil.validTelStatus(tel);

			bean.setErTel(beanList.get(0).getErTel());
			bean.setValiTel(beanList.get(0).isValiTel());
		}

		//userListにemailがあればバリデーションチェック
		if(userList.get(0).getEmail() != null) {
			String email = userList.get(0).getEmail();
			beanList = ValidationUtil.validEmailStatus(email);

			bean.setErEmail(beanList.get(0).getErEmail());
			bean.setValiEmail(beanList.get(0).isValiEmail());
		}

		//userListにnoteがあればバリデーションチェック
		if(userList.get(0).getNote() != null) {
			String note = userList.get(0).getNote();
			beanList = ValidationUtil.validNoteStatus(note);

			bean.setErNote(beanList.get(0).getErNote());
			bean.setValiNote(beanList.get(0).isValiNote());
		}

		//userListにdelFlagがあればバリデーションチェック
		if(userList.get(0).getDelFlag() != null) {
			String delFlag = userList.get(0).getDelFlag();
			beanList = ValidationUtil.validDelFlagStatus(delFlag);

			bean.setErDelFlag(beanList.get(0).getErDelFlag());
			bean.setValiDelFlag(beanList.get(0).isValiDelFlag());
		}



		//valiListに値をセットし、引き渡す
		List<ValidationBean> valiList = new ArrayList<>();
		valiList.add(bean);

		return valiList;
	}





	/**
	 * idをバリデーションチェック
	 * valiListに結果とエラーテキストをセットし返す
	 */
	public static List<ValidationBean> validIdStatus(String id) {

		//valiListを戻り値として返す
		List<ValidationBean> valiList = new ArrayList<>();

		boolean vali = false;
		String er = "";

		//半角英数字
		String   pattern = "^[" + Const.valiAlpha + Const.valiNum + "]+$";

		try {
			//idが4文字であればバリデーションチェック
			if(id.length() == 4) {
				vali = id.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!vali) {
			er = Const.erId;
		}

		//バリデーションチェックの結果とエラーテキストをセット
		ValidationBean bean = new ValidationBean();
		bean.setValiId(vali);
		bean.setErId(er);
		valiList.add(bean);

		return valiList;
	}

	/**
	 * nameをバリデーションチェック
	 * valiListに結果とエラーテキストをセットし返す
	 */
	public static List<ValidationBean> validNameStatus(String name) {

		//valiListを戻り値として返す
		List<ValidationBean> valiList = new ArrayList<>();

		boolean vali = false;
		String er = "";

		//半角英字,全角かなカナ漢字,半角全角スペース,数字
		String pattern = "^[" + Const.valiAlpha + Const.valiGana + Const.valiKana + Const.valiKanji + Const.valiNum + Const.valiAllowKigou + "]+$";

		try {
			 //nameが20文字未満であればバリデーションチェック
			if(name.length() <= 20) {
				vali = name.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!vali) {
			er = Const.erName;
		}

		//バリデーションチェックの結果とエラーテキストをセット
		ValidationBean bean = new ValidationBean();
		bean.setValiName(vali);
		bean.setErName(er);
		valiList.add(bean);

		return valiList;
	}

	/**
	 * nameKanaをバリデーションチェック
	 * valiListに結果とエラーテキストをセットし返す
	 */
	public static List<ValidationBean> validNameKanaStatus(String nameKana) {

		//valiListを戻り値として返す
		List<ValidationBean> valiList = new ArrayList<>();

		boolean vali = false;
		String er = "";

		//全角カタカナ
		String pattern = "^[" + Const.valiKana + "]+$";

		try {

			if(nameKana.length() <= 40) {
				//nameKanaが40文字未満であればバリデーションチェック
				vali = nameKana.matches(pattern);
			}

			//nameKanaが""であればtrue(任意のため)
			if(nameKana.isEmpty()) {
				vali = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!vali) {
			er = Const.erNameKana;
		}

		//バリデーションチェックの結果とエラーテキストをセット
		ValidationBean bean = new ValidationBean();
		bean.setValiNameKana(vali);
		bean.setErNameKana(er);
		valiList.add(bean);

		return valiList;
	}

	/**
	 * genderをバリデーションチェック
	 * valiListに結果とエラーテキストをセットし返す
	 */
	public static List<ValidationBean> validGenderStatus(String gender) {

		//valiListを戻り値として返す
		List<ValidationBean> valiList = new ArrayList<>();

		boolean vali = false;
		String er = "";

		//1または2
		String pattern = Const.valiGender;

		try {
			//genderが1文字であればバリデーションチェック
			if(gender.length() == 1) {
				vali = gender.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!vali) {
			er = Const.erGender;
		}

		//バリデーションチェックの結果とエラーテキストをセット
		ValidationBean bean = new ValidationBean();
		bean.setValiGender(vali);
		bean.setErGender(er);
		valiList.add(bean);

		return valiList;
	}

	/**
	 * passwordをバリデーションチェック
	 * valiListに結果とエラーテキストをセットし返す
	 */
	public static List<ValidationBean> validPasswordStatus(String password) {

		//valiListを戻り値として返す
		List<ValidationBean> valiList = new ArrayList<>();

		boolean vali = false;
		String er = "";

		//半角英数字
		String pattern = "^[" + Const.valiNum + Const.valiAlpha + "]+$";

		try {
			//passwordが8文字未満であればバリデーションチェック
			if(password.length() <= 8) {
				vali = password.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!vali) {
			er = Const.erPassword;
		}

		//バリデーションチェックの結果とエラーテキストをセット
		ValidationBean bean = new ValidationBean();
		bean.setValiPassword(vali);
		bean.setErPassword(er);
		valiList.add(bean);

		return valiList;
	}

	/**
	 * addressをバリデーションチェック
	 * valiListに結果とエラーテキストをセットし返す
	 */
	public static List<ValidationBean> validAddressrStatus(String address) {

		//valiListを戻り値として返す
		List<ValidationBean> valiList = new ArrayList<>();

		boolean vali = false;
		String er = "";

		//半角英数字,全角かなカナ漢字,半角全角スペース,-
		String pattern = "^[" + Const.valiAlpha + Const.valiGana + Const.valiKana  + Const.valiAllowKigou + Const.valiNum + Const.valiKanji + Const.valiAllowKigouVer + "]+$";

		try {
			//addressが100文字未満であればバリデーションチェック
			if(address.length() <= 100) {
				vali = address.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!vali) {
			er = Const.erAddress;
		}

		//バリデーションチェックの結果とエラーテキストをセット
		ValidationBean bean = new ValidationBean();
		bean.setValiAddress(vali);
		bean.setErAddress(er);
		valiList.add(bean);

		return valiList;
	}

	/**
	 * telをバリデーションチェック
	 * valiListに結果とエラーテキストをセットし返す
	 */
	public static List<ValidationBean> validTelStatus(String tel) {

		//valiListを戻り値として返す
		List<ValidationBean> valiList = new ArrayList<>();

		boolean vali = false;
		String er = "";

		//半角数字,-
		String pattern = "^[" + Const.valiNum + Const.valiAlpha + "]+$";

		try {
			//telが15文字未満であればバリデーションチェック
			if(tel.length() <= 15) {
				vali = tel.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!vali) {
			er = Const.erTel;
		}

		//バリデーションチェックの結果とエラーテキストをセット
		ValidationBean bean = new ValidationBean();
		bean.setValiTel(vali);
		bean.setErTel(er);
		valiList.add(bean);

		return valiList;
	}

	/**
	 * emailをバリデーションチェック
	 * valiListに結果とエラーテキストをセットし返す
	 */
	public static List<ValidationBean> validEmailStatus(String email) {

		//valiListを戻り値として返す
		List<ValidationBean> valiList = new ArrayList<>();

		boolean vali = false;
		String er = "";

		//英数字記号＠英数字記号
		String pattern = Const.valiEmail;

		try {
			//emailが20文字未満であればバリデーションチェック
			if(email.length() <= 20) {
				vali = email.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!vali) {
			er = Const.erEmail;
		}

		//バリデーションチェックの結果とエラーテキストをセット
		ValidationBean bean = new ValidationBean();
		bean.setValiEmail(vali);
		bean.setErEmail(er);
		valiList.add(bean);

		return valiList;
	}

	/**
	 * noteをバリデーションチェック
	 * valiListに結果とエラーテキストをセットし返す
	 */
	public static List<ValidationBean> validNoteStatus(String note) {

		//valiListを戻り値として返す
		List<ValidationBean> valiList = new ArrayList<>();

		boolean vali = false;
		String er = "";

		//noteが400文字未満であればtrue
		if(note.length() <= 400) {
			vali = true;
		}

		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!vali) {
			er = Const.erNote;
		}

		//バリデーションチェックの結果とエラーテキストをセット
		ValidationBean bean = new ValidationBean();
		bean.setValiNote(vali);
		bean.setErNote(er);
		valiList.add(bean);

		return valiList;
	}

	/**
	 * delFlagをバリデーションチェック
	 * valiListに結果とエラーテキストをセットし返す
	 */
	public static List<ValidationBean> validDelFlagStatus(String delFlag) {

		//valiListを戻り値として返す
		List<ValidationBean> valiList = new ArrayList<>();

		boolean vali = false;
		String er = "";

		// 1
		String pattern = Const.valiDelFlag;

		try {
			 //delFlagが1文字であればバリデーションチェック
			if(delFlag.length() == 1) {
				vali = delFlag.matches(pattern);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		//delFlagは任意のため、""のときはtrue
		if(delFlag.isEmpty()) {
			vali = true;
		}

		//バリデーションチェックがアウトなら、エラ―テキストを代入
		if(!vali) {
			er = Const.erDelFlag;
		}

		//バリデーションチェックの結果とエラーテキストをセット
		ValidationBean bean = new ValidationBean();
		bean.setValiDelFlag(vali);
		bean.setErDelFlag(er);
		valiList.add(bean);

		return valiList;
	}
}
