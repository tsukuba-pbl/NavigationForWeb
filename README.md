[![Build Status](https://travis-ci.org/tsukuba-pbl/NavigationForWeb.svg?branch=master)](https://travis-ci.org/tsukuba-pbl/NavigationForWeb)

# NavigationForWeb

# 目次

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->


- [how to use](#how-to-use)
- [各種説明](#%E5%90%84%E7%A8%AE%E8%AA%AC%E6%98%8E)
  - [DBへの接続](#db%E3%81%B8%E3%81%AE%E6%8E%A5%E7%B6%9A)
  - [マイグレーション関係のコマンド](#%E3%83%9E%E3%82%A4%E3%82%B0%E3%83%AC%E3%83%BC%E3%82%B7%E3%83%A7%E3%83%B3%E9%96%A2%E4%BF%82%E3%81%AE%E3%82%B3%E3%83%9E%E3%83%B3%E3%83%89)
    - [マイグレーション情報の確認](#%E3%83%9E%E3%82%A4%E3%82%B0%E3%83%AC%E3%83%BC%E3%82%B7%E3%83%A7%E3%83%B3%E6%83%85%E5%A0%B1%E3%81%AE%E7%A2%BA%E8%AA%8D)
    - [マイグレーションの実行](#%E3%83%9E%E3%82%A4%E3%82%B0%E3%83%AC%E3%83%BC%E3%82%B7%E3%83%A7%E3%83%B3%E3%81%AE%E5%AE%9F%E8%A1%8C)
    - [テーブルの削除](#%E3%83%86%E3%83%BC%E3%83%96%E3%83%AB%E3%81%AE%E5%89%8A%E9%99%A4)
  - [環境別のフォルダ構成について](#%E7%92%B0%E5%A2%83%E5%88%A5%E3%81%AE%E3%83%95%E3%82%A9%E3%83%AB%E3%83%80%E6%A7%8B%E6%88%90%E3%81%AB%E3%81%A4%E3%81%84%E3%81%A6)
    - [migration関連](#migration%E9%96%A2%E9%80%A3)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# how to use
local環境で動かす場合

```
sh ./serve.sh -e local
```

prod環境で動かす場合

```
sh ./serve.sh -e prod
```

それぞれ中では`docker-compose up`をしている．コマンド実行時には自動的にマイグレーションが実行されている．

実行後， `http://127.0.0.1` or `http://localhost` にアクセスすることでブラウザで確認できる．

# 各種説明
## DBへの接続
mysqlには `mysql -h127.0.0.1 -u docker -p` でパスワードが `password`で接続できる．ユーザ名とパスワードとDB名は，`docker-compose.yml`を参考にしていただきたい．

## マイグレーション関係のコマンド
`docker exec`コマンドでコンテナに対してコマンドを実行する．
`docker exec`の次の`springdocker_app_1`は，`docker ps`で表示されるWebアプリケーションを動かしているコンテナのNAMESの部分である．
コマンドラインからflywayを実行するには，各環境のapplication.propetiesを読み込む必要があるため，`-Dflyway.configFile=`で指定する必要がある．

### マイグレーション情報の確認
`./mvnw flyway:info`でマイグレーション情報の確認をすることが出来る．

localのapplication.propertiesで実行したい場合

```
docker exec springdocker_app_1 ./mvnw flyway:info -Dflyway.configFile=./src/main/resources/local/application.properties
```

prodのapplication.propertiesで実行したい場合

```
docker exec springdocker_app_1 ./mvnw flyway:info -Dflyway.configFile=./src/main/resources/prod/application.properties
```

### マイグレーションの実行
マイグレーション自体は起動時に自動的に行ってくれるので，このコマンドを利用することは無い

`./mvnw flyway:migrate` でマイグレーションの実行をすることが出来る．

localのapplication.propertiesで実行したい場合

```
docker exec springdocker_app_1 ./mvnw flyway:migrate -Dflyway.configFile=./src/main/resources/local/application.properties -Dflyway.locations=filesystem:./src/main/resources/common/db/migration,filesystem:./src/main/resources/local/db/migration
```

prodのapplication.propertiesで実行したい場合

```
docker exec springdocker_app_1 ./mvnw flyway:migrate -Dflyway.configFile=./src/main/resources/prod/application.properties -Dflyway.locations=filesystem:./src/main/resources/common/db/migration,filesystem:./src/main/resources/prod/db/migration
```

### テーブルの削除
`./mvnw flyway:clean`でテーブルを削除出来る．本番環境では絶対してはいけない．

localのapplication.propertiesで実行したい場合

```
docker exec springdocker_app_1 ./mvnw flyway:clean -Dflyway.configFile=./src/main/resources/local/application.properties
```

## 環境別のフォルダ構成について
以下のmigrationやapplication.propertiesの各環境ごとに分けているのは，`pom.xml` の `<profiles></profiles>` に記述されている．
### migration関連
各環境ごとにマイグレーションファイルを用意することが出来る．

- 共通の環境

    `/src/main/resources/common/db/migration` にテストデータを挿入するマイグレーションファイルを入れることでマイグレーション時に実行してくれる．

- local環境

    `/src/main/resources/local/db/migration` にテストデータを挿入するマイグレーションファイルを入れることでマイグレーション時に実行してくれる．

- prod環境

    `/src/main/resources/prod/db/migration` にテストデータを挿入するマイグレーションファイルを入れることでマイグレーション時に実行してくれる．
## application.properties
`/src/main/resources/`以下に各環境ごとにフォルダ分けされている．

- local環境

    `/src/main/resources/local`の`application.properties`を参照する．

- prod環境

    `/src/main/resources/local`の`application.properties`を参照する．