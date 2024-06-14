![Build](https://github.com/1C-Company/dt-example-plugins/workflows/CI/badge.svg) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=1c-company_dt-example-plugins&metric=alert_status)](https://sonarcloud.io/dashboard?id=1c-company_dt-example-plugins) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=1c-company_dt-example-plugins&metric=coverage)](https://sonarcloud.io/dashboard?id=1c-company_dt-example-plugins) [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=1c-company_dt-example-plugins&metric=ncloc)](https://sonarcloud.io/dashboard?id=1c-company_dt-example-plugins)

Пример реализации плагина для "1C: Enterprise Development Tools".

Данный плагин представляет собой плагин, который добавляет в 1C:EDT команду для создания обработчика проведения документа через специальный диалог, а также пример расширения проверок модуля. В плагине продемонстрированы следующие приемы:

* расширение контекстного меню редактора встроенного языка,
* генерация кода модуля,
* работа с объектами конфигурации,
* расширение проверки модуля.
* Сборка проекта в CI:
   * Сборка и тестирование с использованием Maven на GitHub Actions
   * Интеграция стат-анализа SonarQube/Cloud
   * Снятие покрытия кода тестами
   * Публикация P2-репозитория

Для изучения и запуска демонстрационного плагина выполните следующие шаги:
* [Настройте](https://edt.1c.ru/dev/ru/docs/plugins/project/env-setup/) необходимое программное обеспечение,
* [Скопируйте или склонируйте](https://edt.1c.ru/dev/ru/docs/plugins/project/copy-clone/) проект плагина из GitHub-репозитория фирмы «1С»,
* [Познакомьтесь](https://edt.1c.ru/dev/ru/docs/plugins/project/project-structure/) со структурой демонстрационного проекта,
* [Запустите](https://edt.1c.ru/dev/ru/docs/plugins/project/run/) плагин из Eclipse.

Руководство по разработке плагинов доступно по [ссылке](https://edt.1c.ru/dev/ru/)
