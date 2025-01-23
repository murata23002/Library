# Library

このリポジトリは、書籍管理システム「Library」のソースコードを含んでいます。

## 機能概要

- **書籍管理**: 書籍の追加、編集、削除、検索機能を提供します。
- **ユーザー管理**: ユーザーの登録、認証、権限管理を行います。
- **貸出管理**: 書籍の貸出状況の管理、貸出履歴の追跡が可能です。

## 環境構築

1. **リポジトリのクローン**

   ```bash
   git clone https://github.com/murata23002/Library.git
   cd Library
   ```

2. **Dockerのセットアップ**

   DockerおよびDocker Composeを使用して環境を構築します。

   ```bash
   docker-compose up -d
   ```

3. **データベースのマイグレーション**

   初回起動時にデータベースのマイグレーションを実行してください。

   ```bash
   docker-compose exec app python manage.py migrate
   ```

## 注意事項

**重要**: 現在の設定では、データベースのパスワードなどの機密情報がハードコードされています。


