Пример реализации плагина для "1C: Enterprise Development Tools".
Данный плагин добавляет возможность создания обработчика проведения документа через специальный диалог.
В плагине продемонстрированы примеры:
* расширения контекстного меню редактора модуля
* генерация кода модуля
* работа с моделью объектов метаданных
 
Инструкция по разработке плагинов к 1C:Enterprise Development Tools расположена в подкаталоге  <b>plugin-development/documentation-html/index.html</b> каталога установки инструмента.

# Для разработки и запуска плагина необходимо, чтобы были установлены:
1. 1C:Enterprise Development Tools версии 2020.1. (https://releases.1c.ru/project/DevelopmentTools10)
2. Eclipse 2019-06 for Eclipse Committers (https://www.eclipse.org/downloads/packages/release/2019-06/r/eclipse-ide-eclipse-committers)
3. Liberica JDK version 11 (Full version)  (https://bell-sw.com/pages/java-11.0.6/)

# Запуск плагина из Eclipse
Запускаем Eclipse на чистой рабочей области (workspace) 
## Импортируем проект
Импортировать проект можно любым удобным способом. Или, например:
 1. File -> Import -> Projects from Git
 2. В появившемся диалоге выбираем Clone URI, в поле URI вставляем адрес репозитория, далее следуем простым инструкциям помощника импорта проекта.

## Настраиваем Target Platform
После импорта проекта необходимо настроить целевую платформу. Для этого при открытии файла target/default.target нужно ввести в появившемся окне логин и пароль от "https://partners.v8.1c.ru/". После дожидаемся установки всех компонент и устанавливаем данную целевую платформу, ПКМ на <b>target/default.target</b> -> Open with -> Target Editor -> Set as target platform.

## Запуск 
ПКМ на проекте <b>com._1c.dt.example.plugin.ui</b> -> Run as -> Eclipse application.
После чего запустится 1C:Enterprise Development Tools с разработанным плагином.
Для проверки можно создать новую конфигурацию с документом и регистром накопления, далее в объектном модуле документа через контекстное меню вызвать "Создать движения документов"

# Сборка плагина через Maven (https://maven.apache.org/)
## Установка maven:
1. Установить Maven последней версии 3.6.3 (https://maven.apache.org/download.cgi)
2. Выполнить все инструкции по установке (https://maven.apache.org/install.html)
3. Так как необходимые данные для разработки плагина доступны только пользователям портала "partners.v8.1c.ru", то необходимо указать информацию о пользователе. Зашифруем пароль от портала "partners.v8.1c.ru" (более подробно можно почитать по данной ссылке: https://maven.apache.org/guides/mini/guide-encryption.html):
    1. Установить master-password - выполнить в консоли командной строки вашей операционной системы: mvn --encrypt-master-password <password>
        Важно! master-password хранится в файле "папка_пользователя_операциионной_системы/.m2/settings-security.xml", если он не был ранее создан, то его следует создать через файловую систему и поместить туда полученный пароль:           
`<settingsSecurity>`
  `<master>{YT+AcblyWvgHjw8m9ak/WoXRxOIrXIp7S69ULTb3UhQ=}</master>`
`</settingsSecurity>`

    2. Зашифровать пароль от "https://partners.v8.1c.ru/": mvn --encrypt-password <password>
    3. Скопировать полученный результат
4. Сконфигурировать "settings.xml", который обычно располагается в "папка_пользователя_операциионной_системы/.m2/". В секцию <servers> внести следующее:
```        
        <server>
          <id>dt_repository</id>
          <username>Ваше имя пользователя на сайте "https://partners.v8.1c.ru/"</username>
          <password>Ваш зашифрованный пароль от сайта "https://partners.v8.1c.ru/"</password>
        </server>
 ```
5. Пример готового "settings.xml":
```    
     <settings>
      <servers>
          
          <server>
            <id>dt_repository</id>
            <username>username@1c.ru</username>
            <password>{qEXU1eFlPK0HdzfDm+noq/9i981iKCW3ITAitg7coBI=}"</password>
          </server>
          
        </servers>
        <mirrors>
          
        </mirrors>
        <proxies>
         
        </proxies>
      </settings>
 ```
## Сборка p2 репозитория ("https://wiki.eclipse.org/Equinox_p2_Repository_Mirroring") через Maven
1. Открыть консоль и перейти в папку, содержащую разрабатываемый плагин
2. Выполнить: mvn clean verify -f build/pom.xml
3. Собранный репозиторий p2 можно обнаружить в "путь_до_разрабатываемого_плагина/repository/target/repository/"
