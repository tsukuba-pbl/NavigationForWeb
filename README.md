# Spring Boot with Docker
## how to use
local環境で動かす場合

```
sh ./serve.sh -e local
```

prod環境で動かす場合

```
sh ./serve.sh -e prod
```

それぞれ中では`docker-compose up`をしている．コマンド実行時には自動的にマイグレーションが実行されている．

実行後， `http://127.0.0.1:8080` or `http://localhost:8080` にアクセスすることでブラウザで確認できる．

mysqlには `mysql -h127.0.0.1 -u docker -p` でパスワードが `password`で接続できる．ユーザ名とパスワードとDB名は，`docker-compose.yml`を参考にしていただきたい．

現在は127.0.0.1から対象のmysqlのコンテナにアクセスできるが，mysqlのコンテナが複数になる場合，mysqlの固定したipを外からアクセス出来るようにする必要があると思われる．その際には`docker-compose.yml`のnetworksの対象のネットワークに`external: true`を追加するといけるのではないか？※やってませんのでわかりません．

## マイグレーションの実行
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
`./mvnw flyway:migrate`でマイグレーションの実行をすることが出来る．

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

## フォルダ構成について
以下のmigrationやapplication.propertiesの各環境ごとに分けているのは，`pom.xml`の`<profiles></profiles>`に記述されている．
### migration
`/src/main/resources/common/db/migration`には共通のマイグレーションファイルが入っている．

また，各環境ごとにマイグレーションファイルを用意することが出来る．

例えば，local環境で，テストデータを入れたい場合`/src/main/resources/local/db/migration`にテストデータを挿入するマイグレーションファイルを入れることでマイグレーション時に実行してくれる．
### application.properties
`/src/main/resources/`以下に各環境ごとにフォルダ分けされている．
local環境は`/src/main/resources/local`の`application.properties`を参照する．