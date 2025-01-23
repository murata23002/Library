package com.libease.controller;

public class MessageManager {
    public final static String SUCCESS_SAMPLE = "サンプル成功";
    public final static String SUCCESS_BOOKING = "書籍の予約処理が完了しました。";
    public final static String SUCCESS_CANCEL = "書籍のキャンセル処理が完了しました。";
    public final static String SUCCESS_LOGIN = "ログインに成功しました。";
    public final static String SUCCESS_LOGOUT = "ログアウトに成功しました。";

    public final static String ERROR_SAMPLE = "サンプルエラー";
    public final static String ERROR_BOOKING = "書籍の予約に失敗しました。再度画面を読み込みし直して予約を実行してください。";
    public final static String ERROR_CANCEL = "書籍の予約キャンセルに失敗しました。再度画面を読み込みし直してキャンセルを実行してください。";
    public final static String ERROR_LOGIN = "ログインに失敗しました。ユーザーIDとユーザー名を再入力してログインを再実行してください。";

    private MessageManager () {
        
    }
}
