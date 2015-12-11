Пример реализации плагина для "1C: Enterprise Development Tools".
Данный плагин добавляет возможность создания обработчика проведения документа через специальный диалог.
В плагине продемонстрированы примеры:
* расширения контекстного меню редактора модуля
* генерация кода модуля
* работа с моделью объектов метаданных
 
Инструкция по разработке плагинов к 1C:Enterprise Development Tools расположена в подкаталоге  <b>plugin-development/documentation-html/index.html</b> каталога установки инструмента.

# Для разработки и запуска плагина необходимо, чтобы были установлены:
1. 1C:Enterprise Development Tools версии 1.1.0.203 (https://releases.1c.ru/version_files?nick=DevelopmentTools10&ver=1.1.0.203)
2. Eclipse Luna SR2 (4.4.2) for RCP and RAP Developers (http://www.eclipse.org/downloads/packages/eclipse-rcp-and-rap-developers/lunasr2)
3. Java 7 (начиная с 7u65) (http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)

# Запуск плагина из Eclipse
Запускаем Eclipse на чистой рабочей области (workspace) 
## Импортируем проект
Импортировать проект можно любым удобным способом. Или, например:
 1. File -> Import -> Projects from Git
 2. В появившемся диалоге выбираем Clone URI, в поле URI вставляем адрес репозитория, далее следуем простым инструкциям помощника импорта проекта.

## Настраиваем Target Platform
После импорта проекта необходимо настроить целевую платформу. Для этого в файле target/default.target необходимо изменить `${dt_home}` на путь до установленной версии "1C: Enterprise Development Tools" в строке `"<location path="${dt_home}" type="Profile"/>"`, например `"<location path="C:\1cedt" type="Profile"/>"`. После устанавливаем данную целевую платформу, ПКМ на <b>target/default.target</b> -> Open with -> Target Editor -> Set as target platform.

## Запуск 
ПКМ на проекте <b>com._1c.dt.example.plugin</b> -> Run as -> Eclipse application.
После чего запустится 1C:Enterprise Development Tools с разработанным плагином.
Для проверки можно создать новую конфигурацию с документом и регистром накопления, далее в объектном модуле документа через контекстное меню вызвать "Создать движения регистров"
